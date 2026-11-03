package com.frescuratotal.erp.logistics.model;

public enum EstadoVehiculo {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    MANTENIMIENTO("Mantenimiento");

    private final String texto;

    EstadoVehiculo(String texto) {
        this.texto = texto;
    }

    // enum → texto de la base (al guardar)
    public String getTexto() {
        return texto;
    }

    // texto de la base → enum (al leer)
    public static EstadoVehiculo desdeTexto(String texto) {
        for (EstadoVehiculo e : values()) {
            if (e.texto.equalsIgnoreCase(texto)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado de vehículo desconocido: " + texto);
    }
}