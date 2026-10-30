package com.frescuratotal.erp.inventory.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listarTodos() throws SQLException{
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias_producto ORDER BY id_categoria";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                Categoria c = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    public boolean existeCategoria(int id) throws SQLException{
        String sql = "SELECT * FROM categorias_producto WHERE id_categoria = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }










}
