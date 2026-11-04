package com.frescuratotal.erp.inventory.model;

import com.frescuratotal.erp.common.DatabaseConnection;
import com.frescuratotal.erp.sales.model.Cliente;
import com.frescuratotal.erp.suppliers.model.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Producto convertirFila(ResultSet rs) throws SQLException {
    return new Producto(rs.getInt("id_producto"),
            rs.getString("nombre"),
            rs.getString("descripcion"),
            rs.getInt("id_categoria"),
            UnidadMedida.valueOf(rs.getString("unidad_medida")),
            rs.getLong("precio_base"));
    }

    public int insertarProducto(Producto p) throws SQLException{
        String sql = "INSERT INTO productos(nombre,descripcion,id_categoria,unidad_medida,precio_base) VALUES(?,?,?,?,?) ";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getIdCategoria());
            ps.setString(4, p.getUnidadMedida().name());
            ps.setLong(5, p.getPrecioBase());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public List<Producto> listarTodos() throws SQLException{
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(convertirFila(rs));
            }
        }
        return lista;
    }

    public Producto buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM productos WHERE id_producto = ?";

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

    public boolean actualizarProducto(Producto p) throws SQLException{
        String sql = "UPDATE productos " +
                "SET nombre = ?," +
                "descripcion = ?," +
                "id_categoria = ?," +
                "unidad_medida = ?," +
                "precio_base = ? " +
                "WHERE id_producto = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getIdCategoria());
            ps.setString(4, p.getUnidadMedida().name());
            ps.setLong(5, p.getPrecioBase());
            ps.setInt(6, p.getIdProducto());

            int filas = ps.executeUpdate();

            return filas > 0;

        }

    }

    public List<Producto> listarPorProveedor(int idProveedor) throws SQLException{
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.*\n" +
                "FROM productos p\n" +
                "JOIN productos_proveedor pp ON pp.id_producto = p.id_producto\n" +
                "WHERE pp.id_proveedor = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(convertirFila(rs));
                }
            }
        }
        return lista;
    }



}
