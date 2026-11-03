package com.frescuratotal.erp.logistics.model;

public enum EstadoRuta {
    PLANIFICADA("Planificada"),
    EN_TRANSITO("En Transito"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada");

    private final String texto;

    EstadoRuta(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public static EstadoRuta desdeTexto(String texto) {
        for (EstadoRuta e : values()) {
            if (e.texto.equalsIgnoreCase(texto)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado de ruta desconocido: " + texto);
    }
}