package com.frescuratotal.erp.inventory.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;

public class RegistroCuarentenaDAO {

    public int insertarRegistroCuarentena(RegistroCuarentena rC) throws SQLException {
        String sql = "INSERT INTO registro_cuarentena(id_lote,motivo,usuario) VALUES(?,?,?) ";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, rC.getIdLote());
            ps.setString(2, rC.getMotivo());
            ps.setString(3, rC.getUsuario());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }



}
