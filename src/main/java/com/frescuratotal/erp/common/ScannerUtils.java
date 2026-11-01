package com.frescuratotal.erp.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public static long capturarLong(String mensaje){
        System.out.println(mensaje + ": ");

        while(!SCANNER.hasNextLong()){
            System.out.println("Dato no aceptado" + mensaje + ": ");
            SCANNER.next();
        }

        long dato = SCANNER.nextLong();
        SCANNER.nextLine();
        return dato;

    }

    public static String capturarTextoOpcional(String mensaje,String valorActual){
        System.out.println(mensaje + "["+valorActual+"]:");
        String entrada = SCANNER.nextLine();

        if (entrada.isBlank()){
            return valorActual;
        }else {
            return entrada;
        }

    }
    public static LocalDate  capturarFecha (String mensaje){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print(mensaje + " (dd/MM/yyyy): ");
            String entrada = SCANNER.nextLine();

            try {
                return LocalDate.parse(entrada.trim(), formato);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no válida. Ejemplo: 20/11/2026");
            }
        }

    }

    public static int capturarIntOpcional(String mensaje, int valorActual) {
        while (true) {
            System.out.print(mensaje + " [" + valorActual + "]: ");
            String entrada = SCANNER.nextLine();

            if (entrada.isBlank()) {
                return valorActual;
            }
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número.");
            }
        }
    }

    public static long capturarLongOpcional(String mensaje, long valorActual) {
        while (true) {
            System.out.print(mensaje + " [" + valorActual + "]: ");
            String entrada = SCANNER.nextLine();

            if (entrada.isBlank()) {
                return valorActual;
            }
            try {
                return Long.parseLong(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número.");
            }
        }
    }


}

