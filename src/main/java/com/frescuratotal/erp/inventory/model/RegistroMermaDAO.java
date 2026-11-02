package com.frescuratotal.erp.inventory.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;

public class RegistroMermaDAO {

    public int registrarMerma(RegistroMerma rM) throws SQLException {
        String sql = "INSERT INTO registro_mermas(id_lote,id_inspeccion,cantidad_perdida,motivo,usuario_registro) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, rM.getIdLote());
            if (rM.getIdInspeccion() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, rM.getIdInspeccion());
            }
            ps.setDouble(3, rM.getCantidadPerdida());
            ps.setString(4, rM.getMotivo());
            ps.setString(5, rM.getUsuarioRegistro());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }

    }
}
