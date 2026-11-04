package com.frescuratotal.erp.logistics.model;

public class Conductor {
    private int idConductor;
    private String nombre;
    private String numeroLicencia;
    private String telefono;
    private String email;
    private boolean activo;

    public Conductor(int idConductor, String nombre, String numeroLicencia,
                     String telefono, String email, boolean activo) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.numeroLicencia = numeroLicencia;
        this.telefono = telefono;
        this.email = email;
        this.activo = activo;
    }

    public Conductor(String nombre, String numeroLicencia, String telefono, String email) {
        this.nombre = nombre;
        this.numeroLicencia = numeroLicencia;
        this.telefono = telefono;
        this.email = email;
        this.activo = true;
    }

    public int getIdConductor() { return idConductor; }
    public String getNombre() { return nombre; }
    public String getNumeroLicencia() { return numeroLicencia; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public boolean isActivo() { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return String.format("%-4d %-28.28s %-12s %-12s %s",
                idConductor, nombre, numeroLicencia, telefono, activo ? "Sí" : "No");
    }

    public static String encabezado() {
        return String.format("%-4s %-28s %-12s %-12s %s",
                "ID", "NOMBRE", "LICENCIA", "TELÉFONO", "ACTIVO");
    }


}