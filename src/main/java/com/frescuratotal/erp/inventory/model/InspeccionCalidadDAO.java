package com.frescuratotal.erp.inventory.model;

import com.frescuratotal.erp.common.DatabaseConnection;
import com.frescuratotal.erp.suppliers.model.Proveedor;

import java.sql.*;

public class InspeccionCalidadDAO {
    public int insertarInspeccion(InspeccionCalidad iC) throws SQLException {
        String sql = "INSERT INTO inspecciones_calidad(id_lote,resultado,nombre_inspector,motivo_rechazo,notas) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, iC.getIdLote());
            ps.setString(2, iC.isAprobado() ? "Aprobado" : "Rechazado");
            ps.setString(3, iC.getNombreInspector());
            ps.setString(4, iC.getMotivoRechazo());
            ps.setString(5, iC.getNotas());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }

    }
}
