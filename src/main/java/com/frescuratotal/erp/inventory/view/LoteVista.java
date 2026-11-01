package com.frescuratotal.erp.inventory.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.controller.LoteController;
import com.frescuratotal.erp.inventory.controller.ProductoController;
import com.frescuratotal.erp.inventory.model.Lote;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.suppliers.controller.ProveedorController;
import com.frescuratotal.erp.suppliers.model.Proveedor;

import java.time.LocalDate;
import java.util.List;

public class LoteVista {
    private ProductoController productoController = new ProductoController();
    private ProveedorController proveedorController = new ProveedorController();
    private LoteController loteController = new LoteController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                    -----------------------------------
                                L O T E S
                    -----------------------------------
                    1. Recibir mercancía (registrar lote)
                    2. Listar lotes
                    3. Buscar lote por ID
                    0. Volver
                    """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar: ");

            switch (opcion){

                case 1->{
                    registrar();
                }
                case 2->{
                    listar();
                }
                case 3->{
                    buscarPorId();
                }
                case 0->{
                    System.out.println("Volviendo al menu anterior. ");
                }
                default -> System.out.println("Opción no válida.");

            }
        }while (opcion!=0);

    }

    private void registrar(){
        System.out.println("              REGISTRAR LOTE                 \n");

        List<Producto> listaProductos = productoController.listarProductos();
        if (listaProductos.isEmpty()) {
            System.out.println("No hay productos registrados. Registre uno primero.");
            return;
        }
        for (Producto producto : listaProductos) {
            System.out.println(producto);
        }
        int opcionIdProducto = ScannerUtils.capturarNumero("Digite el id del producto que desea asignar ");

        List<Proveedor> listaProveedores = proveedorController.listarProveedores();
        if (listaProveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados. Registre uno primero. ");
            return;
        }
        for (Proveedor proveedor : listaProveedores) {
            System.out.println(proveedor);
        }
        int opcionIdProveedor = ScannerUtils.capturarNumero("Digite el id del proveedor que desea asignar ");

        LocalDate fechaVencimiento = ScannerUtils.capturarFecha("Digite la fecha de vencimiento ");

        double cantidadInicial = ScannerUtils.capturarDecimal("Digite la cantidad inicial ");
        long precioCompra = ScannerUtils.capturarLong("Digite el precio de compra ");
        long precioVenta = ScannerUtils.capturarLong("Digite el precio de venta ");
        String pasillo = ScannerUtils.capturarTexto("Digite el pasillo ");
        String estante = ScannerUtils.capturarTexto("Digite el estante ");

        String mensaje = loteController.insertarLote(opcionIdProducto,opcionIdProveedor,fechaVencimiento,cantidadInicial,
                                        precioCompra,precioVenta,pasillo,estante);
        System.out.println(mensaje);

    }

    private void listar(){
        List<Lote> lista = loteController.listarLotes();
        if (lista.isEmpty()){
            System.out.println("No hay lotes registrados");
        }else {
            for (Lote lote : lista){
                System.out.println(lote);
            }
        }
    }

    private void buscarPorId(){
        int loteABuscar = ScannerUtils.capturarNumero("Digite el id del lote a buscar. ");

        Lote l = loteController.buscarPorId(loteABuscar);

        if (l==null){
            System.out.println("No existe un lote con ese ID. ");
        }else {
            System.out.println(l);
        }

    }


}
