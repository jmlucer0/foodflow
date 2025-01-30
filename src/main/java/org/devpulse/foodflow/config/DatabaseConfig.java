package org.devpulse.foodflow.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_PATH = System.getProperty("user.home") + "/foodflow/foodflow.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    static {
        try {
            File dbFolder = new File(System.getProperty("user.home") + "/foodflow");
            if (!dbFolder.exists()) {
                dbFolder.mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Error creando la carpeta de la base de datos: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
}
