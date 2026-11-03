package com.frescuratotal.erp.logistics.model;

import com.frescuratotal.erp.common.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

        private Vehiculo convertirFila(ResultSet rs) throws SQLException {
            return new Vehiculo(
                    rs.getInt("id_vehiculo"),
                    rs.getString("placa"),
                    rs.getString("modelo"),
                    rs.getDouble("capacidad"),
                    EstadoVehiculo.desdeTexto(rs.getString("estado"))
            );
        }
        public int insertarVehiculo(Vehiculo v) throws SQLException{
            String sql = "INSERT INTO vehiculos(placa,modelo,capacidad,estado) VALUES(?,?,?,?) ";
            try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, v.getPlaca());
                ps.setString(2, v.getModelo());
                ps.setDouble(3, v.getCapacidad());
                ps.setString(4, v.getEstado().getTexto());

                ps.executeUpdate();

                try(ResultSet rs = ps.getGeneratedKeys()){
                    return rs.next() ? rs.getInt(1) : -1;
                }
            }
        }

        public List<Vehiculo> listarTodos() throws SQLException{
            List<Vehiculo> lista = new ArrayList<>();
            String sql = "SELECT * FROM vehiculos";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(convertirFila(rs));
                }
            }
            return lista;
        }

        public Vehiculo buscarPorId(int id) throws SQLException{
            String sql = "SELECT * FROM vehiculos WHERE id_vehiculo = ?";

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


