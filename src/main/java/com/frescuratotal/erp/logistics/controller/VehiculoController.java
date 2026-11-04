package com.frescuratotal.erp.logistics.controller;

import com.frescuratotal.erp.common.LogUtils;
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
        if (capacidad <= 0){
            return "La capacidad del vehiculo debe ser mayor a cero";
        }

        Vehiculo v = new Vehiculo(placa, modelo, capacidad);
        try {
            int id = vehiculoDAO.insertarVehiculo(v);
            LogUtils.info("Vehículo registrado: placa " + placa + ", " + modelo + " (id " + id + ")");
            return "Vehiculo registrado con id " + id;
        } catch (SQLIntegrityConstraintViolationException e) {
            LogUtils.warn("Intento de registrar una placa repetida: " + placa);
            return "Ya existe un vehiculo con ese numero de placa.";
        } catch (SQLException e) {
            LogUtils.error("Error al registrar el vehículo " + placa, e);
            return "Error al registrar: " + e.getMessage();
        }
    }

    public List<Vehiculo> listarTodos(){
        try {
            return vehiculoDAO.listarTodos();
        }catch (SQLException e){
            LogUtils.error("Error al listar vehículos", e);
            return new ArrayList<>();
        }
    }

    public Vehiculo buscarPorId(int id){
        try {
            return vehiculoDAO.buscarPorId(id);
        }catch (SQLException e){
            LogUtils.error("Error al buscar el vehículo " + id, e);
            return null;
        }
    }
}