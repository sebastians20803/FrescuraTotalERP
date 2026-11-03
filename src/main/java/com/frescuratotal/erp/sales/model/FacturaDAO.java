package com.frescuratotal.erp.sales.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;

public class FacturaDAO {

    public int insertar(Factura f) throws SQLException {
        String sql = "INSERT INTO facturas(id_venta,numero_factura,cufe,enlace_qr,fecha_emision," +
                "subtotal,total_descuento,monto_impuesto,monto_total) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,f.getIdVenta());
            ps.setString(2,f.getNumeroFactura());
            ps.setString(3,f.getCufe());
            ps.setString(4, f.getEnlaceQr());
            ps.setObject(5, f.getFechaEmision());
            ps.setLong(6, f.getSubtotal());
            ps.setLong(7, f.getTotalDescuento());
            ps.setLong(8, f.getMontoImpuesto());
            ps.setLong(9, f.getMontoTotal());


            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }


}
