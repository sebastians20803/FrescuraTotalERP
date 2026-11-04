package com.frescuratotal.erp.inventory.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.controller.*;
import com.frescuratotal.erp.inventory.model.EstadoLote;
import com.frescuratotal.erp.inventory.model.Lote;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.sales.controller.ClienteController;
import com.frescuratotal.erp.sales.model.Cliente;
import com.frescuratotal.erp.suppliers.controller.ProveedorController;
import com.frescuratotal.erp.suppliers.model.Proveedor;

import java.time.LocalDate;
import java.util.List;

public class LoteVista {
    private ProductoController productoController = new ProductoController();
    private ProveedorController proveedorController = new ProveedorController();
    private LoteController loteController = new LoteController();
    private RegistroCuarentenaController registroCuarentenaController = new RegistroCuarentenaController();
    private InspeccionController inspeccionController = new InspeccionController();
    private ClienteController clienteController = new ClienteController();
    private MermaController mermaController = new MermaController();

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
                    4. Revisar vencimientos.
                    5. Poner lote en cuarentena.
                    6. Inspeccionar lote en cuarentena.
                    7. Trazabilidad de un lote.
                    8. Registrar merma (pérdida de mercancía).
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
                case 4->{
                    String mensaje = loteController.revisarVencimientos();
                    System.out.println(mensaje);
                }
                case 5->{
                    ponerLoteEnCuarentena();
                }
                case 6->{
                    inspeccionar();
                }
                case 7->{
                    trazabilidad();
                }
                case 8->{
                    registrarMerma();
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

    private void ponerLoteEnCuarentena(){
        List<Lote> listaLote = loteController.listarLotes();
        if (listaLote.isEmpty()) {
            System.out.println("No hay lotes registrados. Registre uno primero.");
            return;
        }
        for (Lote lote : listaLote) {
            System.out.println(lote);
        }
        int idLote = ScannerUtils.capturarNumero("Digite el id del lote que desea registrar en cuarentena ");
        String motivo = ScannerUtils.capturarTexto("Digite el motivo del registro ");
        String usuario = ScannerUtils.capturarTexto("Digite el nombre del usuario que está registrando el lote ");

        String mensaje = registroCuarentenaController.ponerEnCuarentena(idLote,motivo,usuario);
        System.out.println(mensaje);

    }

    private void inspeccionar(){
        List<Lote> lista = loteController.listarLotes();
        int contador = 0;
        if (lista.isEmpty()){
            System.out.println("No hay lotes registrados");
            return;
        }
        for (Lote lote : lista){
            if (lote.getEstadoLote() == EstadoLote.EN_CUARENTENA){
                System.out.println(lote);
                contador ++;
            }
        }
        if (contador==0){
            System.out.println("No hay lotes en Cuarentena");
            return;
        }


        int idLote = ScannerUtils.capturarNumero("Digite el idLote a inspeccionar");

        String r;
        do {
            r = ScannerUtils.capturarTexto("¿Aprobado? (s/n)").trim();
        } while (!r.equalsIgnoreCase("s") && !r.equalsIgnoreCase("n"));
        boolean aprobado = r.equalsIgnoreCase("s");

        String nombreInspector = ScannerUtils.capturarTexto("Digite el nombre del inspector");

        String motivoRechazo = null;

        if(!aprobado){
            motivoRechazo = ScannerUtils.capturarTexto("Escriba el motivo del rechazo");
        }

        String notas = ScannerUtils.capturarTexto("Digite las notas de la inspeccion.");

        String mensaje = inspeccionController.inspeccionarLote(idLote,aprobado,nombreInspector,motivoRechazo,notas);
        System.out.println(mensaje);

    }
    private void trazabilidad(){
        int opcionLote = ScannerUtils.capturarNumero("Digite el numero del lote que desea revisar");
        Lote l = loteController.buscarPorId(opcionLote);
        if (l == null){
            System.out.println("No hay un lote con ese numero, intente de nuevo. ");
            return;
        }
        System.out.println("=== LOTE ===");
        System.out.println(l);
        System.out.println("=== PROVIENE DEL PROVEEDOR ===");
        System.out.println(proveedorController.buscarPorId(l.getIdProveedor()));
        System.out.println("=== CLIENTES QUE LO RECIBIERON ===");
        List<Cliente> listaClientes = clienteController.listarPorLote(opcionLote);
        if (listaClientes.isEmpty()){
            System.out.println("Este lote no se ha vendido a ningún cliente.");
            return;
        }
        for (Cliente c : listaClientes){
            System.out.println(c);
        }

    }

    private void registrarMerma() {

        List<Lote> lista = loteController.listarLotes();
        int contador = 0;
        for (Lote lote : lista) {
            if (lote.getCantidad() > 0) {
                System.out.println(lote);
                contador++;
            }
        }
        if (contador == 0) {
            System.out.println("No hay lotes con mercancía disponible.");
            return;
        }

        int idLote = ScannerUtils.capturarNumero("Id del lote");
        double cantidad = ScannerUtils.capturarDecimal("Cantidad perdida");

        System.out.println("1. Vencido");
        System.out.println("2. Dañado");
        System.out.println("3. Otro");
        int opcionMotivo;
        do {
            opcionMotivo = ScannerUtils.capturarNumero("Razón de la merma");
        } while (opcionMotivo < 1 || opcionMotivo > 3);

        String motivo;
        if (opcionMotivo == 1) {
            motivo = "Vencido";
        } else if (opcionMotivo == 2) {
            motivo = "Dañado";
        } else {
            motivo = ScannerUtils.capturarTexto("Describa el motivo");
        }

        String usuario = ScannerUtils.capturarTexto("Usuario que registra la merma");

        System.out.println(mermaController.registrarMerma(idLote, cantidad, motivo, usuario));
    }

}
