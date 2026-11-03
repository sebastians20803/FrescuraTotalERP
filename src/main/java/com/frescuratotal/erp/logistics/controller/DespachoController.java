package com.frescuratotal.erp.logistics.controller;

import com.frescuratotal.erp.sales.model.EstadoVenta;
import com.frescuratotal.erp.sales.model.Venta;
import com.frescuratotal.erp.sales.model.VentaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DespachoController {
    private VentaDAO ventaDAO = new VentaDAO();


    public String prepararVenta(int idVenta) {
        try {
            Venta venta = ventaDAO.buscarPorId(idVenta);
            if (venta==null){
                return "No existe una venta con ese ID";
            }
            if (venta.getEstado() != EstadoVenta.REGISTRADA){
                return  "Solo se pueden preparar ventas registradas. Estado actual: " + venta.getEstado();
            }
            ventaDAO.actualizarEstado(idVenta, EstadoVenta.EN_PREPARACION);
            return "Venta " + venta.getNumeroVenta() + " lista para despacho.";

        } catch (SQLException e) {
            return "Error al preparar la venta: " + e.getMessage();
        }

    }

    public List<Venta> listarPorEstado(EstadoVenta estado) {
        try {
            return ventaDAO.listarPorEstado(estado);
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();        }
    }


}
