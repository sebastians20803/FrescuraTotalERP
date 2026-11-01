package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.inventory.model.*;
import com.frescuratotal.erp.suppliers.model.Proveedor;
import com.frescuratotal.erp.suppliers.model.ProveedorDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        // LOT-20261101125930

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


        long dias = ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
        if (dias <= 7) {
            lote.setEstadoLote(EstadoLote.PROXIMO_A_VENCER);
        }

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
            return "Lote " + numeroLote + " registrado correctamente.";

        } catch (SQLException e) {
            return "Error al registrar: " + e.getMessage();
        }

    }

    public List<Lote> listarLotes(){
        try {
            return loteDAO.listarTodos();
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Lote buscarPorId(int id){
        try {
            return loteDAO.buscarPorId(id);
        }catch (SQLException e){
            System.out.println("Error al buscar: " + e.getMessage());
            return null;
        }

    }




}
