package com.robertob.p1ipc2.database;

import com.robertob.p1ipc2.model.Returning;
import com.robertob.p1ipc2.model.ReturningDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbReturning {
    private Connection connection;

    public DbReturning(Connection connection) {
        this.connection = connection;
    }

    public void insert (Returning returning, ArrayList<ReturningDetail> returningDetails) throws SQLException {
        String query = "INSERT INTO returnings (status, store_code, user_code) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, returning.getStatus());
            preparedStatement.setInt(2, returning.getStore_code());
            preparedStatement.setInt(3, returning.getUser_code());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int returningId = resultSet.getInt("id");

            String detailsQuery = "INSERT INTO returnings_details (returning_id, product_code, quantity, description) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(detailsQuery);
            for (ReturningDetail detail: returningDetails) {
                preparedStatement2.setInt(1, returningId);
                preparedStatement2.setInt(2, detail.getProductCode());
                preparedStatement2.setInt(3, detail.getQuantity());
                preparedStatement2.setString(4, detail.getDescription());
            }
            connection.commit();
        } catch (SQLException se) {
            connection.rollback();
            System.out.println(se);
        }
    }



}
