package com.frescuratotal.erp.common;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InformacionEmpresaDAO {
    public InformacionEmpresa obtener() throws SQLException {
        String sql = "SELECT * FROM informacion_empresa LIMIT 1";

            try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){

                try (ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return new InformacionEmpresa(
                                rs.getInt("id_empresa"),
                                rs.getString("nombre_empresa"),
                                rs.getString("nit"),
                                rs.getString("direccion"),
                                rs.getString("ciudad"),
                                rs.getString("telefono"),
                                rs.getString("email")
                        );
                    }else {
                        return null;
                    }
                }
            }
    }
}

