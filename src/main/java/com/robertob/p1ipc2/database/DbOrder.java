package com.robertob.p1ipc2.database;

import com.robertob.p1ipc2.model.Order;
import com.robertob.p1ipc2.model.OrderDetail;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class DbOrder {
    private Connection connection;

    public DbOrder(Connection connection) {
        this.connection = connection;
    }

    public void insert(Order order,  ArrayList<OrderDetail> orderDetails) throws SQLException {
        String query = "INSERT INTO orders (status, description, store_code, store_user_code) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, order.getStatus());
            preparedStatement.setString(2, order.getDescription());
            preparedStatement.setInt(3, order.getStore_code());
            preparedStatement.setInt(4, order.getStore_user_code());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int orderId = resultSet.getInt("id");

            String detailsQuery = "INSERT INTO order_details (order_id, product_code, quantity, cost) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(detailsQuery);
            for (OrderDetail detail : orderDetails){
                preparedStatement2.setInt(1, orderId);
                preparedStatement2.setInt(2,detail.getProduct_code());
                preparedStatement2.setInt(3, detail.getQuantity());
                preparedStatement2.setDouble(4, detail.getCost());
                preparedStatement2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException se){
            connection.rollback();
            System.out.println(se);
        }
    }
    public void insertWithIdAndDate(Order order){
        String query = "INSERT INTO orders (id, created_date, status, description, store_code, store_user_code) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setString(2, order.getCreatedDate().toString());
            preparedStatement.setString(3, order.getStatus());
            preparedStatement.setString(4, order.getDescription());
            preparedStatement.setInt(5, order.getStore_code());
            preparedStatement.setInt(6, order.getStore_user_code());
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println(se);
        }
    }

    public void update (Order order){
        String query = "UPDATE orders SET status = ?, description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, order.getStatus());
            preparedStatement.setString(2, order.getDescription());
            preparedStatement.setInt(3, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println(se);
        }

    }

    public void delete (int code){
        String query = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println(se);
        }
    }

    public Optional<Order> findById(int id){
        String query = "SELECT * FROM orders WHERE id = ?";
        Order order = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.getResultSet()){
                while (resultSet.next()){
                    var orderId = resultSet.getInt("id");
                    var createdDate = LocalDateTime.parse(resultSet.getString("created_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    var status = resultSet.getString("status");
                    var description = resultSet.getString("description");
                    var store_code = resultSet.getInt("store_code");
                    var user_code = resultSet.getInt("store_user_code");

                    order = new Order(id, createdDate, status,description,store_code,user_code);
                }
            }
        } catch (SQLException se){
            System.out.println(se);
        }

        return Optional.ofNullable(order);
    }
}
