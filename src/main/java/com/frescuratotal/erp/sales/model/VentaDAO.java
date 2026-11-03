package com.frescuratotal.erp.sales.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;

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



}
