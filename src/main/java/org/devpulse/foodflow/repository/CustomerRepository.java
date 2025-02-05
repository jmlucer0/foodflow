package org.devpulse.foodflow.repository;

import org.devpulse.foodflow.config.DatabaseConfig;
import org.devpulse.foodflow.model.Customer;
import org.devpulse.foodflow.model.dtos.CustomerRegisterDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerRepository {

    private Connection connection;

    public CustomerRepository() {
        this.connection = DatabaseConfig.getConnection();
    }

    public CustomerRepository(Connection connection) {
        this.connection = connection;
    }

    public void saveCustomer(CustomerRegisterDto customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (connection == null) {
            throw new IllegalStateException("Database connection is not initialized.");
        }

        String sql = "INSERT INTO  customers (first_name, last_name, address) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName() != null ? customer.getName() : "Desconocido");
            statement.setString(2, customer.getLastname() != null ? customer.getLastname() : "N/A");
            statement.setString(3, customer.getAddress() != null ? customer.getAddress() : "Sin dirección");

            statement.executeUpdate();
            System.out.println("cliente guardado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //ver de hacer un dao para no exponer datos
    public Customer getCustomerById(Long id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET first_name= ?, last_name= ?, address= ? WHERE id= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (customer.getName() != null) {
                statement.setString(1, customer.getName());
            } else {
                statement.setNull(1, Types.VARCHAR);
            }

            if (customer.getLastName() != null) {
                statement.setString(2, customer.getLastName());
            } else {
                statement.setNull(2, Types.VARCHAR);
            }

            if (customer.getAddress() != null) {
                statement.setString(3, customer.getAddress());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }
            statement.setLong(4, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(Long id) {
        String sql = "UPDATE customers SET active= ? WHERE id= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            boolean currentActiveStatus = getCustomerById(id).getActive();
            if (currentActiveStatus) statement.setBoolean(1, false);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
