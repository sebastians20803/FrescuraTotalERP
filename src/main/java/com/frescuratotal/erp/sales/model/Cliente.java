package com.frescuratotal.erp.sales.model;

import java.time.LocalDateTime;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
    private String direccion;
    private TipoCliente tipoCliente;
    private LocalDateTime fechaRegistro;

    public Cliente(int id_cliente, String nombre, String documento, String email, String telefono, String direccion, TipoCliente tipoCliente, LocalDateTime fechaRegistro) {
        this.idCliente = id_cliente;
        this.nombre = nombre;
        this.documento = documento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tipoCliente = tipoCliente;
        this.fechaRegistro = fechaRegistro;
    }

    public Cliente(String nombre, String documento, String email, String telefono, String direccion, TipoCliente tipoCliente) {
        this.nombre = nombre;
        this.documento = documento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tipoCliente = tipoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return String.format("%-4d %-30.30s %-13s %-12s %-25.25s %s",
                idCliente, nombre, documento, telefono, email, tipoCliente);
    }

    public static String encabezado() {
        return String.format("%-4s %-30s %-13s %-12s %-25s %s",
                "ID", "NOMBRE", "DOCUMENTO", "TELÉFONO", "EMAIL", "TIPO");
    }



}
