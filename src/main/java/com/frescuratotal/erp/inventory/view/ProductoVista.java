package com.frescuratotal.erp.inventory.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.controller.CategoriaController;
import com.frescuratotal.erp.inventory.controller.ProductoController;
import com.frescuratotal.erp.inventory.model.Categoria;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.inventory.model.UnidadMedida;

import java.util.List;

public class ProductoVista {
    private ProductoController controller = new ProductoController();
    private CategoriaController controllerCategoria = new CategoriaController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                    -----------------------------------
                                PRODUCTOS
                    -----------------------------------
                    1. Registrar Producto.
                    2. Listar Productos.
                    3. Actualizar Producto.
                    0. Volver.
                    """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar");

            switch (opcion){
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> actualizar();
                case 0 -> System.out.println("Volviendo al menu anterior.");
                default -> System.out.println("Opción no válida.");
            }
        }while (opcion!=0);
    }

    private void registrar(){
        String nombre = ScannerUtils.capturarTexto("Digite el NOMBRE del producto");
        String descripcion = ScannerUtils.capturarTexto("Digite la descripción del producto");

        System.out.println("Categorías disponibles:");
        List<Categoria> listaCategorias = controllerCategoria.listarCategorias();
        listaCategorias.forEach(System.out::println);
        int opcionCategoria = ScannerUtils.capturarNumero("Digite el id de la categoría que desea asignar");

        UnidadMedida[] unidades = UnidadMedida.values();
        System.out.println("Unidades de medida:");
        for (int i = 0; i < unidades.length; i++) {
            System.out.println((i + 1) + ". " + unidades[i]);
        }
        int opcionUnidadMedida;
        do {
            opcionUnidadMedida = ScannerUtils.capturarNumero("Digite la unidad de medida");
            if (opcionUnidadMedida < 1 || opcionUnidadMedida > unidades.length) {
                System.out.println("Opción no válida.");
            }
        } while (opcionUnidadMedida < 1 || opcionUnidadMedida > unidades.length);

        long precioBase = ScannerUtils.capturarLong("Digite el precio base del producto");

        String mensaje = controller.insertarProducto(nombre, descripcion, opcionCategoria,
                unidades[opcionUnidadMedida - 1], precioBase);
        System.out.println(mensaje);
    }

    private void listar(){
        List<Producto> lista = controller.listarProductos();
        if (lista.isEmpty()){
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println(Producto.encabezado());
        lista.forEach(System.out::println);
    }

    private void actualizar(){
        int idAActualizar = ScannerUtils.capturarNumero("Digite el id del producto a actualizar");
        Producto p = controller.buscarPorId(idAActualizar);

        if (p == null){
            System.out.println("No existe un producto con ese ID.");
            return;
        }

        System.out.println(Producto.encabezado());
        System.out.println(p + "\n");
        System.out.println("Presione Enter para conservar el valor actual.");
        p.setNombre(ScannerUtils.capturarTextoOpcional("Nombre", p.getNombre()));
        p.setDescripcion(ScannerUtils.capturarTextoOpcional("Descripción", p.getDescripcion()));
        p.setIdCategoria(elegirCategoria(p.getIdCategoria()));
        p.setUnidadMedida(elegirUnidad(p.getUnidadMedida()));
        p.setPrecioBase(ScannerUtils.capturarLongOpcional("Precio Base", p.getPrecioBase()));

        String mensaje = controller.actualizarProducto(p);
        System.out.println(mensaje);
    }

    private int elegirCategoria(int actual){
        List<Categoria> listaCategorias = controllerCategoria.listarCategorias();
        System.out.println("Categorías disponibles:");
        listaCategorias.forEach(System.out::println);
        int opcion;
        boolean existe;
        do {
            opcion = ScannerUtils.capturarIntOpcional("Categoría", actual);

            final int elegida = opcion;
            existe = listaCategorias.stream()
                    .anyMatch(c -> c.getIdCategoria() == elegida);

            if (!existe) {
                System.out.println("No existe una categoría con ese id.");
            }
        } while (!existe);

        return opcion;
    }

    private UnidadMedida elegirUnidad(UnidadMedida actual) {
        UnidadMedida[] unidades = UnidadMedida.values();

        System.out.println("Unidades de medida:");
        for (int i = 0; i < unidades.length; i++) {
            System.out.println((i + 1) + ". " + unidades[i]);
        }

        int opcion;
        do {
            if (actual == null) {
                opcion = ScannerUtils.capturarNumero("Elija la unidad de medida");
            } else {
                opcion = ScannerUtils.capturarIntOpcional("Unidad de medida", actual.ordinal() + 1);
            }

            if (opcion < 1 || opcion > unidades.length) {
                System.out.println("Opción no válida. Elija entre 1 y " + unidades.length + ".");
            }
        } while (opcion < 1 || opcion > unidades.length);

        return unidades[opcion - 1];
    }
}