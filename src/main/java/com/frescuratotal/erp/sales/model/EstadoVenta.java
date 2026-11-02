package com.frescuratotal.erp.sales.model;

// IMPORTANTE: el orden debe coincidir con los id de la tabla estados_venta.
// REGISTRADA = 1, EN_PREPARACION = 2, EN_TRANSITO = 3, ENTREGADA = 4, CANCELADA = 5
public enum EstadoVenta {
    REGISTRADA,
    EN_PREPARACION,
    EN_TRANSITO,
    ENTREGADA,
    CANCELADA;

    public int getId() {
        return ordinal() + 1;
    }

    public static EstadoVenta desdeId(int id) {
        return values()[id - 1];
    }
}