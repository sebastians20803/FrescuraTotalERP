package com.frescuratotal.erp.sales.controller;

import com.frescuratotal.erp.sales.model.Cliente;
import com.frescuratotal.erp.sales.model.ClienteDAO;
import com.frescuratotal.erp.sales.model.TipoCliente;


import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO = new ClienteDAO();

    public String registrarCliente(String nombre, String documento, String email, String telefono, String direccion, TipoCliente tipo){

        if (nombre == null || nombre.isBlank()) {
            return  ("Nombre inválido");
        }
        if (!documento.matches("[0-9]{6,10}|[0-9]{9}-[0-9]")){
            return  ("Documento inválido: Ejemplo de formato correcto: 1234567890 o 900123456-7 ");
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return ("Correo no es válido. ");
        }
        if (!telefono.matches("[0-9]{10}")){
            return  ("Telefono inválido: Debe contener 10 numeros exactamente. ");
        }
        if (direccion == null || direccion.isBlank()) {
            return  ("Direccion inválida. ");
        }

        Cliente c = new Cliente(nombre, documento,email,telefono,direccion,tipo);
        try {
            int id = clienteDAO.insertarCliente(c);
            return "Cliente registrado con id " + id;
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ya existe un cliente con ese documento.";
        } catch (SQLException e) {
            return "Error al registrar: " + e.getMessage();
        }

    }

    public List<Cliente> listarTodos(){
        try {
            return clienteDAO.listarTodos();
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Cliente buscarPorId(int id){
        try {
            return clienteDAO.buscarPorId(id);
        }catch (SQLException e){
            System.out.println("Error al buscar: " + e.getMessage());
            return null;
        }

    }

    public List<Cliente> listarPorLote(int idLote){
        try {
            return clienteDAO.listarPorLote(idLote);
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }





}
