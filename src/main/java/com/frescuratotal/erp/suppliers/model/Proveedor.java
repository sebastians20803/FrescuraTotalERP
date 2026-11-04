package com.frescuratotal.erp.suppliers.model;

import java.time.LocalDateTime;

public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String nit;
    private String personaContacto;
    private String email;
    private String telefono;
    private String direccion;
    private boolean activo;
    private LocalDateTime fechaRegistro;

    public Proveedor(int idProveedor, String nombre, String nit, String personaContacto, String email, String telefono, String direccion, boolean activo, LocalDateTime fechaRegistro) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.nit = nit;
        this.personaContacto = personaContacto;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    public Proveedor(String nombre, String nit, String personaContacto, String email, String telefono, String direccion) {
        this.nombre = nombre;
        this.nit = nit;
        this.personaContacto = personaContacto;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.activo = true;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    @Override
    public String toString() {
        return String.format("%-4d %-30.30s %-13s %-22.22s %-12s %s",
                idProveedor, nombre, nit, personaContacto, telefono, activo ? "Sí" : "No");
    }

    public static String encabezado() {
        return String.format("%-4s %-30s %-13s %-22s %-12s %s",
                "ID", "NOMBRE", "NIT", "CONTACTO", "TELÉFONO", "ACTIVO");
    }
}
