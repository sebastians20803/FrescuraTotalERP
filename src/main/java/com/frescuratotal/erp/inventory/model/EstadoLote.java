package com.frescuratotal.erp.inventory.model;

// IMPORTANTE: el orden debe coincidir con los id de la tabla estados_lote.
// DISPONIBLE = 1, PROXIMO_A_VENCER = 2, VENCIDO = 3, EN_CUARENTENA = 4

public enum EstadoLote {
    DISPONIBLE,
    PROXIMO_A_VENCER,
    VENCIDO,
    EN_CUARENTENA;

    // enum → id de la tabla
    public int getId() {
        return ordinal() + 1;
    }

    // id de la tabla → enum
    public static EstadoLote desdeId(int id) {
        return values()[id - 1];
    }
}
