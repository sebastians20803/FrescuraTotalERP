package com.frescuratotal.erp.suppliers.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {



    public List<Proveedor> listarTodos() throws SQLException {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proveedor p = new Proveedor(
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre"),
                        rs.getString("nit"),
                        rs.getString("persona_contacto"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getBoolean("activo"),
                        rs.getObject("fecha_registro", LocalDateTime.class)
                );
                lista.add(p);
            }
        }
        return lista;
    }


    public int insertarProveedor(Proveedor p) throws SQLException{
        String sql = "INSERT INTO proveedores(nombre,nit,persona_contacto,email,telefono,direccion,activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getNit());
            ps.setString(3, p.getPersonaContacto());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getDireccion());
            ps.setBoolean(7, true);

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }

    }




}
