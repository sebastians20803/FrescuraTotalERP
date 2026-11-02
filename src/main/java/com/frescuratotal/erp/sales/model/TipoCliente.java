package com.frescuratotal.erp.sales.model;

public enum TipoCliente {
    MAYORISTA,
    MINORISTA;

    public int getId() {
        return ordinal() + 1;
    }

    public static TipoCliente desdeId(int id) {
        return values()[id - 1];
    }
}