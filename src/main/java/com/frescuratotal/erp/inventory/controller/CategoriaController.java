package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.common.LogUtils;
import com.frescuratotal.erp.inventory.model.Categoria;
import com.frescuratotal.erp.inventory.model.CategoriaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public List<Categoria> listarCategorias(){
        try {
            return categoriaDAO.listarTodos();
        }catch (SQLException e){
            LogUtils.error("Error al listar categorías", e);
            return new ArrayList<>();
        }
    }
}