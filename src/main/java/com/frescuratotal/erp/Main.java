package com.frescuratotal.erp;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.model.Categoria;
import com.frescuratotal.erp.inventory.model.CategoriaDAO;
import com.frescuratotal.erp.inventory.view.ProductoVista;
import com.frescuratotal.erp.suppliers.model.ProveedorDAO;
import com.frescuratotal.erp.suppliers.view.ProveedorVista;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        ProveedorVista proveedorV =  new ProveedorVista();
        ProductoVista productoVista = new ProductoVista();



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
                              0. Salir
                              """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar: ");

            switch (opcion){

                case 1->{
                    proveedorV.mostrarMenu();
                }
                case 2->{
                    productoVista.mostrarMenu();
                }
                case 3->{
                    return;
                }
                case 4->{
                    return;
                }
                case 0->{
                    System.out.println("Saliendo del sistema. ");
                }
                default -> System.out.println("Opción no válida.");

            }
        }while (opcion!=0);




    }

}