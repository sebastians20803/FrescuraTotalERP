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
                        2. Nueva Venta
                        0. Volver.
                        """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar: ");

            switch (opcion){

                case 1->{
                    clienteVista.mostrarMenu();
                }
                case 2->{
                    nuevaVenta();
                }
                case 0->{
                    System.out.println("Volviendo al menu anterior. ");
                }
                default -> System.out.println("Opción no válida.");

            }
        }while (opcion!=0);
    }

    private void nuevaVenta() {

        System.out.println("              REGISTRAR VENTA NUEVA             \n");

        List<Cliente> listaClientes = clienteController.listarTodos();
        if (listaClientes.isEmpty()) {
            System.out.println("No hay Clientes registrados. Registre uno primero.");
            return;
        }
        for (Cliente c : listaClientes) {
            System.out.println(c);
        }
        int idCliente = ScannerUtils.capturarNumero("Digite el id del cliente para la venta ");
        String vendedor = ScannerUtils.capturarTexto("Digite el nombre del vendedor");

        Venta venta = ventaController.crearVenta(idCliente,vendedor);

        if ( venta == null){
            System.out.println("Cliente no encontrado");
            return;
        }

        List<Producto> listaProductos = productoController.listarProductos();
        if (listaProductos.isEmpty()) {
            System.out.println("No hay Productos registrados. Registre uno primero.");
            return;
        }

        for (Producto p : listaProductos) {
            System.out.println(p);
        }
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
        for (DetalleVenta dV : venta.getDetalles()){
            System.out.println(dV);

        }
        System.out.println("Subtotal:   $" + venta.getSubtotal());
        System.out.println("Descuento: -$" + venta.getTotalDescuento());
        System.out.println("IVA:        $" + venta.getTotalImpuesto());
        System.out.println("TOTAL:      $" + venta.getMontoTotal());
        System.out.println("\n");

        String r;
        do {
            r = ScannerUtils.capturarTexto("¿Confirmar venta? (s/n)").trim();
        } while (!r.equalsIgnoreCase("s") && !r.equalsIgnoreCase("n"));
        boolean aprobado = r.equalsIgnoreCase("s");
        if(aprobado){
            System.out.println(ventaController.confirmarVenta(venta));
            if (venta.getIdVenta() > 0) {
                System.out.println(facturaController.generarFactura(venta));
            }
        }else{
            System.out.println("Venta cancelada. ");
        }

    }



}
