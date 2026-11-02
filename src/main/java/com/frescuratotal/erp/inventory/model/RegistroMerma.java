package com.frescuratotal.erp.inventory.model;

import java.time.LocalDateTime;

public class RegistroMerma {
    private int idRegistro;
    private int idLote;
    private Integer idInspeccion;
    private double cantidadPerdida;
    private String motivo;
    private LocalDateTime FechaRegistro;
    private String usuarioRegistro;

    public RegistroMerma(int idRegistro, int idLote, Integer idInspeccion, double cantidadperdida, String motivo, LocalDateTime fechaInspeccion, String usuarioRegistro) {
        this.idRegistro = idRegistro;
        this.idLote = idLote;
        this.idInspeccion = idInspeccion;
        this.cantidadPerdida = cantidadperdida;
        this.motivo = motivo;
        this.FechaRegistro = fechaInspeccion;
        this.usuarioRegistro = usuarioRegistro;
    }

    public RegistroMerma(int idLote, Integer idInspeccion, double cantidadperdida, String motivo, String usuarioRegistro) {
        this.idLote = idLote;
        this.idInspeccion = idInspeccion;
        this.cantidadPerdida = cantidadperdida;
        this.motivo = motivo;
        this.usuarioRegistro = usuarioRegistro;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public int getIdLote() {
        return idLote;
    }

    public Integer getIdInspeccion() {
        return idInspeccion;
    }

    public double getCantidadPerdida() {
        return cantidadPerdida;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getFechaRegistro() {
        return FechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public void setIdInspeccion(Integer idInspeccion) {
        this.idInspeccion = idInspeccion;
    }

    public void setCantidadPerdida(double cantidadPerdida) {
        this.cantidadPerdida = cantidadPerdida;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.FechaRegistro = fechaRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public String toString() {
        return "RegistroMerma{" +
                "idRegistro=" + idRegistro +
                ", idLote=" + idLote +
                ", idInspeccion=" + idInspeccion +
                ", cantidadperdida=" + cantidadPerdida +
                ", motivo='" + motivo + '\'' +
                ", fechaInspeccion=" + FechaRegistro +
                ", usuarioRegistro='" + usuarioRegistro + '\'' +
                '}';
    }
}
