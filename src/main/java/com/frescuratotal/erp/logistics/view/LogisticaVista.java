package com.frescuratotal.erp.logistics.view;

import com.frescuratotal.erp.common.ScannerUtils;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.logistics.controller.ConductorController;
import com.frescuratotal.erp.logistics.controller.DespachoController;
import com.frescuratotal.erp.logistics.controller.RutaController;
import com.frescuratotal.erp.logistics.controller.VehiculoController;
import com.frescuratotal.erp.logistics.model.Conductor;
import com.frescuratotal.erp.logistics.model.EstadoRuta;
import com.frescuratotal.erp.logistics.model.RutaEntrega;
import com.frescuratotal.erp.logistics.model.Vehiculo;
import com.frescuratotal.erp.sales.model.EstadoVenta;
import com.frescuratotal.erp.sales.model.Venta;

import java.util.ArrayList;
import java.util.List;

public class LogisticaVista {

    private ConductorController conductorController = new ConductorController();
    private VehiculoController vehiculoController = new VehiculoController();
    private DespachoController despachoController = new DespachoController();
    private RutaController rutaController = new RutaController();

    public void mostrarMenu(){
        int opcion;
        do{
            System.out.println(""" 
                -----------------------------------
                             LOGÍSTICA
                -----------------------------------
                1. Registrar conductor.
                2. Listar conductores.
                3. Registrar vehículo.
                4. Listar vehículos.
                5. Preparar venta para despacho.
                6. Panel de despachos.
                7. Crear ruta de entrega.
                8. Listar rutas.
                9. Iniciar ruta (salida).
                10. Completar ruta (entregada).
                0. Volver.
                """);
            opcion = ScannerUtils.capturarNumero("Digite la opción que desea realizar");

            switch (opcion){
                case 1 -> registrarConductor();
                case 2 -> listarConductores();
                case 3 -> registrarVehiculo();
                case 4 -> listarVehiculos();
                case 5 -> prepararVenta();
                case 6 -> panelDespachos();
                case 7 -> crearRuta();
                case 8 -> listarRutas();
                case 9 -> iniciarRuta();
                case 10 -> completarRuta();
                case 0 -> System.out.println("Volviendo al menu anterior.");
                default -> System.out.println("Opción no válida.");
            }
        }while (opcion!=0);
    }

    // ---------- Métodos para mostrar listas con su encabezado ----------

    private void mostrarConductores(List<Conductor> conductores) {
        System.out.println(Conductor.encabezado());
        conductores.forEach(System.out::println);
    }

    private void mostrarVehiculos(List<Vehiculo> vehiculos) {
        System.out.println(Vehiculo.encabezado());
        vehiculos.forEach(System.out::println);
    }

    private void mostrarVentas(List<Venta> ventas) {
        System.out.println(Venta.encabezado());
        ventas.forEach(System.out::println);
    }

    private void mostrarRutas(List<RutaEntrega> rutas) {
        System.out.println(RutaEntrega.encabezado());
        rutas.forEach(System.out::println);
    }

    private List<RutaEntrega> rutasEnEstado(EstadoRuta estado) {
        List<RutaEntrega> resultado = new ArrayList<>();
        for (RutaEntrega r : rutaController.listarRutas()) {
            if (r.getEstado() == estado) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    // ---------- Conductores y vehículos ----------

    private void registrarConductor(){
        String nombre = ScannerUtils.capturarTexto("Digite el NOMBRE del conductor");
        String numeroLicencia = ScannerUtils.capturarTexto("Digite el NÚMERO DE LICENCIA del conductor");
        String telefono = ScannerUtils.capturarTexto("Digite el TELÉFONO del conductor");
        String email = ScannerUtils.capturarTexto("Digite el EMAIL del conductor");

        System.out.println(conductorController.registrarConductor(nombre, numeroLicencia, telefono, email));
    }

    private void listarConductores(){
        List<Conductor> lista = conductorController.listarTodos();
        if (lista.isEmpty()){
            System.out.println("No hay conductores registrados.");
            return;
        }
        mostrarConductores(lista);
    }

    private void registrarVehiculo(){
        String placa = ScannerUtils.capturarTexto("Digite la PLACA del vehículo");
        String modelo = ScannerUtils.capturarTexto("Digite el MODELO del vehículo");
        double capacidad = ScannerUtils.capturarDecimal("Digite la capacidad del vehículo (kg)");

        System.out.println(vehiculoController.registrarVehiculo(placa, modelo, capacidad));
    }

    private void listarVehiculos(){
        List<Vehiculo> lista = vehiculoController.listarTodos();
        if (lista.isEmpty()){
            System.out.println("No hay vehículos registrados.");
            return;
        }
        mostrarVehiculos(lista);
    }

    // ---------- Despachos ----------

    private void prepararVenta() {
        List<Venta> registradas = despachoController.listarPorEstado(EstadoVenta.REGISTRADA);
        if (registradas.isEmpty()) {
            System.out.println("No hay ventas registradas pendientes de despacho.");
            return;
        }
        mostrarVentas(registradas);
        int idVenta = ScannerUtils.capturarNumero("Id de la venta a preparar");
        System.out.println(despachoController.prepararVenta(idVenta));
    }

    private void panelDespachos() {
        List<Venta> enPreparacion = despachoController.listarPorEstado(EstadoVenta.EN_PREPARACION);
        if (enPreparacion.isEmpty()) {
            System.out.println("No hay ventas en preparación.");
            return;
        }
        System.out.println("\n=== PANEL DE DESPACHOS ===");
        mostrarVentas(enPreparacion);
    }

    // ---------- Rutas ----------

    private void crearRuta() {
        List<Venta> enPreparacion = despachoController.listarPorEstado(EstadoVenta.EN_PREPARACION);
        if (enPreparacion.isEmpty()) {
            System.out.println("No hay ventas en preparación para despachar.");
            return;
        }

        String nombre = ScannerUtils.capturarTexto("Nombre de la ruta");
        String usuario = ScannerUtils.capturarTexto("Usuario que crea la ruta");

        mostrarVehiculos(vehiculoController.listarTodos());
        int idVehiculo = ScannerUtils.capturarNumero("Id del vehículo");

        mostrarConductores(conductorController.listarTodos());
        int idConductor = ScannerUtils.capturarNumero("Id del conductor");

        System.out.println("\n=== VENTAS EN PREPARACIÓN ===");
        mostrarVentas(enPreparacion);
        List<Integer> idsVentas = new ArrayList<>();
        while (true) {
            int idVenta = ScannerUtils.capturarNumero("Id de venta a incluir (0 para terminar)");
            if (idVenta == 0) {
                break;
            }
            idsVentas.add(idVenta);
        }

        System.out.println(rutaController.crearRuta(nombre, usuario, idVehiculo, idConductor, idsVentas));
    }

    private void listarRutas(){
        List<RutaEntrega> lista = rutaController.listarRutas();
        if (lista.isEmpty()){
            System.out.println("No hay rutas registradas.");
            return;
        }
        mostrarRutas(lista);
    }

    private void iniciarRuta(){
        List<RutaEntrega> planificadas = rutasEnEstado(EstadoRuta.PLANIFICADA);
        if (planificadas.isEmpty()){
            System.out.println("No hay rutas planificadas para iniciar.");
            return;
        }
        mostrarRutas(planificadas);
        int idRuta = ScannerUtils.capturarNumero("Digite el id de la ruta a iniciar");
        System.out.println(rutaController.iniciarRuta(idRuta));
    }

    private void completarRuta(){
        List<RutaEntrega> enTransito = rutasEnEstado(EstadoRuta.EN_TRANSITO);
        if (enTransito.isEmpty()){
            System.out.println("No hay rutas en tránsito para completar.");
            return;
        }
        mostrarRutas(enTransito);
        int idRuta = ScannerUtils.capturarNumero("Digite el id de la ruta a completar");
        System.out.println(rutaController.completarRuta(idRuta));
    }
}