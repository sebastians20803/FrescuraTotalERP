package com.frescuratotal.erp.suppliers.controller;

import com.frescuratotal.erp.inventory.model.Lote;
import com.frescuratotal.erp.inventory.model.LoteDAO;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.inventory.model.ProductoDAO;
import com.frescuratotal.erp.suppliers.model.Proveedor;
import com.frescuratotal.erp.suppliers.model.ProveedorDAO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorController {

    private ProveedorDAO proveedorDAO = new ProveedorDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private LoteDAO loteDAO = new LoteDAO();

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
            int id = proveedorDAO.insertarProveedor(p);
            return "Proveedor registrado con id " + id;
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ya existe un proveedor con ese NIT.";
        } catch (SQLException e) {
            return "Error al registrar: " + e.getMessage();
        }
    }

    public List<Proveedor> listarProveedores(){
        try {
            return proveedorDAO.listarTodos();
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean desactivarProveedor(int id){
        try {
            return proveedorDAO.desactivarProveedor(id);
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
            Proveedor original = proveedorDAO.buscarPorId(p.getIdProveedor());
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
            if(proveedorDAO.actualizarProveedor(p)){
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
            return proveedorDAO.buscarPorId(id);
        }catch (SQLException e){
            System.out.println("Error al buscar: " + e.getMessage());
            return null;
        }

    }

    public String agregarAlCatalogo(int idProveedor, int idProducto) {
        try {
            Proveedor proveedor = proveedorDAO.buscarPorId(idProveedor);
            if (proveedor == null) {
                return "No existe un proveedor con ese ID.";
            }
            if (!proveedor.isActivo()) {
                return "El proveedor está inactivo.";
            }
            Producto producto = productoDAO.buscarPorId(idProducto);
            if (producto == null) {
                return "No existe un producto con ese ID.";
            }

            proveedorDAO.agregarAlCatalogo(idProveedor, idProducto);
            return producto.getNombre() + " agregado al catálogo de " + proveedor.getNombre() + ".";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ese producto ya está en el catálogo de este proveedor.";
        } catch (SQLException e) {
            return "Error al agregar al catálogo: " + e.getMessage();
        }
    }

    public String quitarDelCatalogo(int idProveedor, int idProducto) {
        try {
            if (proveedorDAO.quitarDelCatalogo(idProveedor, idProducto)) {
                return "Producto quitado del catálogo.";
            } else {
                return "Ese producto no está en el catálogo de este proveedor.";
            }
        } catch (SQLException e) {
            return "Error al quitar del catálogo: " + e.getMessage();

        }
    }

    public List<Producto> verCatalogo(int idProveedor) {
        try {
            return productoDAO.listarPorProveedor(idProveedor);
        } catch (SQLException e) {
            System.out.println("Error al consultar el catálogo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Lote> historialCompras(int idProveedor) {
        try {
            return loteDAO.listarPorProveedor(idProveedor);
        } catch (SQLException e) {
            System.out.println("Error al consultar el historial: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
