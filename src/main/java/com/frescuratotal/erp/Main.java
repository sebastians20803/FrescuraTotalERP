package com.frescuratotal.erp;

import com.frescuratotal.erp.suppliers.model.Proveedor;
import com.frescuratotal.erp.suppliers.model.ProveedorDAO;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {

        Proveedor proveedor = new Proveedor("Sebastian", "100533331755",
                "Steven","sebas@gmail.com","3131313131","calle45");


        ProveedorDAO dao = new ProveedorDAO();
        int idNuevo = dao.insertarProveedor(proveedor);

        System.out.println("Proveedor creado con id: " + idNuevo);

        for (Proveedor p : dao.listarTodos()) {
            System.out.println(p);
        }

    }
}