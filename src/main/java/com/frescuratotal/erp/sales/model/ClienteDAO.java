package com.frescuratotal.erp.sales.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Cliente convertirFila(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id_cliente"),
                rs.getString("nombre"),
                rs.getString("documento"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("direccion"),
                TipoCliente.desdeId(rs.getInt("id_tipo")),
                rs.getObject("fecha_registro", LocalDateTime.class)
        );
    }

    public int insertarCliente(Cliente c) throws SQLException{
        String sql = "INSERT INTO clientes(nombre,documento,email,telefono,direccion,id_tipo) VALUES(?,?,?,?,?,?) ";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getEmail());
            ps.setString(4,c.getTelefono());
            ps.setString(5,c.getDireccion());
            ps.setInt(6, c.getTipoCliente().getId());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }
    public List<Cliente> listarTodos() throws SQLException{
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(convertirFila(rs));
            }
        }
        return lista;
    }

    public Cliente buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

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
