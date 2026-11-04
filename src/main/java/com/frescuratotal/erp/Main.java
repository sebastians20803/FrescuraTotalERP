package com.frescuratotal.erp;

import com.frescuratotal.erp.common.LogUtils;
import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.view.InventarioVista;
import com.frescuratotal.erp.inventory.view.ProductoVista;
import com.frescuratotal.erp.logistics.view.LogisticaVista;
import com.frescuratotal.erp.sales.view.VentaVista;
import com.frescuratotal.erp.suppliers.view.ProveedorVista;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProveedorVista proveedorV =  new ProveedorVista();
        InventarioVista inventarioVista = new InventarioVista();
        VentaVista ventaVista = new VentaVista();
        LogisticaVista logisticaVista = new LogisticaVista();
        LogUtils.info("Sistema iniciado");


        int opcion;
        do{
            System.out.println("""
                              ---------------------------------
                                    ERP FRESCURA TOTAL
                              ---------------------------------
                              1. Proveedores
                              2. Inventario
                              3. Ventas
                              4. Logisticas
                              5. Ver registro de actividad
                              0. Salir
                              """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar: ");

            switch (opcion){

                case 1->{
                    proveedorV.mostrarMenu();
                }
                case 2->{
                    inventarioVista.mostrarMenu();
                }
                case 3->{
                    ventaVista.mostrarMenu();
                }
                case 4->{
                    logisticaVista.mostrarMenu();
                }
                case 5->{
                    verRegistroActividad();
                }
                case 0->{
                    LogUtils.info("Sistema cerrado");
                    System.out.println("Saliendo del sistema. ");
                }
                default -> System.out.println("Opción no válida.");

            }

        }while (opcion!=0);

    }


    private static void verRegistroActividad() {
        List<String> lineas = LogUtils.ultimasLineas(20);
        if (lineas.isEmpty()) {
            System.out.println("Todavía no hay actividad registrada.");
            return;
        }
        System.out.println("--- Últimas " + lineas.size() + " acciones (logs/erp.log) ---");
        lineas.forEach(System.out::println);
    }
}
