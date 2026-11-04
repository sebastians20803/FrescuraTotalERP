package com.frescuratotal.erp.sales.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.sales.controller.ClienteController;
import com.frescuratotal.erp.sales.model.Cliente;
import com.frescuratotal.erp.sales.model.TipoCliente;

import java.util.List;

public class ClienteVista {
    private ClienteController clienteController = new ClienteController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                    -----------------------------------
                                 CLIENTES
                    -----------------------------------
                    1. Registrar cliente.
                    2. Listar clientes.
                    3. Buscar por ID.
                    0. Volver.
                    """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar");

            switch (opcion){
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 0 -> System.out.println("Volviendo al menu anterior.");
                default -> System.out.println("Opción no válida.");
            }
        }while (opcion!=0);
    }

    private void registrar(){
        String nombre = ScannerUtils.capturarTexto("Digite el NOMBRE del cliente");
        String documento = ScannerUtils.capturarTexto("Digite el DOCUMENTO o NIT del cliente");
        String email = ScannerUtils.capturarTexto("Digite el EMAIL del cliente");
        String telefono = ScannerUtils.capturarTexto("Digite el TELÉFONO del cliente");
        String direccion = ScannerUtils.capturarTexto("Digite la DIRECCIÓN del cliente");

        TipoCliente[] tipos = TipoCliente.values();
        System.out.println("Tipos de cliente:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        int opcionTipoCliente;
        do {
            opcionTipoCliente = ScannerUtils.capturarNumero("Digite el tipo de cliente");
            if (opcionTipoCliente < 1 || opcionTipoCliente > tipos.length) {
                System.out.println("Opción no válida.");
            }
        } while (opcionTipoCliente < 1 || opcionTipoCliente > tipos.length);

        String mensaje = clienteController.registrarCliente(nombre, documento, email, telefono, direccion,
                TipoCliente.desdeId(opcionTipoCliente));
        System.out.println(mensaje);
    }

    private void listar(){
        List<Cliente> lista = clienteController.listarTodos();
        if (lista.isEmpty()){
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println(Cliente.encabezado());
        lista.forEach(System.out::println);
    }

    private void buscarPorId(){
        int clienteABuscar = ScannerUtils.capturarNumero("Digite el id del cliente a buscar");
        Cliente c = clienteController.buscarPorId(clienteABuscar);

        if (c == null){
            System.out.println("No existe un cliente con ese ID.");
            return;
        }
        System.out.println(Cliente.encabezado());
        System.out.println(c);
    }
}