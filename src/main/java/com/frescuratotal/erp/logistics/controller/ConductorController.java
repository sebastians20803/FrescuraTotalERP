package com.frescuratotal.erp.logistics.controller;

import com.frescuratotal.erp.logistics.model.Conductor;
import com.frescuratotal.erp.logistics.model.ConductorDAO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ConductorController {
    private ConductorDAO conductorDAO = new ConductorDAO();

    public String registrarConductor(String nombre, String numeroLicencia, String telefono, String email){

        if (nombre == null || nombre.isBlank()) {
            return  ("Nombre inválido");
        }
        if (!numeroLicencia.matches("[0-9]{6,10}")){
            return  ("Licencia inválida: Ejemplo de formato correcto: 1234567890 ");
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return ("Correo no es válido. ");
        }
        if (!telefono.matches("[0-9]{10}")){
            return  ("Telefono inválido: Debe contener 10 numeros exactamente. ");
        }

        Conductor c = new Conductor(nombre, numeroLicencia ,telefono,email);
        try {
            int id = conductorDAO.insertarConductor(c);
            return "Conductor registrado con id " + id;
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ya existe un conductor con ese numero de licencia.";
        } catch (SQLException e) {
            return "Error al registrar: " + e.getMessage();
        }

    }

    public List<Conductor> listarTodos(){
        try {
            return conductorDAO.listarTodos();
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Conductor buscarPorId(int id){
        try {
            return conductorDAO.buscarPorId(id);
        }catch (SQLException e){
            System.out.println("Error al buscar: " + e.getMessage());
            return null;
        }

    }

}
