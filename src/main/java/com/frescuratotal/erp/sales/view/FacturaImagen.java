package com.frescuratotal.erp.sales.view;

import com.frescuratotal.erp.common.InformacionEmpresa;
import com.frescuratotal.erp.sales.model.Cliente;
import com.frescuratotal.erp.sales.model.DetalleVenta;
import com.frescuratotal.erp.sales.model.Factura;
import com.frescuratotal.erp.sales.model.Venta;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FacturaImagen {
    private static final int ANCHO = 800;
    private static final int ALTO = 1100;
    private static final int MARGEN = 40;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final Font TITULO = new Font("SansSerif", Font.BOLD, 24);
    private static final Font SUBTITULO = new Font("SansSerif", Font.BOLD, 15);
    private static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font PEQUENA = new Font("Monospaced", Font.PLAIN, 11);

    // nombresProductos va en el mismo orden que venta.getDetalles()
    public static String generar(Factura f, Venta v, InformacionEmpresa e, Cliente c,
                                 List<String> nombresProductos) throws IOException, WriterException {

        // 1. Hoja en blanco
        BufferedImage img = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ANCHO, ALTO);
        g.setColor(Color.BLACK);

        // 2. Encabezado: la empresa a la izquierda, los datos de la factura a la derecha
        int y = 60;
        g.setFont(TITULO);
        g.drawString(e.getNombreEmpresa(), MARGEN, y);
        g.setFont(SUBTITULO);
        g.drawString("FACTURA ELECTRÓNICA DE VENTA", 480, y);

        g.setFont(NORMAL);
        y += 25;
        g.drawString("NIT: " + e.getNit(), MARGEN, y);
        g.drawString("No. " + f.getNumeroFactura(), 480, y);
        y += 20;
        g.drawString(e.getDireccion() + " - " + e.getCiudad(), MARGEN, y);
        g.drawString("Fecha: " + f.getFechaEmision().format(FORMATO_FECHA), 480, y);
        y += 20;
        g.drawString("Tel: " + e.getTelefono() + "  |  " + e.getEmail(), MARGEN, y);

        y += 20;
        g.drawLine(MARGEN, y, ANCHO - MARGEN, y);

        // 3. Datos del cliente
        y += 30;
        g.setFont(SUBTITULO);
        g.drawString("CLIENTE", MARGEN, y);
        g.setFont(NORMAL);
        y += 22;
        g.drawString("Nombre: " + c.getNombre(), MARGEN, y);
        g.drawString("Documento: " + c.getDocumento(), 480, y);
        y += 20;
        g.drawString("Dirección: " + c.getDireccion(), MARGEN, y);
        g.drawString("Teléfono: " + c.getTelefono(), 480, y);

        y += 20;
        g.drawLine(MARGEN, y, ANCHO - MARGEN, y);

        // 4. Tabla de productos
        y += 25;
        g.setFont(SUBTITULO);
        g.drawString("Producto", MARGEN, y);
        g.drawString("Cant.", 290, y);
        g.drawString("Precio", 360, y);
        g.drawString("Desc.", 460, y);
        g.drawString("IVA", 560, y);
        g.drawString("Total", 660, y);
        y += 8;
        g.drawLine(MARGEN, y, ANCHO - MARGEN, y);

        g.setFont(NORMAL);
        List<DetalleVenta> detalles = v.getDetalles();
        for (int i = 0; i < detalles.size(); i++) {
            DetalleVenta d = detalles.get(i);
            String nombre = nombresProductos.get(i);
            if (nombre.length() > 30) {
                nombre = nombre.substring(0, 30);      // recortar nombres muy largos
            }
            y += 22;
            g.drawString(nombre, MARGEN, y);
            g.drawString(String.valueOf(d.getCantidadVendida()), 290, y);
            g.drawString(pesos(d.getPrecioUnitario()), 360, y);
            g.drawString(pesos(d.getMontoDescuento()), 460, y);
            g.drawString(pesos(d.getMontoImpuesto()), 560, y);
            g.drawString(pesos(d.getTotalLinea()), 660, y);
        }

        y += 15;
        g.drawLine(MARGEN, y, ANCHO - MARGEN, y);

        // 5. Totales
        y += 25;
        g.drawString("Subtotal:", 520, y);
        g.drawString(pesos(f.getSubtotal()), 660, y);
        y += 20;
        g.drawString("Descuento:", 520, y);
        g.drawString("-" + pesos(f.getTotalDescuento()), 660, y);
        y += 20;
        g.drawString("IVA (19%):", 520, y);
        g.drawString(pesos(f.getMontoImpuesto()), 660, y);
        y += 25;
        g.setFont(SUBTITULO);
        g.drawString("TOTAL:", 520, y);
        g.drawString(pesos(f.getMontoTotal()), 660, y);

        // 6. QR + CUFE
        y += 40;
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matriz = writer.encode(f.getEnlaceQr(), BarcodeFormat.QR_CODE, 170, 170);
        BufferedImage qr = MatrixToImageWriter.toBufferedImage(matriz);
        g.drawImage(qr, MARGEN, y, null);

        g.setFont(SUBTITULO);
        g.drawString("CUFE:", 230, y + 30);
        g.setFont(PEQUENA);
        String cufe = f.getCufe();
        g.drawString(cufe.substring(0, 48), 230, y + 55);     // el CUFE tiene 96 caracteres:
        g.drawString(cufe.substring(48), 230, y + 72);        // se parte en dos renglones
        g.setFont(NORMAL);
        g.drawString("Representación gráfica de factura electrónica (simulada)", 230, y + 110);

        g.dispose();     // "terminé de dibujar"

        // 7. Guardar en la carpeta facturas/ del proyecto
        File carpeta = new File("facturas");
        carpeta.mkdirs();                // la crea si no existe
        File archivo = new File(carpeta, f.getNumeroFactura() + ".png");
        ImageIO.write(img, "png", archivo);
        return archivo.getAbsolutePath();
    }

    // 15000 → "$15.000"
    private static String pesos(long valor) {
        return "$" + String.format("%,d", valor).replace(',', '.');
    }
}