package com.frescuratotal.erp.logistics.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.logistics.controller.ConductorController;
import com.frescuratotal.erp.logistics.controller.DespachoController;
import com.frescuratotal.erp.logistics.controller.VehiculoController;
import com.frescuratotal.erp.logistics.model.Conductor;
import com.frescuratotal.erp.logistics.model.Vehiculo;
import com.frescuratotal.erp.sales.model.EstadoVenta;
import com.frescuratotal.erp.sales.model.Venta;

import java.util.List;

public class LogisticaVista {

    private ConductorController conductorController = new ConductorController();
    private VehiculoController vehiculoController = new VehiculoController();
    private DespachoController despachoController = new DespachoController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                -----------------------------------
                             LOGISTICA
                -----------------------------------
                1. Registrar conductor
                2. Listar conductores
                3. Registrar vehiculo
                4. Listar vehiculos.
                5. Preparar venta para despacho
                6. Panel de despachos
                0. Volver
                """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar: ");

            switch (opcion){

                case 1->{
                    registrarConductor();
                }
                case 2->{
                    listarConductores();
                }
                case 3->{
                    registrarVehiculo();
                }
                case 4->{
                    listarVehiculos();
                }
                case 5->{
                    prepararVenta();
                }
                case 6->{
                    panelDespachos();
                }
                case 0->{
                    System.out.println("Volviendo al menu anterior. ");
                }
                default -> System.out.println("Opción no válida.");

            }
        }while (opcion!=0);
    }

    private void registrarConductor(){
        String nombre = ScannerUtils.capturarTexto("Digite el NOMBRE del conductor ");
        String numeroLicencia = ScannerUtils.capturarTexto("Digite el Numero de Licencia del conductor ");
        String telefono = ScannerUtils.capturarTexto("Digite el TELÉFONO del conductor ");
        String email = ScannerUtils.capturarTexto("Digite el EMAIL del conductor ");

        String mensaje = conductorController.registrarConductor(nombre, numeroLicencia, telefono, email);
        System.out.println(mensaje);

    }

    private void listarConductores(){
        List<Conductor> lista = conductorController.listarTodos();
        if (lista.isEmpty()){
            System.out.println("No hay conductores registrados");
        }else {
            for (Conductor c : lista){
                System.out.println(c);
            }
        }
    }

    private void registrarVehiculo(){
        String placa = ScannerUtils.capturarTexto("Digite la PLACA del vehiculo ");
        String modelo = ScannerUtils.capturarTexto("Digite el MODELO del vehiculo ");
        double capacidad = ScannerUtils.capturarDecimal("Digite la capacidad del vehiculo ");

        String mensaje = vehiculoController.registrarVehiculo(placa, modelo, capacidad);
        System.out.println(mensaje);

    }

    private void listarVehiculos(){
        List<Vehiculo> lista = vehiculoController.listarTodos();
        if (lista.isEmpty()){
            System.out.println("No hay vehiculos registrados");
        }else {
            for (Vehiculo c : lista){
                System.out.println(c);
            }
          }

    }

    private void prepararVenta() {
        List<Venta> registradas = despachoController.listarPorEstado(EstadoVenta.REGISTRADA);
        if (registradas.isEmpty()) {
            System.out.println("No hay ventas registradas pendientes de despacho.");
            return;
        }
        for (Venta v : registradas) {
            System.out.println(v);
        }
        int idVenta = ScannerUtils.capturarNumero("Id de la venta a preparar");
        System.out.println(despachoController.prepararVenta(idVenta));
    }

    private void panelDespachos() {
        List<Venta> enPreparacion = despachoController.listarPorEstado(EstadoVenta.EN_PREPARACION);
        if (enPreparacion.isEmpty()) {
            System.out.println("No hay ventas en preparación.");
            return;
        }
        System.out.println("--- PANEL DE DESPACHOS ---");
        for (Venta v : enPreparacion) {
            System.out.println(v);
        }
    }

}
