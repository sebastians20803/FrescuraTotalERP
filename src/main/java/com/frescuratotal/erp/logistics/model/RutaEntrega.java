package com.frescuratotal.erp.logistics.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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


    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @Override
    public String toString() {
        String salida = (horaSalidaReal == null) ? "-" : horaSalidaReal.format(FORMATO);
        String llegada = (horaLlegadaReal == null) ? "-" : horaLlegadaReal.format(FORMATO);
        return String.format("%-4d %-25.25s %-13s %-17s %s",
                idRuta, nombreRuta, estado, salida, llegada);
    }

    public static String encabezado() {
        return String.format("%-4s %-25s %-13s %-17s %s",
                "ID", "NOMBRE", "ESTADO", "SALIDA", "LLEGADA");
    }
}