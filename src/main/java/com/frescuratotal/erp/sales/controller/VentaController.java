package com.frescuratotal.erp.sales.controller;

import com.frescuratotal.erp.common.LogUtils;
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
    private LoteDAO loteDAO = new LoteDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private VentaDAO ventaDAO = new VentaDAO();

    private static final double TASA_IVA = 0.19;

    public String agregarProducto(Venta venta, int idProducto, double cantidadPedida) {
        if (cantidadPedida <= 0){
            return "La cantidad pedida debe ser mayor a cero";
        }

        try {
            List<Lote> lotes = loteDAO.listarVendiblesPorProducto(idProducto);
            double cantidadTotalLotes = lotes.stream()
                    .mapToDouble(Lote::getCantidad)
                    .sum();

            if (cantidadPedida > cantidadTotalLotes){
                LogUtils.warn("Stock insuficiente del producto " + idProducto + ": pedido " + cantidadPedida
                        + ", disponible " + cantidadTotalLotes);
                return "Stock insuficiente. Disponible: " + cantidadTotalLotes;
            }

            double falta = cantidadPedida;
            for (Lote l : lotes) {
                if (falta == 0){
                    break;
                }
                double descuento = 0;
                double tomar = Math.min(falta, l.getCantidad());
                if (l.getEstadoLote() == EstadoLote.PROXIMO_A_VENCER){
                    descuento = 0.50;
                }
                venta.agregarDetalle(new DetalleVenta(l.getIdLote(), tomar, l.getPrecioVenta(), descuento, TASA_IVA));
                falta = falta - tomar;
            }

            return "Agregado: " + cantidadPedida + " del producto " + idProducto;

        } catch (SQLException e) {
            LogUtils.error("Error al agregar el producto " + idProducto + " a la venta", e);
            return "Error al agregar producto: " + e.getMessage();
        }
    }

    public Venta crearVenta(int idCliente, String usuario) {
        try {
            if(clienteDAO.buscarPorId(idCliente) == null){
                return null;
            }
            String numero = "VTA-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            return new Venta(numero, idCliente, usuario);
        } catch (SQLException e) {
            LogUtils.error("Error al crear la venta para el cliente " + idCliente, e);
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
            LogUtils.info("Venta " + venta.getNumeroVenta() + " registrada (id " + id + "). Cliente "
                    + venta.getIdCliente() + ", " + venta.getDetalles().size() + " línea(s), total $"
                    + venta.getMontoTotal() + ". Vendedor: " + venta.getUsuarioRegistro());
            return "Venta " + venta.getNumeroVenta() + " registrada. Total: $" + venta.getMontoTotal();
        } catch (SQLException e) {
            LogUtils.error("No se pudo registrar la venta " + venta.getNumeroVenta(), e);
            return "No se pudo registrar la venta: " + e.getMessage();
        }
    }
}