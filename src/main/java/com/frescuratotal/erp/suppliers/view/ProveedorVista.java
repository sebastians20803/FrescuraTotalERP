package com.frescuratotal.erp.suppliers.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.controller.ProductoController;
import com.frescuratotal.erp.inventory.model.Lote;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.suppliers.controller.ProveedorController;
import com.frescuratotal.erp.suppliers.model.Proveedor;

import java.util.List;

public class ProveedorVista {
    private ProveedorController proveedorController = new ProveedorController();
    private ProductoController productoController = new ProductoController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                    -----------------------------------
                                PROVEEDORES
                    -----------------------------------
                    1. Registrar Proveedor.
                    2. Listar Proveedores.
                    3. Desactivar Proveedor.
                    4. Actualizar Proveedor.
                    5. Ver catálogo de un proveedor.
                    6. Agregar producto al catálogo.
                    7. Quitar producto del catálogo.
                    8. Historial de compras (lotes entregados).
                    0. Volver.
                    """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar");

            switch (opcion){
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> desactivar();
                case 4 -> actualizar();
                case 5 -> verCatalogo();
                case 6 -> agregarAlCatalogo();
                case 7 -> quitarDelCatalogo();
                case 8 -> historialCompras();
                case 0 -> System.out.println("Volviendo al menu anterior.");
                default -> System.out.println("Opción no válida.");
            }
        }while (opcion!=0);
    }

    private void registrar(){
        String nombre = ScannerUtils.capturarTexto("Digite el NOMBRE del proveedor");
        String nit = ScannerUtils.capturarTexto("Digite el NIT del proveedor");
        String personaContacto = ScannerUtils.capturarTexto("Digite la PERSONA DE CONTACTO del proveedor");
        String email = ScannerUtils.capturarTexto("Digite el EMAIL del proveedor");
        String telefono = ScannerUtils.capturarTexto("Digite el TELÉFONO del proveedor");
        String direccion = ScannerUtils.capturarTexto("Digite la DIRECCIÓN del proveedor");

        String mensaje = proveedorController.registrarProveedor(nombre, nit, personaContacto, email, telefono, direccion);
        System.out.println(mensaje);
    }

    private void listar(){
        List<Proveedor> lista = proveedorController.listarProveedores();
        if (lista.isEmpty()){
            System.out.println("No hay proveedores registrados.");
            return;
        }
        System.out.println(Proveedor.encabezado());
        for (Proveedor p : lista) {
            System.out.println(p);
        }
    }

    private void desactivar(){
        mostrarProveedores();
        int idADesactivar = ScannerUtils.capturarNumero("Digite el id del proveedor a desactivar");
        if (proveedorController.desactivarProveedor(idADesactivar)){
            System.out.println("Proveedor desactivado correctamente.");
        }else{
            System.out.println("No existe un proveedor con ese ID.");
        }
    }

    private void actualizar(){
        int idAActualizar = ScannerUtils.capturarNumero("Digite el id del proveedor a actualizar");
        Proveedor p = proveedorController.buscarPorId(idAActualizar);

        if (p == null){
            System.out.println("No existe un proveedor con ese ID.");
            return;
        }

        System.out.println(Proveedor.encabezado());
        System.out.println(p + "\n");
        System.out.println("Presione Enter para conservar el valor actual.");
        p.setNombre(ScannerUtils.capturarTextoOpcional("Nombre", p.getNombre()));
        p.setNit(ScannerUtils.capturarTextoOpcional("Nit", p.getNit()));
        p.setPersonaContacto(ScannerUtils.capturarTextoOpcional("Persona de contacto", p.getPersonaContacto()));
        p.setEmail(ScannerUtils.capturarTextoOpcional("Email", p.getEmail()));
        p.setTelefono(ScannerUtils.capturarTextoOpcional("Teléfono", p.getTelefono()));
        p.setDireccion(ScannerUtils.capturarTextoOpcional("Dirección", p.getDireccion()));

        String mensaje = proveedorController.actualizarProveedor(p);
        System.out.println(mensaje);
    }

    private void mostrarProveedores() {
        System.out.println(Proveedor.encabezado());
        for (Proveedor p : proveedorController.listarProveedores()) {
            System.out.println(p);
        }
    }

    private void mostrarProductos(List<Producto> productos) {
        System.out.println(Producto.encabezado());
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    private void verCatalogo() {
        mostrarProveedores();
        int idProveedor = ScannerUtils.capturarNumero("Id del proveedor");

        List<Producto> catalogo = proveedorController.verCatalogo(idProveedor);
        if (catalogo.isEmpty()) {
            System.out.println("Este proveedor no tiene productos en su catálogo.");
            return;
        }
        System.out.println("\n=== CATÁLOGO DEL PROVEEDOR " + idProveedor + " ===");
        mostrarProductos(catalogo);
    }

    private void agregarAlCatalogo() {
        mostrarProveedores();
        int idProveedor = ScannerUtils.capturarNumero("Id del proveedor");

        mostrarProductos(productoController.listarProductos());
        int idProducto = ScannerUtils.capturarNumero("Id del producto a agregar");

        System.out.println(proveedorController.agregarAlCatalogo(idProveedor, idProducto));
    }

    private void quitarDelCatalogo() {
        mostrarProveedores();
        int idProveedor = ScannerUtils.capturarNumero("Id del proveedor");

        List<Producto> catalogo = proveedorController.verCatalogo(idProveedor);
        if (catalogo.isEmpty()) {
            System.out.println("Este proveedor no tiene productos en su catálogo.");
            return;
        }
        mostrarProductos(catalogo);
        int idProducto = ScannerUtils.capturarNumero("Id del producto a quitar");

        System.out.println(proveedorController.quitarDelCatalogo(idProveedor, idProducto));
    }

    private void historialCompras() {
        mostrarProveedores();
        int idProveedor = ScannerUtils.capturarNumero("Id del proveedor");

        List<Lote> lotes = proveedorController.historialCompras(idProveedor);
        if (lotes.isEmpty()) {
            System.out.println("Este proveedor no ha entregado lotes.");
            return;
        }
        System.out.println("\n=== LOTES ENTREGADOS POR EL PROVEEDOR " + idProveedor + " ===");
        System.out.println(Lote.encabezado());
        for (Lote l : lotes) {
            System.out.println(l);
        }
    }
}