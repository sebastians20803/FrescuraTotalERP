package com.frescuratotal.erp.inventory.view;

import com.frescuratotal.erp.common.ScannerUtils;

public class InventarioVista {
    private ProductoVista productoVista = new ProductoVista();
    private LoteVista loteVista = new LoteVista();

    public void mostrarMenu() {
        int opcion;
        do{
            System.out.println(""" 
                    -----------------------------------
                                INVENTARIO
                    -----------------------------------
                    1. Productos
                    2. Lotes
                    0. Volver
                    """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar: ");

            switch (opcion){

                case 1->{
                    productoVista.mostrarMenu();
                }
                case 2->{
                    loteVista.mostrarMenu();
                }
                case 0->{
                    System.out.println("Volviendo al menu anterior ");
                }
                default -> System.out.println("Opción no válida");

            }
        }while (opcion!=0);
    }
}