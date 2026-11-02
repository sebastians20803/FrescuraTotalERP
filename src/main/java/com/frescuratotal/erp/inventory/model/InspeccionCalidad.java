package com.frescuratotal.erp.inventory.model;

import java.time.LocalDateTime;

public class InspeccionCalidad {
    private int idInspeccion;
    private int idLote;
    private LocalDateTime fechaInspeccion;
    private boolean aprobado;
    private String nombreInspector;
    private String motivoRechazo;
    private String notas;

    public InspeccionCalidad(int id_inspeccion, int idLote, LocalDateTime fechaInspeccion, boolean resultado, String nombreInspector, String motivoRechazo, String notas) {
        this.idInspeccion = id_inspeccion;
        this.idLote = idLote;
        this.fechaInspeccion = fechaInspeccion;
        this.aprobado = resultado;
        this.nombreInspector = nombreInspector;
        this.motivoRechazo = motivoRechazo;
        this.notas = notas;
    }

    public InspeccionCalidad(int idLote, boolean resultado, String nombreInspector, String motivoRechazo, String notas) {
        this.idLote = idLote;
        this.aprobado = resultado;
        this.nombreInspector = nombreInspector;
        this.motivoRechazo = motivoRechazo;
        this.notas = notas;
    }

    public int getIdInspeccion() {
        return idInspeccion;
    }

    public int getIdLote() {
        return idLote;
    }

    public LocalDateTime getFechaInspeccion() {
        return fechaInspeccion;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public String getNombreInspector() {
        return nombreInspector;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public String getNotas() {
        return notas;
    }

    public void setIdInspeccion(int idInspeccion) {
        this.idInspeccion = idInspeccion;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public void setFechaInspeccion(LocalDateTime fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public void setNombreInspector(String nombreInspector) {
        this.nombreInspector = nombreInspector;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "InspeccionCalidad{" +
                "id_inspeccion=" + idInspeccion +
                ", idLote=" + idLote +
                ", fechaInspeccion=" + fechaInspeccion +
                ", resultado=" + aprobado +
                ", nombreInspector='" + nombreInspector + '\'' +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", notas='" + notas + '\'' +
                '}';
    }
}
