package com.frescuratotal.erp.common;

public class InformacionEmpresa {
    private int idEmpresa;
    private String nombreEmpresa;
    private String nit;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String email;

    public InformacionEmpresa(int idEmpresa, String nombreEmpresa, String nit, String direccion,
                              String ciudad, String telefono, String email) {
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.nit = nit;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIdEmpresa() { return idEmpresa; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public String getNit() { return nit; }
    public String getDireccion() { return direccion; }
    public String getCiudad() { return ciudad; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return nombreEmpresa + " - NIT " + nit;
    }
}