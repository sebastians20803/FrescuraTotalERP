package com.frescuratotal.erp.inventory.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Lote {
    private int idLote;
    private String numeroLote;
    private int idProducto;
    private int idProveedor;
    private LocalDateTime fechaIngreso;
    private LocalDate fechaVencimiento;
    private double cantidadInicial;
    private double cantidad;
    private long precioCompra;
    private long precioVenta;
    private String pasillo;
    private String estante;
    private EstadoLote estadoLote;

    public Lote(int idLote, String numeroLote, int idProducto, int idProveedor, LocalDateTime fechaIngreso, LocalDate fechaVencimiento, double cantidadInicial, double cantidad, long precioCompra, long precioVenta, String pasillo, String estante, EstadoLote estadoLote) {
        this.idLote = idLote;
        this.numeroLote = numeroLote;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidadInicial = cantidadInicial;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.pasillo = pasillo;
        this.estante = estante;
        this.estadoLote = estadoLote;
    }

    public Lote(String numeroLote, int idProducto, int idProveedor, LocalDate fechaVencimiento, double cantidadInicial, long precioCompra, long precioVenta, String pasillo, String estante) {
        this.numeroLote = numeroLote;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidadInicial = cantidadInicial;
        this.cantidad = cantidadInicial;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.pasillo = pasillo;
        this.estante = estante;
        this.estadoLote = EstadoLote.DISPONIBLE;
    }

    public int getIdLote() {
        return idLote;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public double getCantidadInicial() {
        return cantidadInicial;
    }

    public double getCantidad() {
        return cantidad;
    }

    public long getPrecioCompra() {
        return precioCompra;
    }

    public long getPrecioVenta() {
        return precioVenta;
    }

    public String getPasillo() {
        return pasillo;
    }

    public String getEstante() {
        return estante;
    }

    public EstadoLote getEstadoLote() {
        return estadoLote;
    }


    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioCompra(long precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setPrecioVenta(long precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public void setPasillo(String pasillo) {
        this.pasillo = pasillo;
    }

    public void setEstadoLote(EstadoLote estadoLote) {
        this.estadoLote = estadoLote;
    }

    @Override
    public String toString() {
        return "Lote{ " +
                "idLote=" + idLote +
                ", numeroLote='" + numeroLote + '\'' +
                ", idProducto=" + idProducto +
                ", idProveedor=" + idProveedor +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaVencimiento=" + fechaVencimiento +
                ", cantidadInicial=" + cantidadInicial +
                ", cantidad=" + cantidad +
                ", precioCompra=" + precioCompra +
                ", precioVenta=" + precioVenta +
                ", pasillo='" + pasillo + '\'' +
                ", estante='" + estante + '\'' +
                ", estadoLote=" + estadoLote +
                '}';
    }

    public long getpreci() {
        return 0;
    }
}
