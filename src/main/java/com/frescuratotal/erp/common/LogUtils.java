package com.frescuratotal.erp.common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogUtils {
    private static final String CARPETA = "logs";
    private static final String ARCHIVO = CARPETA + "/erp.log";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void info(String mensaje) {
        escribir("INFO ", mensaje);
    }

    public static void warn(String mensaje) {
        escribir("WARN ", mensaje);
    }

    public static void error(String mensaje, Exception e) {
        escribir("ERROR", mensaje + " | " + e.getMessage());
    }

    private static void escribir(String nivel, String mensaje) {
        String linea = LocalDateTime.now().format(FORMATO) + " [" + nivel + "] " + mensaje;
        try {
            Files.createDirectories(Paths.get(CARPETA));
            try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
                pw.println(linea);
            }
        } catch (IOException e) {
            System.out.println("No se pudo escribir en el log: " + e.getMessage());
        }
    }

    // Lee las últimas N líneas del log
    public static List<String> ultimasLineas(int cantidad) {
        try {
            Path ruta = Paths.get(ARCHIVO);
            if (!Files.exists(ruta)) {
                return List.of();
            }
            List<String> lineas = Files.readAllLines(ruta);
            return lineas.stream()
                    .skip(Math.max(0, lineas.size() - cantidad))
                    .toList();
        } catch (IOException e) {
            return List.of();
        }
    }
}