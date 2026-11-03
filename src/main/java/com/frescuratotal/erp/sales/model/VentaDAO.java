package com.frescuratotal.erp.sales.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public int registrarVenta(Venta v) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);          // ← empieza el paquete
            int idVenta;

            String sqlVenta = "INSERT INTO ventas (numero_venta, id_cliente, subtotal, total_descuento, " +
                    "total_impuesto, monto_total, id_estado, usuario_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, v.getNumeroVenta());
                ps.setInt(2, v.getIdCliente());
                ps.setLong(3, v.getSubtotal());
                ps.setLong(4, v.getTotalDescuento());
                ps.setLong(5, v.getTotalImpuesto());
                ps.setLong(6, v.getMontoTotal());
                ps.setInt(7, v.getEstado().getId());
                ps.setString(8, v.getUsuarioRegistro());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    idVenta = rs.next() ? rs.getInt(1) : -1;
                }
            }

            String sqlDetalle = "INSERT INTO detalles_venta (id_venta, id_lote, cantidad_vendida, precio_unitario, " +
                    "descuento_aplicado, monto_descuento, tasa_impuesto, monto_impuesto, subtotal, total_linea) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlDescontar = "UPDATE lotes_inventario SET cantidad = cantidad - ? " +
                    "WHERE id_lote = ? AND cantidad >= ?";


            for (DetalleVenta d : v.getDetalles()) {

                try (PreparedStatement ps = conn.prepareStatement(sqlDetalle)) {
                    ps.setInt(1, idVenta);
                    ps.setInt(2, d.getIdLote());
                    ps.setDouble(3, d.getCantidadVendida());
                    ps.setLong(4, d.getPrecioUnitario());
                    ps.setDouble(5, d.getDescuentoAplicado());
                    ps.setLong(6, d.getMontoDescuento());
                    ps.setDouble(7, d.getTasaImpuesto());
                    ps.setLong(8, d.getMontoImpuesto());
                    ps.setLong(9, d.getSubtotal());
                    ps.setLong(10, d.getTotalLinea());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(sqlDescontar)) {
                    ps.setDouble(1, d.getCantidadVendida());
                    ps.setInt(2, d.getIdLote());
                    ps.setDouble(3, d.getCantidadVendida());
                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("Stock insuficiente en el lote " + d.getIdLote());
                    }
                }
            }

            conn.commit();                      // ← todo bien: guardar el paquete
            return idVenta;

        } catch (SQLException e) {
            conn.rollback();                    // ← algo falló: deshacer todo
            throw e;                            // y avisarle al controller
        } finally {
            conn.close();                       // ← siempre se cierra, pase lo que pase
        }
    }

    private Venta convertirFila(ResultSet rs) throws SQLException {
        return new Venta(
                rs.getInt("id_venta"),
                rs.getString("numero_venta"),
                rs.getInt("id_cliente"),
                rs.getObject("fecha_venta", LocalDateTime.class),
                rs.getLong("subtotal"),
                rs.getLong("total_descuento"),
                rs.getLong("total_impuesto"),
                rs.getLong("monto_total"),
                EstadoVenta.desdeId(rs.getInt("id_estado")),
                rs.getString("usuario_registro")
        );
    }

    public boolean actualizarEstado (int idVenta, EstadoVenta estado) throws SQLException{
        String sql = "UPDATE ventas SET id_estado = ? WHERE id_venta = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, estado.getId());
            ps.setInt(2, idVenta);

            int filas = ps.executeUpdate();
            return filas > 0;
        }

    }

    public List<Venta> listarPorEstado(EstadoVenta estado) throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE id_estado = ? ORDER BY fecha_venta";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, estado.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(convertirFila(rs));
                }
            }
        }
        return lista;
    }

    public Venta buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return convertirFila(rs);
                }else {
                    return null;
                }
            }
        }
    }

}
