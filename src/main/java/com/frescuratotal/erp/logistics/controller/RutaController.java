package com.frescuratotal.erp.logistics.controller;

import com.frescuratotal.erp.common.LogUtils;
import com.frescuratotal.erp.logistics.model.*;
import com.frescuratotal.erp.sales.model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RutaController {
    private RutaDAO rutaDAO = new RutaDAO();
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private ConductorDAO conductorDAO = new ConductorDAO();
    private VentaDAO ventaDAO = new VentaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();

    public String crearRuta(String nombre, String usuario, int idVehiculo, int idConductor,
                            List<Integer> idsVentas) {
        if (nombre == null || nombre.isBlank()) {
            return "La ruta debe tener un nombre.";
        }
        if (idsVentas.isEmpty()) {
            return "La ruta debe incluir al menos una venta.";
        }

        try {
            Vehiculo vehiculo = vehiculoDAO.buscarPorId(idVehiculo);
            if (vehiculo == null) {
                return "No existe un vehículo con ese ID.";
            }
            if (vehiculo.getEstado() != EstadoVehiculo.ACTIVO) {
                return "El vehículo no está disponible. Estado: " + vehiculo.getEstado();
            }
            Conductor conductor = conductorDAO.buscarPorId(idConductor);
            if (conductor == null) {
                return "No existe un conductor con ese ID.";
            }
            if (!conductor.isActivo()) {
                return "El conductor está inactivo.";
            }

            // Cada venta: existe, está en preparación y no está en otra ruta
            List<Venta> ventas = new ArrayList<>();
            List<String> direcciones = new ArrayList<>();

            for (int idVenta : idsVentas) {
                Venta venta = ventaDAO.buscarPorId(idVenta);
                if (venta == null) {
                    return "No existe la venta " + idVenta + ".";
                }
                if (venta.getEstado() != EstadoVenta.EN_PREPARACION) {
                    return "La venta " + venta.getNumeroVenta() + " no está en preparación.";
                }
                if (rutaDAO.ventaEstaEnRuta(idVenta)) {
                    return "La venta " + venta.getNumeroVenta() + " ya está asignada a otra ruta.";
                }
                Cliente cliente = clienteDAO.buscarPorId(venta.getIdCliente());
                ventas.add(venta);
                direcciones.add(cliente.getDireccion());
            }

            int idRuta = rutaDAO.crearRuta(new RutaEntrega(nombre, usuario),
                    idVehiculo, idConductor, ventas, direcciones);

            LogUtils.info("Ruta " + idRuta + " (" + nombre + ") creada por " + usuario + " con "
                    + ventas.size() + " venta(s). Vehículo " + vehiculo.getPlaca()
                    + ", conductor " + conductor.getNombre());
            return "Ruta " + idRuta + " creada con " + ventas.size() + " venta(s). Vehículo "
                    + vehiculo.getPlaca() + ", conductor " + conductor.getNombre() + ".";

        } catch (SQLException e) {
            LogUtils.error("Error al crear la ruta " + nombre, e);
            return "Error al crear la ruta: " + e.getMessage();
        }
    }

    public String iniciarRuta(int idRuta) {
        try {
            RutaEntrega ruta = rutaDAO.buscarPorId(idRuta);
            if (ruta == null) {
                return "No existe una ruta con ese ID.";
            }
            if (ruta.getEstado() != EstadoRuta.PLANIFICADA) {
                return "Solo se pueden iniciar rutas planificadas. Estado actual: " + ruta.getEstado();
            }
            rutaDAO.iniciarRuta(idRuta);
            LogUtils.info("Ruta " + idRuta + " (" + ruta.getNombreRuta() + ") salió a tránsito");
            return "Ruta " + ruta.getNombreRuta() + " en tránsito. Sus ventas pasaron a EN_TRANSITO.";
        } catch (SQLException e) {
            LogUtils.error("Error al iniciar la ruta " + idRuta, e);
            return "Error al iniciar la ruta: " + e.getMessage();
        }
    }

    public String completarRuta(int idRuta) {
        try {
            RutaEntrega ruta = rutaDAO.buscarPorId(idRuta);
            if (ruta == null) {
                return "No existe una ruta con ese ID.";
            }
            if (ruta.getEstado() != EstadoRuta.EN_TRANSITO) {
                return "Solo se pueden completar rutas en tránsito. Estado actual: " + ruta.getEstado();
            }
            rutaDAO.completarRuta(idRuta);
            LogUtils.info("Ruta " + idRuta + " (" + ruta.getNombreRuta() + ") completada. Ventas entregadas");
            return "Ruta " + ruta.getNombreRuta() + " completada. Sus ventas quedaron ENTREGADAS.";
        } catch (SQLException e) {
            LogUtils.error("Error al completar la ruta " + idRuta, e);
            return "Error al completar la ruta: " + e.getMessage();
        }
    }

    public List<RutaEntrega> listarRutas() {
        try {
            return rutaDAO.listarTodas();
        } catch (SQLException e) {
            LogUtils.error("Error al listar rutas", e);
            return new ArrayList<>();
        }
    }
}