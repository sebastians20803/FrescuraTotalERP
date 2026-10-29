package com.frescuratotal.erp.common;

import java.util.Scanner;

public class ScannerUtils {

    private static Scanner SCANNER = new Scanner(System.in);

    public static String capturarTexto(String mensaje){
        System.out.println(mensaje + ": ");
        return SCANNER.nextLine();


    }

    public static int capturarNumero(String mensaje){
        System.out.println(mensaje + ": ");

        while(!SCANNER.hasNextInt()){
            System.out.println("Dato no aceptado. " + mensaje + ": ");
            SCANNER.next();
        }

        int dato = SCANNER.nextInt();
        SCANNER.nextLine();
        return dato;

    }

    public static double capturarDecimal(String mensaje){
        System.out.println(mensaje + ": ");

        while(!SCANNER.hasNextDouble()){
            System.out.println("Dato no aceptado" + mensaje + ": ");
            SCANNER.next();
        }

        double dato = SCANNER.nextDouble();
        SCANNER.nextLine();
        return dato;

    }


}

