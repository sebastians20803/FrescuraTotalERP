package com.frescuratotal.erp;

import com.frescuratotal.erp.suppliers.model.Proveedor;
import com.frescuratotal.erp.suppliers.model.ProveedorDAO;
import com.frescuratotal.erp.suppliers.view.ProveedorVista;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        ProveedorVista proveedorV =  new ProveedorVista();

        proveedorV.mostrarMenu();


    }

}