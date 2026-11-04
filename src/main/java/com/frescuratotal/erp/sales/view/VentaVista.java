package com.frescuratotal.erp.sales.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.controller.ProductoController;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.sales.controller.ClienteController;
import com.frescuratotal.erp.sales.controller.FacturaController;
import com.frescuratotal.erp.sales.controller.VentaController;
import com.frescuratotal.erp.sales.model.Cliente;
import com.frescuratotal.erp.sales.model.DetalleVenta;
import com.frescuratotal.erp.sales.model.Venta;

import java.util.List;

public class VentaVista {
    private ClienteVista clienteVista = new ClienteVista();
    private ClienteController clienteController = new ClienteController();
    private VentaController ventaController = new VentaController();
    private ProductoController productoController = new ProductoController();
    private FacturaController facturaController = new FacturaController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                    -----------------------------------
                                V E N T A S
                    -----------------------------------
                    1. Clientes.
                    2. Nueva venta.
                    0. Volver.
                    """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar");

            switch (opcion){
                case 1 -> clienteVista.mostrarMenu();
                case 2 -> nuevaVenta();
                case 0 -> System.out.println("Volviendo al menu anterior.");
                default -> System.out.println("Opción no válida.");
            }
        }while (opcion!=0);
    }

    private void nuevaVenta() {
        System.out.println("              REGISTRAR VENTA NUEVA             \n");

        // ---------- 1. Cliente ----------
        List<Cliente> listaClientes = clienteController.listarTodos();
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados. Registre uno primero.");
            return;
        }
        System.out.println(Cliente.encabezado());
        listaClientes.forEach(System.out::println);
        int idCliente = ScannerUtils.capturarNumero("Digite el id del cliente para la venta");
        if (clienteController.buscarPorId(idCliente) == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }
        String vendedor = ScannerUtils.capturarTexto("Digite el nombre del vendedor");

        Venta venta = ventaController.crearVenta(idCliente, vendedor);
        if (venta == null){
            System.out.println("No se pudo crear la venta.");
            return;
        }

        // ---------- 2. Productos ----------
        List<Producto> listaProductos = productoController.listarProductos();
        if (listaProductos.isEmpty()) {
            System.out.println("No hay productos registrados. Registre uno primero.");
            return;
        }
        System.out.println(Producto.encabezado());
        listaProductos.forEach(System.out::println);
        while (true) {
            int idProducto = ScannerUtils.capturarNumero("Id del producto (0 para terminar)");
            if (idProducto == 0) {
                break;
            }
            double cantidad = ScannerUtils.capturarDecimal("Cantidad");
            System.out.println(ventaController.agregarProducto(venta, idProducto, cantidad));
        }

        if (venta.getDetalles().isEmpty()){
            System.out.println("Venta cancelada: no se agregaron productos.");
            return;
        }

        // ---------- 3. Resumen ----------
        System.out.println("\n=== RESUMEN DE LA VENTA ===");
        System.out.println(DetalleVenta.encabezado());
        System.out.println(Producto.encabezado());
        venta.getDetalles().forEach(System.out::println);

        System.out.println();
        System.out.println(String.format("%-12s $%d", "Subtotal:", venta.getSubtotal()));
        System.out.println(String.format("%-12s -$%d", "Descuento:", venta.getTotalDescuento()));
        System.out.println(String.format("%-12s $%d", "IVA (19%):", venta.getTotalImpuesto()));
        System.out.println(String.format("%-12s $%d", "TOTAL:", venta.getMontoTotal()));
        System.out.println();

        // ---------- 4. Confirmar ----------
        String r;
        do {
            r = ScannerUtils.capturarTexto("¿Confirmar venta? (s/n)").trim();
        } while (!r.equalsIgnoreCase("s") && !r.equalsIgnoreCase("n"));
        boolean confirmar = r.equalsIgnoreCase("s");

        if (confirmar) {
            System.out.println(ventaController.confirmarVenta(venta));
            if (venta.getIdVenta() > 0) {
                System.out.println(facturaController.generarFactura(venta));
            }
        } else {
            System.out.println("Venta cancelada.");
        }
    }
}