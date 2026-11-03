package com.frescuratotal.erp.logistics.controller;

import com.frescuratotal.erp.logistics.model.Vehiculo;
import com.frescuratotal.erp.logistics.model.VehiculoDAO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoController {
        private VehiculoDAO vehiculoDAO = new VehiculoDAO();

        public String registrarVehiculo(String placa, String modelo, double capacidad){

            if (placa == null || placa.isBlank()) {
                return  ("Placa inválida");
            }
            if (modelo == null || modelo.isBlank()) {
                return  ("Modelo inválido");
            }
            if (!(capacidad>0)){
                return "La capacidad del vehiculo debe ser mayor a cero";
            }



            Vehiculo v = new Vehiculo(placa, modelo, capacidad);
            try {
                int id = vehiculoDAO.insertarVehiculo( v);
                return "Vehiculo registrado con id " + id;
            } catch (SQLIntegrityConstraintViolationException e) {
                return "Ya existe un vehiculo con ese numero de placa.";
            } catch (SQLException e) {
                return "Error al registrar: " + e.getMessage();
            }

        }

        public List<Vehiculo> listarTodos(){
            try {
                return vehiculoDAO.listarTodos();
            }catch (SQLException e){
                System.out.println("Error al listar: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        public Vehiculo buscarPorId(int id){
            try {
                return vehiculoDAO.buscarPorId(id);
            }catch (SQLException e){
                System.out.println("Error al buscar: " + e.getMessage());
                return null;
            }

        }

}


