package com.frescuratotal.erp.logistics.model;

public class Vehiculo {
    private int idVehiculo;
    private String placa;
    private String modelo;
    private double capacidad;      // en kg
    private EstadoVehiculo estado;

    public Vehiculo(int idVehiculo, String placa, String modelo, double capacidad, EstadoVehiculo estado) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.estado = estado;
    }
    public Vehiculo(String placa, String modelo, double capacidad) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.estado = EstadoVehiculo.ACTIVO;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public double getCapacidad() { return capacidad; }
    public EstadoVehiculo getEstado() { return estado; }

    public void setEstado(EstadoVehiculo estado) { this.estado = estado; }

    @Override
    public String toString() {
        return String.format("%-4d %-8s %-18.18s %-10.0f %s",
                idVehiculo, placa, modelo, capacidad, estado);
    }

    public static String encabezado() {
        return String.format("%-4s %-8s %-18s %-10s %s",
                "ID", "PLACA", "MODELO", "CAP. (KG)", "ESTADO");
    }
}