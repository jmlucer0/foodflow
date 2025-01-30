package org.devpulse.foodflow.utils;

import org.devpulse.foodflow.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initDatabase() throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement()) {

            //tabla customer
            String createCustomerTable =
                    "CREATE TABLE IF NOT EXISTS customers (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "first_name TEXT NOT NULL, " +
                            "last_name TEXT NOT NULL, " +
                            "address TEXT)";
            statement.execute(createCustomerTable);

            //tabla products
            String productsTable =
                    "CREATE TABLE IF NOT EXISTS products (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT NOT NULL, " +
                            "price REAL NOT NULL)";
            statement.execute(productsTable);

            //tabla Order
            String createOrderTable =
                    "CREATE TABLE IF NOT EXISTS orders (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "customer_id INTEGER NOT NULL, " +
                            "total_price REAL NOT NULL, " +
                            "payment_method TEXT NOT NULL, " +
                            "order_date TEXT NOT NULL, " +
                            "order_time TEXT NOT NULL, " +
                            "order_status TEXT NOT NULL, " +
                            "FOREIGN KEY(customer_id) REFERENCES customers(id))";
            statement.execute(createOrderTable);

            // Tabla order-product
            String createOrderProduct =
                    "CREATE TABLE IF NOT EXISTS order_product (" +
                            "order_id INTEGER NOT NULL, " +
                            "product_id INTEGER NOT NULL, " +
                            "quantity INTEGER NOT NULL DEFAULT 1, " +
                            "PRIMARY KEY (order_id, product_id), " +
                            "FOREIGN KEY (order_id) REFERENCES orders(id), " +
                            "FOREIGN KEY (product_id) REFERENCES products(id))";
            statement.execute(createOrderProduct);
        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}
