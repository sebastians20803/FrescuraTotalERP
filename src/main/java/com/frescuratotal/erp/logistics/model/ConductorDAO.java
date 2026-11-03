package com.frescuratotal.erp.logistics.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    private Conductor convertirFila(ResultSet rs) throws SQLException {
        return new Conductor(
            rs.getInt("id_conductor"),
            rs.getString("nombre"),
            rs.getString("numero_licencia"),
            rs.getString("telefono"),
            rs.getString("email"),
            rs.getBoolean("activo")
    );
    }
    public int insertarConductor(Conductor c) throws SQLException{
        String sql = "INSERT INTO conductores(nombre,numero_licencia,telefono,email,activo) VALUES(?,?,?,?,?) ";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getNumeroLicencia());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getEmail());
            ps.setBoolean(5, c.isActivo());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public List<Conductor> listarTodos() throws SQLException{
        List<Conductor> lista = new ArrayList<>();
        String sql = "SELECT * FROM conductores";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(convertirFila(rs));
            }
        }
        return lista;
    }

    public Conductor buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM conductores WHERE id_conductor = ?";

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

