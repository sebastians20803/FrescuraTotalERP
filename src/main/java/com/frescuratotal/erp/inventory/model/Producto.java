package com.frescuratotal.erp.inventory.model;

import java.math.BigDecimal;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private int idCategoria;
    private UnidadMedida unidadMedida;
    private long precioBase;

    public Producto(int idProducto, String nombre, String descripcion, int idCategoria, UnidadMedida unidadMedida, long precioBase) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.unidadMedida = unidadMedida;
        this.precioBase = precioBase;
    }

    public Producto(String nombre, String descripcion, int idCategoria, UnidadMedida unidadMedida, long precioBase) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.unidadMedida = unidadMedida;
        this.precioBase = precioBase;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public long getPrecioBase() {
        return precioBase;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public void setPrecioBase(long precioBase) {
        this.precioBase = precioBase;
    }

    @Override
    public String toString() {
        return String.format("%-4d %-22.22s %-5d %-8s $%d",
                idProducto, nombre, idCategoria, unidadMedida, precioBase);
    }

    public static String encabezado() {
        return String.format("%-4s %-22s %-5s %-8s %s",
                "ID", "NOMBRE", "CAT.", "UNIDAD", "PRECIO BASE");
    }
}
