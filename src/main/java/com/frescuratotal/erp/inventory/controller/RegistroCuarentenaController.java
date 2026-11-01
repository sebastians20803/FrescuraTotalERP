package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.inventory.model.*;

import java.sql.SQLException;

public class RegistroCuarentenaController {
    private RegistroCuarentenaDAO registroCuarentenaDAO = new RegistroCuarentenaDAO();
    private LoteDAO loteDAO = new LoteDAO();

    public String ponerEnCuarentena(int idLote, String motivo, String usuario) {
        if (motivo == null || motivo.isBlank()) {
            return  ("El registro debe tener un motivo");
        }
        if (usuario == null || usuario.isBlank()) {
            return  ("El registro debe tener un usuario");
        }


        RegistroCuarentena rC = new RegistroCuarentena(idLote,motivo,usuario);

        try {
            Lote lote = loteDAO.buscarPorId(idLote);
            if (lote == null) {
                return "Lote no encontrado.";
            }
            if (lote.getEstadoLote() != EstadoLote.DISPONIBLE
                    && lote.getEstadoLote() != EstadoLote.PROXIMO_A_VENCER) {
                return "Solo se pueden poner en cuarentena lotes disponibles o próximos a vencer. Estado actual: "
                        + lote.getEstadoLote();
            }
            loteDAO.actualizarEstado(idLote,EstadoLote.EN_CUARENTENA);
            registroCuarentenaDAO.insertarRegistroCuarentena(rC);
            return "El lote " + lote.getNumeroLote() + " quedó en cuarentena. Bloqueado para la venta hasta su inspección.";
        }catch (SQLException e){
            return "Error al poner en cuarentena: " + e.getMessage();
        }

    }

}
