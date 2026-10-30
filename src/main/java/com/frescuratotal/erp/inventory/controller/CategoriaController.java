package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.inventory.model.Categoria;
import com.frescuratotal.erp.inventory.model.CategoriaDAO;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.inventory.model.UnidadMedida;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public List<Categoria> listarCategorias(){
        try {
            return categoriaDAO.listarTodos();
        }catch (SQLException e){
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }


}
