package com.frescuratotal.erp.sales.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int idVenta;
    private String numeroVenta;
    private int idCliente;
    private LocalDateTime fechaVenta;
    private long subtotal;
    private long totalDescuento;
    private long totalImpuesto;
    private long montoTotal;
    private EstadoVenta estado;
    private String usuarioRegistro;

    // No está en la tabla: las líneas de esta venta
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta(int idVenta, String numeroVenta, int idCliente, LocalDateTime fechaVenta,
                 long subtotal, long totalDescuento, long totalImpuesto, long montoTotal,
                 EstadoVenta estado, String usuarioRegistro) {
        this.idVenta = idVenta;
        this.numeroVenta = numeroVenta;
        this.idCliente = idCliente;
        this.fechaVenta = fechaVenta;
        this.subtotal = subtotal;
        this.totalDescuento = totalDescuento;
        this.totalImpuesto = totalImpuesto;
        this.montoTotal = montoTotal;
        this.estado = estado;
        this.usuarioRegistro = usuarioRegistro;
    }

    public Venta(String numeroVenta, int idCliente, String usuarioRegistro) {
        this.numeroVenta = numeroVenta;
        this.idCliente = idCliente;
        this.usuarioRegistro = usuarioRegistro;
        this.estado = EstadoVenta.REGISTRADA;
    }

    public void agregarDetalle(DetalleVenta d) {
        detalles.add(d);
        subtotal += d.getSubtotal();
        totalDescuento += d.getMontoDescuento();
        totalImpuesto += d.getMontoImpuesto();
        montoTotal += d.getTotalLinea();
    }

    public int getIdVenta() { return idVenta; }
    public String getNumeroVenta() { return numeroVenta; }
    public int getIdCliente() { return idCliente; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public long getSubtotal() { return subtotal; }
    public long getTotalDescuento() { return totalDescuento; }
    public long getTotalImpuesto() { return totalImpuesto; }
    public long getMontoTotal() { return montoTotal; }
    public EstadoVenta getEstado() { return estado; }
    public String getUsuarioRegistro() { return usuarioRegistro; }
    public List<DetalleVenta> getDetalles() { return detalles; }

    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public void setEstado(EstadoVenta estado) { this.estado = estado; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", numeroVenta='" + numeroVenta + '\'' +
                ", idCliente=" + idCliente +
                ", fechaVenta=" + fechaVenta +
                ", subtotal=" + subtotal +
                ", totalDescuento=" + totalDescuento +
                ", totalImpuesto=" + totalImpuesto +
                ", montoTotal=" + montoTotal +
                ", estado=" + estado +
                '}';
    }
}