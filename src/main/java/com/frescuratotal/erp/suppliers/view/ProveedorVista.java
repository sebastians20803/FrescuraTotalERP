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
                    2. Listar Proveedores.
                    3. Desactivar Proveedor.
                    4. Actualizar Proveedor.
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
                case 3->{
                    desactivar();
                }
                case 4->{
                    actualizar();
                }
                case 0->{
                    System.out.println("Volviendo al menu anterior. ");
                }
                default -> System.out.println("Opción no válida.");

            }
        }while (opcion!=0);

    }
    private void registrar(){
        String nombre = ScannerUtils.capturarTexto("Digite el NOMBRE del proveedor: ");
        String nit = ScannerUtils.capturarTexto("Digite el NIT del proveedor ");
        String personaContacto = ScannerUtils.capturarTexto("Digite la PERSONA DE CONTACTO del proveedor ");
        String email = ScannerUtils.capturarTexto("Digite el EMAIL del proveedor ");
        String telefono = ScannerUtils.capturarTexto("Digite el TELÉFONO del proveedor ");
        String direccion = ScannerUtils.capturarTexto("Digite la DIRECCIÓN del proveedor ");

        String mensaje = controller.registrarProveedor(nombre, nit, personaContacto, email, telefono, direccion);
        System.out.println(mensaje);

    }

    private void listar(){
        List<Proveedor> lista = controller.listarProveedores();
        if (lista.isEmpty()){
            System.out.println("No hay proveedores registrados");
        }else {
            for (Proveedor p : lista)
                System.out.println(p);
        }
    }

    private void desactivar(){
        int idADesactivar = ScannerUtils.capturarNumero("Digite el id del proveedor a desactivar");
        if (controller.desactivarProveedor(idADesactivar)){
            System.out.println("Proveedor desactivado correctamente.");
        }else{
            System.out.println("No existe un proveedor con ese ID");
        }

    }

    private void actualizar(){
        int idAActualizar = ScannerUtils.capturarNumero("Digite el id del proveedor a actualizar");
        Proveedor p = controller.buscarPorId(idAActualizar);

        if (p==null){
            System.out.println("No existe un proveedor con ese ID. ");
            return;
        }

        System.out.println(p + "\n");
        System.out.println("Presione Enter para conservar el valor actual. ");
        p.setNombre(ScannerUtils.capturarTextoOpcional("Nombre", p.getNombre()));
        p.setNit(ScannerUtils.capturarTextoOpcional("Nit", p.getNit()));
        p.setPersonaContacto(ScannerUtils.capturarTextoOpcional("Persona de contacto", p.getPersonaContacto()));
        p.setEmail(ScannerUtils.capturarTextoOpcional("Email", p.getEmail()));
        p.setTelefono(ScannerUtils.capturarTextoOpcional("Teléfono", p.getTelefono()));
        p.setDireccion(ScannerUtils.capturarTextoOpcional("Dirección", p.getDireccion()));

        String mensaje = controller.actualizarProveedor(p);
        System.out.println(mensaje);



    }







}
