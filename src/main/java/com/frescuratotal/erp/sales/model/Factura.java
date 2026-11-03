package com.frescuratotal.erp.sales.model;

import java.time.LocalDateTime;

public class Factura {
    private int idFactura;
    private int idVenta;
    private String numeroFactura;
    private String cufe;
    private String enlaceQr;
    private LocalDateTime fechaEmision;
    private long subtotal;
    private long totalDescuento;
    private long montoImpuesto;
    private long montoTotal;

    public Factura(int idFactura, int idVenta, String numeroFactura, String cufe, String enlaceQr,
                   LocalDateTime fechaEmision, long subtotal, long totalDescuento,
                   long montoImpuesto, long montoTotal) {
        this.idFactura = idFactura;
        this.idVenta = idVenta;
        this.numeroFactura = numeroFactura;
        this.cufe = cufe;
        this.enlaceQr = enlaceQr;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.totalDescuento = totalDescuento;
        this.montoImpuesto = montoImpuesto;
        this.montoTotal = montoTotal;
    }

    public Factura(Venta venta, String numeroFactura, String cufe, String enlaceQr, LocalDateTime fechaEmision) {
        this.idVenta = venta.getIdVenta();
        this.numeroFactura = numeroFactura;
        this.cufe = cufe;
        this.enlaceQr = enlaceQr;
        this.fechaEmision = fechaEmision;
        this.subtotal = venta.getSubtotal();
        this.totalDescuento = venta.getTotalDescuento();
        this.montoImpuesto = venta.getTotalImpuesto();
        this.montoTotal = venta.getMontoTotal();
    }

    public int getIdFactura() { return idFactura; }
    public int getIdVenta() { return idVenta; }
    public String getNumeroFactura() { return numeroFactura; }
    public String getCufe() { return cufe; }
    public String getEnlaceQr() { return enlaceQr; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public long getSubtotal() { return subtotal; }
    public long getTotalDescuento() { return totalDescuento; }
    public long getMontoImpuesto() { return montoImpuesto; }
    public long getMontoTotal() { return montoTotal; }

    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    @Override
    public String toString() {
        return "Factura " + numeroFactura + " | Venta " + idVenta + " | Total $" + montoTotal;
    }
}