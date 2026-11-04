package com.frescuratotal.erp.inventory.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    private Lote convertirFila(ResultSet rs) throws SQLException{
        return new Lote(
                rs.getInt("id_lote"),
                rs.getString("numero_lote"),
                rs.getInt("id_producto"),
                rs.getInt("id_proveedor"),
                rs.getObject("fecha_ingreso", LocalDateTime.class),
                rs.getObject("fecha_vencimiento", LocalDate.class),
                rs.getDouble("cantidad_inicial"),
                rs.getDouble("cantidad"),
                rs.getLong("precio_compra"),
                rs.getLong("precio_venta"),
                rs.getString("pasillo"),
                rs.getString("estante"),
                EstadoLote.desdeId(rs.getInt("id_estado"))
        );


    }

    public int insertarLote(Lote l) throws SQLException{
        String sql = "INSERT INTO lotes_inventario(numero_lote,id_producto,id_proveedor,fecha_vencimiento,cantidad_inicial,cantidad,precio_compra,precio_venta,pasillo,estante,id_estado) VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, l.getNumeroLote());
            ps.setInt(2, l.getIdProducto());
            ps.setInt(3, l.getIdProveedor());
            ps.setObject(4,l.getFechaVencimiento());
            ps.setDouble(5, l.getCantidadInicial());
            ps.setDouble(6,l.getCantidad());
            ps.setLong(7,l.getPrecioCompra());
            ps.setLong(8,l.getPrecioVenta());
            ps.setString(9,l.getPasillo());
            ps.setString(10,l.getEstante());
            ps.setInt(11,l.getEstadoLote().getId());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public List<Lote> listarTodos() throws SQLException{
        List<Lote> lista = new ArrayList<>();
        String sql = "SELECT * FROM lotes_inventario ORDER BY fecha_vencimiento";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(convertirFila(rs));
            }
        }
        return lista;
    }

    public Lote buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM lotes_inventario WHERE id_lote = ?";

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

    public boolean actualizarEstado (int idLote, EstadoLote nuevoEstado) throws SQLException{
        String sql = "UPDATE lotes_inventario SET id_estado = ? WHERE id_lote = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, nuevoEstado.getId());
            ps.setInt(2, idLote);

            int filas = ps.executeUpdate();
            return filas > 0;
        }

    }

    public boolean actualizarCantidad (int idLote, double nuevaCantidad) throws SQLException{
        String sql = "UPDATE lotes_inventario SET cantidad = ? WHERE id_lote = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setDouble(1, nuevaCantidad);
            ps.setInt(2, idLote);

            int filas = ps.executeUpdate();
            return filas > 0;
        }

    }

    public List<Lote> listarVendiblesPorProducto(int idProducto) throws SQLException {
        List<Lote> lista = new ArrayList<>();
        String sql = "SELECT * FROM lotes_inventario " +
                "WHERE id_producto = ? AND id_estado IN (1, 2) AND cantidad > 0 " +
                "ORDER BY fecha_vencimiento";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(convertirFila(rs));
                }
            }
        }
        return lista;
    }
    public List<Lote> listarPorProveedor(int idProveedor) throws SQLException{
        List<Lote> lista = new ArrayList<>();
        String sql = "SELECT * FROM lotes_inventario WHERE id_proveedor = ? ORDER BY fecha_ingreso DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(convertirFila(rs));
                }
            }
        }
        return lista;
    }






}
