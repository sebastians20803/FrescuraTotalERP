package com.frescuratotal.erp.sales.controller;

import com.frescuratotal.erp.inventory.model.EstadoLote;
import com.frescuratotal.erp.inventory.model.Lote;
import com.frescuratotal.erp.inventory.model.LoteDAO;
import com.frescuratotal.erp.sales.model.ClienteDAO;
import com.frescuratotal.erp.sales.model.DetalleVenta;
import com.frescuratotal.erp.sales.model.Venta;
import com.frescuratotal.erp.sales.model.VentaDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentaController {
    LoteDAO loteDAO = new LoteDAO();
    ClienteDAO clienteDAO = new ClienteDAO();
    VentaDAO ventaDAO = new VentaDAO();

    private static final double TASA_IVA = 0.19;

    public String agregarProducto(Venta venta, int idProducto, double cantidadPedida) {
        if (!(cantidadPedida >0) ){
            return "La cantidad pedida debe ser mayor a cero";
        }

        try {
            List<Lote> lotes = loteDAO.listarVendiblesPorProducto(idProducto);
            double cantidadTotalLotes = 0;
            for (Lote l : lotes){
                cantidadTotalLotes += l.getCantidad();
            }
            if (cantidadPedida>cantidadTotalLotes){
                return "Stock insuficiente. Disponible: " + cantidadTotalLotes;
            }



        double falta = cantidadPedida;
        for (Lote l : lotes) {

            double descuento = 0;
            if (falta == 0){
                break;
            }
            double tomar = Math.min(falta,l.getCantidad());
            if (l.getEstadoLote() == EstadoLote.PROXIMO_A_VENCER){
                 descuento = 0.50;
            }
            venta.agregarDetalle(new DetalleVenta(l.getIdLote(), tomar, l.getPrecioVenta(), descuento, TASA_IVA));
            falta = falta - tomar;
        }

        return "Agregado: " + cantidadPedida + " del producto " + idProducto;

        } catch (SQLException e) {
            return "Error al agregar producto: " + e.getMessage();
        }
    }

    public Venta crearVenta(int idCliente, String usuario) {
        try {
            if(clienteDAO.buscarPorId(idCliente) == null){
                return null;
            }
             String numero = "VTA-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            return new Venta(numero,idCliente,usuario);
        } catch (SQLException e) {
            System.out.println("Error al crear venta: " + e.getMessage());
            return null;
        }
    }


    public String confirmarVenta(Venta venta) {

        if (venta.getDetalles().isEmpty()){
            return "La venta no tiene productos.";
        }

        try {
            int id = ventaDAO.registrarVenta(venta);
            venta.setIdVenta(id);
            return "Venta " + venta.getNumeroVenta() + " registrada. Total: $" + venta.getMontoTotal();
        } catch (SQLException e) {
            return "No se pudo registrar la venta: " + e.getMessage();
        }
    }
}
