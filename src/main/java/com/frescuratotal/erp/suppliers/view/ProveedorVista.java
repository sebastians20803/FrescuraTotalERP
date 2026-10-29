package com.frescuratotal.erp.suppliers.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.suppliers.controller.ProveedorController;
import com.frescuratotal.erp.suppliers.model.Proveedor;

import java.util.List;

public class ProveedorVista {
    private ProveedorController controller = new ProveedorController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                    -----------------------------------
                                PROVEEDORES
                    -----------------------------------
                    1. Registrar Proveedor.
                    2. Listar Proveedores
                    0. Volver.
                    """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar: ");

            switch (opcion){

                case 1->{
                    registrar();
                }
                case 2->{
                    listar();
                }
                case 0->{
                    System.out.println("Volviendo al menu anterior. ");
                }
                default -> System.out.println("Opción no válida.");

            }
        }while (opcion!=0);

    }
    private void registrar(){
        String nombre = ScannerUtils.capturarTexto("Digite el NOMBRE del proveedo: ");
        String nit = ScannerUtils.capturarTexto("Digite el NIT del proveedor ");
        String personaContacto = ScannerUtils.capturarTexto("Digite la PERSONA DE CONTACTO del proveedor ");
        String email = ScannerUtils.capturarTexto("Digite el EMAIL del proveedor ");
        String telefono = ScannerUtils.capturarTexto("Digite el TELÉFONO del proveedor ");
        String direccion = ScannerUtils.capturarTexto("Digite la DIRECCIÓN del proveedor ");

        String mensaje = controller.registrarProveedor(nombre, nit, personaContacto, email, telefono, direccion);
        System.out.println(mensaje);

    }

    private void listar(){
        List<Proveedor> lista = controller.ListarProveedores();
        if (lista.isEmpty()){
            System.out.println("No hay proveedores registrados");
        }else {
            for (Proveedor p : lista)
                System.out.println(p);
        }
    }






}
