package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.common.LogUtils;
import com.frescuratotal.erp.inventory.model.CategoriaDAO;
import com.frescuratotal.erp.inventory.model.Producto;
import com.frescuratotal.erp.inventory.model.ProductoDAO;
import com.frescuratotal.erp.inventory.model.UnidadMedida;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {
    private ProductoDAO productoDAO = new ProductoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public String insertarProducto(String nombre, String descripcion, int idCategoria,
                                   UnidadMedida unidadMedida, long precioBase) {
        if (nombre == null || nombre.isBlank()) {
            return  ("Nombre inválido");
        }
        if (precioBase<0){
            return ("Debe tener un precio mayor o igual a cero. ");
        }

        Producto p = new Producto(nombre,descripcion,idCategoria,unidadMedida,precioBase);

        try {
            if (!categoriaDAO.existeCategoria(idCategoria)){
                return "Categoria no encontrada";
            }
            int id = productoDAO.insertarProducto(p);
            LogUtils.info("Producto registrado: " + nombre + " (id " + id + ")");
            return "Producto registrado con exito con id: " +id;
        }catch (SQLException e){
            LogUtils.error("Error al registrar el producto " + nombre, e);
            return "Error al registrar: " + e.getMessage();
        }
    }

    public List<Producto> listarProductos(){
        try {
            return productoDAO.listarTodos();
        }catch (SQLException e){
            LogUtils.error("Error al listar productos", e);
            return new ArrayList<>();
        }
    }

    public Producto buscarPorId(int id){
        try {
            return productoDAO.buscarPorId(id);
        }catch (SQLException e){
            LogUtils.error("Error al buscar el producto " + id, e);
            return null;
        }
    }

    public String actualizarProducto(Producto p){
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            return  ("Nombre inválido");
        }
        if (p.getPrecioBase()<0){
            return ("Debe tener un precio mayor o igual a cero. ");
        }

        try {
            if (!categoriaDAO.existeCategoria(p.getIdCategoria())){
                return "Categoria no encontrada";
            }
            if(productoDAO.actualizarProducto(p)){
                LogUtils.info("Producto " + p.getIdProducto() + " actualizado");
                return ("Producto Actualizado Correctamente!");
            }else {
                return ("No existe un producto con ese ID.");
            }
        } catch (SQLException e){
            LogUtils.error("Error al actualizar el producto " + p.getIdProducto(), e);
            return "Error al actualizar: " + e.getMessage();
        }
    }
}