package com.frescuratotal.erp.sales.controller;

import com.frescuratotal.erp.common.InformacionEmpresa;
import com.frescuratotal.erp.common.InformacionEmpresaDAO;
import com.frescuratotal.erp.common.LogUtils;
import com.frescuratotal.erp.inventory.model.Lote;
import com.frescuratotal.erp.inventory.model.LoteDAO;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.inventory.model.ProductoDAO;
import com.frescuratotal.erp.sales.model.*;
import com.frescuratotal.erp.sales.view.FacturaImagen;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FacturaController {
    private InformacionEmpresaDAO informacionEmpresaDAO = new InformacionEmpresaDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private LoteDAO loteDAO = new LoteDAO();
    private ProductoDAO productoDAO = new ProductoDAO();

    private String generarCufe(String numeroFactura, LocalDateTime fecha, long total, String nitEmpresa) {
        try {
            // 1. Unir los datos de la factura en un solo texto
            String datos = numeroFactura + fecha + total + nitEmpresa;

            // 2. Calcular el hash SHA-384 → da 48 bytes
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] hash = md.digest(datos.getBytes(StandardCharsets.UTF_8));

            // 3. Convertir esos bytes a texto hexadecimal (0-9, a-f)
            StringBuilder cufe = new StringBuilder();
            for (byte b : hash) {
                cufe.append(String.format("%02x", b));
            }
            return cufe.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-384 no disponible", e);
        }
    }

    public String generarFactura(Venta venta) {
        try {
            InformacionEmpresa empresa = informacionEmpresaDAO.obtener();

            String numeroFactura = "FE-" + venta.getIdVenta();
            LocalDateTime fecha = LocalDateTime.now();

            String cufe = generarCufe(numeroFactura, fecha, venta.getMontoTotal(), empresa.getNit());
            String enlaceQr = "https://catalogo-vpfe.dian.gov.co/document/searchqr?documentkey=" + cufe;

            Factura factura = new Factura(venta, numeroFactura, cufe, enlaceQr, fecha);
            int idFactura = facturaDAO.insertar(factura);
            factura.setIdFactura(idFactura);

            Cliente cliente = clienteDAO.buscarPorId(venta.getIdCliente());

            List<String> nombres = new ArrayList<>();
            for (DetalleVenta d : venta.getDetalles()) {
                Lote lote = loteDAO.buscarPorId(d.getIdLote());
                Producto producto = productoDAO.buscarPorId(lote.getIdProducto());
                nombres.add(producto.getNombre());
            }

            String ruta = FacturaImagen.generar(factura, venta, empresa, cliente, nombres);
            LogUtils.info("Factura " + numeroFactura + " generada para la venta " + venta.getNumeroVenta()
                    + ". Total: $" + venta.getMontoTotal() + ". Archivo: " + ruta);
            return "Factura " + numeroFactura + " generada en: " + ruta;

        } catch (SQLException e) {
            LogUtils.error("Error al generar la factura de la venta " + venta.getNumeroVenta(), e);
            return "Error al generar la factura: " + e.getMessage();
        } catch (IOException | WriterException e) {
            LogUtils.error("Error al crear la imagen de la factura de la venta " + venta.getNumeroVenta(), e);
            return "Error al crear la imagen de la factura: " + e.getMessage();
        }
    }
}