package com.frescuratotal.erp.suppliers.controller;

import com.frescuratotal.erp.suppliers.model.Proveedor;
import com.frescuratotal.erp.suppliers.model.ProveedorDAO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorController {

    ProveedorDAO dao = new ProveedorDAO();

    public String registrarProveedor(String nombre, String nit, String personaContacto, String email,
                                     String telefono, String direccion) {

        if (nombre == null || nombre.isBlank()) {
            return  ("Nombre inválido");
        }
        if (!nit.matches("[0-9]{9}-[0-9]")){
            return  ("Nit inválido: Ejemplo de formato correcto: 123456789-0 ");
        }
        if (personaContacto == null || personaContacto.isBlank()) {
            return  ("Nombre inválido para persona de contacto. ");
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


        Proveedor p = new Proveedor(nombre, nit, personaContacto, email, telefono, direccion);
        try {
            int id = dao.insertarProveedor(p);
            return "Proveedor registrado con id " + id;
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ya existe un proveedor con ese NIT.";
        } catch (SQLException e) {
            return "Error al registrar: " + e.getMessage();
        }
    }

    public List<Proveedor> listarProveedores(){
        try {
            return dao.listarTodos();
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean desactivarProveedor(int id){
        try {
            return dao.desactivarProveedor(id);
        }   catch (SQLException e){
            System.out.println("Error al desactivar: " + e.getMessage());
            return false;
        }
    }

    public String actualizarProveedor(Proveedor p){
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            return  ("Nombre inválido");
        }

        try {
            Proveedor original = dao.buscarPorId(p.getIdProveedor());
            boolean nitCambio = original == null || !original.getNit().equals(p.getNit());
            if (nitCambio && !p.getNit().matches("[0-9]{9}-[0-9]")) {
                return "Nit inválido: Ejemplo de formato correcto: 123456789-0";
            }
        } catch (SQLException e) {
            return "Error al validar el NIT: " + e.getMessage();
        }

        if (p.getPersonaContacto() == null || p.getPersonaContacto().isBlank()) {
            return  ("Nombre inválido para persona de contacto. ");
        }
        if (!p.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return ("Correo no es válido. ");
        }
        if (!p.getTelefono().matches("[0-9]{10}")){
            return  ("Telefono inválido: Debe contener 10 numeros exactamente. ");
        }
        if (p.getDireccion() == null || p.getDireccion().isBlank()) {
            return  ("Direccion inválida. ");
        }

        try {
            if(dao.actualizarProveedor(p)){
                return ("Proveedor Actualizado Correctamente!");
            }else {
                return ("No existe un proveedor con ese ID.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ya existe otro proveedor con ese NIT.";
        } catch (SQLException e){
            return "Error al actualizar: " + e.getMessage();
        }

    }

    public Proveedor buscarPorId(int id){
        try {
            return dao.buscarPorId(id);
        }catch (SQLException e){
            System.out.println("Error al buscar: " + e.getMessage());
            return null;
        }

    }

}
