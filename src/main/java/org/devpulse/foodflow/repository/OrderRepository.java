package org.devpulse.foodflow.repository;


import org.devpulse.foodflow.model.Order;
import org.devpulse.foodflow.model.Product;

import java.sql.*;
import java.util.Map;

public class OrderRepository {
    private Connection connection;

    public OrderRepository(Connection connection) {
        this.connection = connection;
    }

    public void saveOrder(Order order) throws SQLException {
        //hacer otra query para poder cargar la tabla order_products
        String sqlProducts = "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?)";
        String sqlOrder = "INSERT INTO orders (customer, totalPrice, paymentMethod, orderDate, orderTime, orderStatus VALUES(?, ?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            Long orderId;
            try (PreparedStatement statement = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, order.getCustomer().getId().intValue());
                statement.setDouble(2, order.getTotalPrice());
                statement.setString(3, order.getPaymentMethod().toString());
                statement.setString(4, order.getOrderDate().toString());
                statement.setString(5, order.getOrderTime().toString());
                statement.setString(6, order.getOrderStatus().toString());

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Error: No se pudo insertar la orden.");
                }
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Error: No se obtuvo el ID de la orden.");
                    }
                }
                // Insertar productos en order_products
                try (PreparedStatement productStatement = connection.prepareStatement(sqlProducts)) {
                    for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
                        productStatement.setLong(1, orderId);
                        productStatement.setLong(2, entry.getKey().getId());
                        productStatement.setInt(3, entry.getValue());
                        productStatement.addBatch();
                    }
                    productStatement.executeBatch();
                }

                connection.commit(); // Confirmar transacción
                System.out.println("Orden guardada exitosamente con ID: " + orderId);
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Revertir cambios en caso de error
                System.err.println("Error al guardar la orden: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true); // Restaurar comportamiento normal
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }
}
