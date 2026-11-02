package com.frescuratotal.erp.sales.model;

public class DetalleVenta {
    private int idDetalle;
    private int idVenta;
    private int idLote;
    private double cantidadVendida;
    private long precioUnitario;
    private double descuentoAplicado;
    private long montoDescuento;
    private double tasaImpuesto;
    private long montoImpuesto;
    private long subtotal;
    private long totalLinea;

    public DetalleVenta(int idDetalle, int idVenta, int idLote, double cantidadVendida, long precioUnitario,
                        double descuentoAplicado, long montoDescuento, double tasaImpuesto,
                        long montoImpuesto, long subtotal, long totalLinea) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idLote = idLote;
        this.cantidadVendida = cantidadVendida;
        this.precioUnitario = precioUnitario;
        this.descuentoAplicado = descuentoAplicado;
        this.montoDescuento = montoDescuento;
        this.tasaImpuesto = tasaImpuesto;
        this.montoImpuesto = montoImpuesto;
        this.subtotal = subtotal;
        this.totalLinea = totalLinea;
    }

    public DetalleVenta(int idLote, double cantidadVendida, long precioUnitario,
                        double descuentoAplicado, double tasaImpuesto) {
        this.idLote = idLote;
        this.cantidadVendida = cantidadVendida;
        this.precioUnitario = precioUnitario;
        this.descuentoAplicado = descuentoAplicado;
        this.tasaImpuesto = tasaImpuesto;

        this.subtotal = Math.round(cantidadVendida * precioUnitario);
        this.montoDescuento = Math.round(subtotal * descuentoAplicado);
        long baseGravable = subtotal - montoDescuento;
        this.montoImpuesto = Math.round(baseGravable * tasaImpuesto);
        this.totalLinea = baseGravable + montoImpuesto;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public int getIdLote() {
        return idLote;
    }

    public double getCantidadVendida() {
        return cantidadVendida;
    }

    public long getPrecioUnitario() {
        return precioUnitario;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public long getMontoDescuento() {
        return montoDescuento;
    }

    public double getTasaImpuesto() {
        return tasaImpuesto;
    }

    public long getMontoImpuesto() {
        return montoImpuesto;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public long getTotalLinea() {
        return totalLinea;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public void setCantidadVendida(double cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public void setPrecioUnitario(long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "idDetalle=" + idDetalle +
                ", idVenta=" + idVenta +
                ", idLote=" + idLote +
                ", cantidadVendida=" + cantidadVendida +
                ", precioUnitario=" + precioUnitario +
                ", descuentoAplicado=" + descuentoAplicado +
                ", montoDescuento=" + montoDescuento +
                ", tasaImpuesto=" + tasaImpuesto +
                ", montoImpuesto=" + montoImpuesto +
                ", subtotal=" + subtotal +
                ", totalLinea=" + totalLinea +
                '}';
    }
}