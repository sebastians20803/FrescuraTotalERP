package com.frescuratotal.erp.logistics.model;

import java.time.LocalDateTime;

public class RutaEntrega {
    private int idRuta;
    private String nombreRuta;
    private LocalDateTime fechaCreacion;
    private String usuarioCreacion;
    private LocalDateTime horaSalidaReal;
    private LocalDateTime horaLlegadaReal;
    private EstadoRuta estado;

    public RutaEntrega(int idRuta, String nombreRuta, LocalDateTime fechaCreacion, String usuarioCreacion,
                       LocalDateTime horaSalidaReal, LocalDateTime horaLlegadaReal, EstadoRuta estado) {
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.horaSalidaReal = horaSalidaReal;
        this.horaLlegadaReal = horaLlegadaReal;
        this.estado = estado;
    }

    public RutaEntrega(String nombreRuta, String usuarioCreacion) {
        this.nombreRuta = nombreRuta;
        this.usuarioCreacion = usuarioCreacion;
        this.estado = EstadoRuta.PLANIFICADA;
    }

    public int getIdRuta() { return idRuta; }
    public String getNombreRuta() { return nombreRuta; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public String getUsuarioCreacion() { return usuarioCreacion; }
    public LocalDateTime getHoraSalidaReal() { return horaSalidaReal; }
    public LocalDateTime getHoraLlegadaReal() { return horaLlegadaReal; }
    public EstadoRuta getEstado() { return estado; }

    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }

    @Override
    public String toString() {
        return idRuta + ". " + nombreRuta + " | " + estado
                + (horaSalidaReal != null ? " | salió: " + horaSalidaReal : "")
                + (horaLlegadaReal != null ? " | llegó: " + horaLlegadaReal : "");
    }
}