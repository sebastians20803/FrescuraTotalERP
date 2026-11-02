package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.inventory.model.*;

import java.sql.SQLException;

public class InspeccionController {
    private LoteDAO loteDAO = new LoteDAO();
    private InspeccionCalidadDAO inspeccionCalidadDAO = new InspeccionCalidadDAO();
    private RegistroCuarentenaDAO registroCuarentenaDAO = new RegistroCuarentenaDAO();
    private RegistroMermaDAO registroMermaDAO = new RegistroMermaDAO();


    public String inspeccionarLote ( int idLote, boolean aprobado, String inspector, String motivoRechazo, String notas){

        if (inspector == null || inspector.isBlank()) {
            return  ("Inspector nulo o inválido");
        }
        if (!aprobado && (motivoRechazo == null || motivoRechazo.isBlank())){
            return ("Debe tener un motivo valido");

        }

        try {
            Lote loteAInspeccionar = loteDAO.buscarPorId(idLote);
            if(loteAInspeccionar == null){
                return "No se encontró un Lote con ese ID. ";
            }
            if(!(loteAInspeccionar.getEstadoLote() == EstadoLote.EN_CUARENTENA)){
                return "El lote debe estar en estado de Cuarentena.";
            }

            InspeccionCalidad inspeccion = new InspeccionCalidad(idLote,aprobado,inspector,motivoRechazo,notas);
            int idInspeccion = inspeccionCalidadDAO.insertarInspeccion(inspeccion);

            registroCuarentenaDAO.resolver(idLote,notas);

            if(aprobado){
                 EstadoLote nuevoEstado = EstadoLote.calcularEstadoPorFecha(loteAInspeccionar.getFechaVencimiento());
                 loteDAO.actualizarEstado(idLote, nuevoEstado);
                 return "Lote aprobado. Nuevo estado: " + nuevoEstado;
            }else {
                 RegistroMerma rM = new RegistroMerma(idLote, idInspeccion, loteAInspeccionar.getCantidad(), motivoRechazo, inspector);
                 registroMermaDAO.registrarMerma(rM);
                 loteDAO.actualizarCantidad(idLote, 0);
                 return "Lote rechazado. Merma registrada de " + loteAInspeccionar.getCantidad();
            }



        } catch (SQLException e){
            return "Error en la inspección: " + e.getMessage();
        }




    }




}
