package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.common.LogUtils;
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
            registroCuarentenaDAO.insertarRegistroCuarentena(rC);
            loteDAO.actualizarEstado(idLote,EstadoLote.EN_CUARENTENA);

            LogUtils.warn("Lote " + lote.getNumeroLote() + " puesto en cuarentena. Motivo: " + motivo
                    + ". Registró: " + usuario);
            return "El lote " + lote.getNumeroLote() + " quedó en cuarentena. Bloqueado para la venta hasta su inspección.";
        }catch (SQLException e){
            LogUtils.error("Error al poner en cuarentena el lote " + idLote, e);
            return "Error al poner en cuarentena: " + e.getMessage();
        }
    }
}