package org.devpulse.foodflow.repository;

import org.devpulse.foodflow.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private Connection connection;

    public ProductRepository(Connection conecction) {
        this.connection = conecction;
    }

    public void saveCustomer(Product product) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(Long id) {
        String sql = "SELECT * FROM products WHERE id= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Product> gatAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public void updateProduct(Product product){
        String sql = "UPDATE products SET name= ?, price= ? WHERE id= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            if (product.getName() != null){
                statement.setString(1, product.getName());
            } else {
                statement.setNull(1, Types.VARCHAR);
            }

            if (product.getPrice() != null){
                statement.setDouble(2, product.getPrice());
            } else {
                statement.setNull(2, Types.REAL);
            }
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProduct(Long id){
        String sql = "DELETE FROM products WHERE id= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

