package com.frescuratotal.erp.inventory.model;

import java.time.LocalDateTime;

public class RegistroCuarentena {
    private int idRegistro;
    private int idLote;
    private String motivo;
    private String usuario;
    private LocalDateTime fechaRegistro;
    private boolean resuelta;
    private LocalDateTime fechaResolucion;
    private String notasResolucion;

    public RegistroCuarentena(int idRegistro, int idLote, String motivo, String usuario, LocalDateTime fechaRegistro, boolean resuelta, LocalDateTime fechaResolucion, String notasResolucion) {
        this.idRegistro = idRegistro;
        this.idLote = idLote;
        this.motivo = motivo;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.resuelta = resuelta;
        this.fechaResolucion = fechaResolucion;
        this.notasResolucion = notasResolucion;
    }

    public RegistroCuarentena(int idLote, String motivo, String usuario) {
        this.idLote = idLote;
        this.motivo = motivo;
        this.usuario = usuario;
        this.resuelta = false;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public int getIdLote() {
        return idLote;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public String getNotasResolucion() {
        return notasResolucion;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public void setNotasResolucion(String notasResolucion) {
        this.notasResolucion = notasResolucion;
    }

    @Override
    public String toString() {
        return "RegistroCuarentena{" +
                "idRegistro=" + idRegistro +
                ", idLote=" + idLote +
                ", motivo='" + motivo + '\'' +
                ", usuario='" + usuario + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", resuelto=" + resuelta +
                ", fechaResolucion=" + fechaResolucion +
                ", notasResolucion='" + notasResolucion + '\'' +
                '}';
    }
}
