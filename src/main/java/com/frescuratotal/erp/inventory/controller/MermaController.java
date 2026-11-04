package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.common.LogUtils;
import com.frescuratotal.erp.inventory.model.Lote;
import com.frescuratotal.erp.inventory.model.LoteDAO;
import com.frescuratotal.erp.inventory.model.RegistroMerma;
import com.frescuratotal.erp.inventory.model.RegistroMermaDAO;

import java.sql.SQLException;

public class MermaController {
    private LoteDAO loteDAO = new LoteDAO();
    private RegistroMermaDAO registroMermaDAO = new RegistroMermaDAO();

    public String registrarMerma(int idLote, double cantidad, String motivo, String usuario) {
        if (usuario == null || usuario.isBlank()) {
            return  ("Usuario inválido");
        }
        if (motivo == null || motivo.isBlank()) {
            return  ("Motivo inválido");
        }
        try{
            Lote l = loteDAO.buscarPorId(idLote);
            if (l == null ){
                return "No existe el lote ingresado";
            }
            if (cantidad <= 0) {
                return "La cantidad debe ser mayor a 0.";
            }
            if (cantidad > l.getCantidad()) {
                return "No se puede registrar más de lo disponible. Cantidad disponible: " + l.getCantidad();
            }

            RegistroMerma registroMerma = new RegistroMerma(idLote, null, cantidad, motivo, usuario);
            registroMermaDAO.registrarMerma(registroMerma);
            loteDAO.actualizarCantidad(idLote, l.getCantidad() - cantidad);

            LogUtils.warn("Merma de " + cantidad + " en el lote " + l.getNumeroLote() + ". Motivo: " + motivo
                    + ". Registró: " + usuario);
            return "Merma registrada. Quedan " + (l.getCantidad() - cantidad) + " en el lote";

        }catch (SQLException e){
            LogUtils.error("Error al registrar la merma del lote " + idLote, e);
            return "Error al registrar la merma: " + e.getMessage();
        }
    }
}