package com.frescuratotal.erp.logistics.model;

import com.frescuratotal.erp.common.DatabaseConnection;
import com.frescuratotal.erp.sales.model.EstadoVenta;
import com.frescuratotal.erp.sales.model.Venta;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {

    private RutaEntrega convertirFila(ResultSet rs) throws SQLException {
        return new RutaEntrega(
                rs.getInt("id_ruta"),
                rs.getString("nombre_ruta"),
                rs.getObject("fecha_creacion", LocalDateTime.class),
                rs.getString("usuario_creacion"),
                rs.getObject("hora_salida_real", LocalDateTime.class),
                rs.getObject("hora_llegada_real", LocalDateTime.class),
                EstadoRuta.desdeTexto(rs.getString("estado"))
        );
    }

    // Crea la ruta, la asignación y las ventas incluidas: TODO o NADA
    // direcciones va en el mismo orden que ventas
    public int crearRuta(RutaEntrega ruta, int idVehiculo, int idConductor,
                         List<Venta> ventas, List<String> direcciones) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            int idRuta;

            // 1. La ruta
            String sqlRuta = "INSERT INTO rutas_entrega (nombre_ruta, usuario_creacion, estado) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlRuta, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, ruta.getNombreRuta());
                ps.setString(2, ruta.getUsuarioCreacion());
                ps.setString(3, ruta.getEstado().getTexto());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    idRuta = rs.next() ? rs.getInt(1) : -1;
                }
            }

            // 2. Qué vehículo y qué conductor van
            String sqlAsignacion = "INSERT INTO asignaciones_entrega (id_ruta, id_vehiculo, id_conductor, usuario_asignacion) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlAsignacion)) {
                ps.setInt(1, idRuta);
                ps.setInt(2, idVehiculo);
                ps.setInt(3, idConductor);
                ps.setString(4, ruta.getUsuarioCreacion());
                ps.executeUpdate();
            }

            // 3. Cada venta de la ruta, con su orden de entrega y dirección
            String sqlVentaRuta = "INSERT INTO ventas_ruta (id_ruta, id_venta, orden_entrega, direccion_destino) " +
                    "VALUES (?, ?, ?, ?)";
            for (int i = 0; i < ventas.size(); i++) {
                try (PreparedStatement ps = conn.prepareStatement(sqlVentaRuta)) {
                    ps.setInt(1, idRuta);
                    ps.setInt(2, ventas.get(i).getIdVenta());
                    ps.setInt(3, i + 1);                 // la primera venta es la entrega 1
                    ps.setString(4, direcciones.get(i));
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return idRuta;

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public boolean ventaEstaEnRuta(int idVenta) throws SQLException {
        String sql = "SELECT 1 FROM ventas_ruta WHERE id_venta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<RutaEntrega> listarTodas() throws SQLException {
        List<RutaEntrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutas_entrega ORDER BY fecha_creacion DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(convertirFila(rs));
            }
        }
        return lista;
    }

    public RutaEntrega buscarPorId(int idRuta) throws SQLException {
        String sql = "SELECT * FROM rutas_entrega WHERE id_ruta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idRuta);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return convertirFila(rs);
                }else {
                    return null;
                }
            }
        }
    }

    // La ruta sale: ruta EN TRANSITO + sus ventas EN_TRANSITO
    public void iniciarRuta(int idRuta) throws SQLException {
        cambiarEstadoRuta(idRuta, EstadoRuta.EN_TRANSITO, "hora_salida_real", EstadoVenta.EN_TRANSITO);
    }

    // La ruta termina: ruta COMPLETADA + sus ventas ENTREGADA
    public void completarRuta(int idRuta) throws SQLException {
        cambiarEstadoRuta(idRuta, EstadoRuta.COMPLETADA, "hora_llegada_real", EstadoVenta.ENTREGADA);
    }

    private void cambiarEstadoRuta(int idRuta, EstadoRuta nuevoEstadoRuta, String columnaHora,
                                   EstadoVenta nuevoEstadoVentas) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // 1. La ruta: nuevo estado + la hora real (salida o llegada)
            String sqlRuta = "UPDATE rutas_entrega SET estado = ?, " + columnaHora + " = NOW() WHERE id_ruta = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlRuta)) {
                ps.setString(1, nuevoEstadoRuta.getTexto());
                ps.setInt(2, idRuta);
                ps.executeUpdate();
            }

            // 2. Todas las ventas de esa ruta, de un solo golpe
            String sqlVentas = "UPDATE ventas SET id_estado = ? " +
                    "WHERE id_venta IN (SELECT id_venta FROM ventas_ruta WHERE id_ruta = ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlVentas)) {
                ps.setInt(1, nuevoEstadoVentas.getId());
                ps.setInt(2, idRuta);
                ps.executeUpdate();
            }

            // 3. Solo al completar: marcar la fecha de entrega de cada venta
            if (nuevoEstadoVentas == EstadoVenta.ENTREGADA) {
                String sqlEntrega = "UPDATE ventas_ruta SET fecha_entrega = NOW() WHERE id_ruta = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlEntrega)) {
                    ps.setInt(1, idRuta);
                    ps.executeUpdate();
                }
            }
            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }
}
