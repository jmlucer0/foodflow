package org.devpulse.foodflow.repository;

import org.devpulse.foodflow.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerRepository {

    private Connection connection;

    public CustomerRepository(Connection connection) {
        this.connection = connection;
    }

    public void saveCustomer(Customer customer){
        String sql = "INSERT INTO  customer (name, lastName, address) VALUES (?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getAddress());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
//ver de hacer un dao para no exponer datos
    public Customer getCustomerById(Long id){
        String sql = "SELECT * FROM customer WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Customer(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("last_name"),
                resultSet.getString("address")
                );
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                customers.add(new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address")
                ));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    public void updateCustomer(Customer customer){
        String sql = "UPDATE customers SET name= ?, lastName= ?, address= ? WHERE id= ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            if (customer.getName() != null){
                statement.setString(1,customer.getName());
            }else {
                statement.setNull(1, Types.VARCHAR);
            }

            if (customer.getLastName() != null){
                statement.setString(2, customer.getLastName());
            }else {
                statement.setNull(2, Types.VARCHAR);
            }

            if (customer.getAddress() != null){
                statement.setString(3, customer.getAddress());
            }else {
                statement.setNull(3, Types.VARCHAR);
            }
            statement.setLong(4, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(Long id){
        String sql = "UPDATE customers SET active= ? WHERE id= ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            boolean currentActiveStatus = getCustomerById(id).getActive();
            if (currentActiveStatus)statement.setBoolean(1, false);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
