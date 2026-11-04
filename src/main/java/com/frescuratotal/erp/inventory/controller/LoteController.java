package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.common.LogUtils;
import com.frescuratotal.erp.inventory.model.*;
import com.frescuratotal.erp.suppliers.model.Proveedor;
import com.frescuratotal.erp.suppliers.model.ProveedorDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoteController {
    private ProveedorDAO proveedorDAO = new ProveedorDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private LoteDAO loteDAO = new LoteDAO();

    public String insertarLote(int idProducto, int idProveedor, LocalDate fechaVencimiento,
                               double cantidadInicial, long precioCompra, long precioVenta,
                               String pasillo, String estante){

        String numeroLote = "LOT-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        if (!fechaVencimiento.isAfter(LocalDate.now())) {
            return "La fecha de vencimiento debe ser posterior a hoy.";
        }
        if (cantidadInicial<=0){
            return "La cantidad inicial debe ser mayor a 0";
        }
        if (precioCompra <= 0) {
            return "El precio de compra debe ser mayor a 0.";
        }
        if (precioVenta<precioCompra){
            return "El precio de venta no puede ser menor que el precio de compra.";
        }

        Lote lote = new Lote(numeroLote,idProducto,idProveedor,fechaVencimiento,cantidadInicial,precioCompra,precioVenta,pasillo,estante);
        lote.setEstadoLote(EstadoLote.calcularEstadoPorFecha(fechaVencimiento));

        try{
            Proveedor proveedor = proveedorDAO.buscarPorId(idProveedor);
            if (proveedor == null){
                return "Id de proveedor no encontrado";
            }
            if (!proveedor.isActivo()) {
                return "El proveedor está inactivo y no puede entregar mercancía.";
            }
            if(productoDAO.buscarPorId(idProducto) == null){
                return "Id de producto no encontrado";
            }

            loteDAO.insertarLote(lote);
            LogUtils.info("Lote " + numeroLote + " recibido: producto " + idProducto + ", proveedor "
                    + idProveedor + ", cantidad " + cantidadInicial + ", estado " + lote.getEstadoLote());
            return "Lote " + numeroLote + " registrado correctamente.";

        } catch (SQLException e) {
            LogUtils.error("Error al registrar el lote " + numeroLote, e);
            return "Error al registrar: " + e.getMessage();
        }
    }

    public List<Lote> listarLotes(){
        try {
            return loteDAO.listarTodos();
        }catch (SQLException e){
            LogUtils.error("Error al listar lotes", e);
            return new ArrayList<>();
        }
    }

    public Lote buscarPorId(int id){
        try {
            return loteDAO.buscarPorId(id);
        }catch (SQLException e){
            LogUtils.error("Error al buscar el lote " + id, e);
            return null;
        }
    }

    public String revisarVencimientos(){
        int contadorVencidos = 0;
        int contadorProximoAVencer = 0;

        try {
            List<Lote> lista = loteDAO.listarTodos();
            if (lista.isEmpty()) {
                return "No hay lotes registrados. Registre uno primero.";
            }

            for (Lote l : lista){
                if (l.getEstadoLote() == EstadoLote.EN_CUARENTENA || l.getEstadoLote() == EstadoLote.VENCIDO) {
                    continue;
                }

                EstadoLote nuevoEstado = EstadoLote.calcularEstadoPorFecha(l.getFechaVencimiento());
                if (nuevoEstado != l.getEstadoLote()){
                    loteDAO.actualizarEstado(l.getIdLote(),nuevoEstado);
                    if (nuevoEstado == EstadoLote.PROXIMO_A_VENCER){
                        contadorProximoAVencer ++;
                    }
                    if (nuevoEstado == EstadoLote.VENCIDO){
                        contadorVencidos++;
                    }
                }
            }

            LogUtils.info("Revisión de vencimientos: " + contadorProximoAVencer + " lote(s) a próximo a vencer, "
                    + contadorVencidos + " lote(s) a vencido");
            return "Revision Completada: "+contadorProximoAVencer+" lote(s) pasaron a Proximo a Vencer, "+contadorVencidos+
                    " lote(s) pasaron a Vencido";
        } catch (SQLException e) {
            LogUtils.error("Error al revisar vencimientos", e);
            return "Error al revisar vencimientos " + e.getMessage();
        }
    }

}