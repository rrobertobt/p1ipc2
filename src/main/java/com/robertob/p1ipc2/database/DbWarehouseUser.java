package com.robertob.p1ipc2.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.robertob.p1ipc2.model.StoreUser;
import com.robertob.p1ipc2.model.SupervisorUser;
import com.robertob.p1ipc2.model.WarehouseUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbWarehouseUser {
    private Connection connection;

    public DbWarehouseUser(Connection connection) {
        this.connection = connection;
    }

    public void insert(WarehouseUser warehouseUser){
        String query = "INSERT INTO warehouse_users (code, name, username, password, email) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, warehouseUser.getCode());
            preparedStatement.setString(2, warehouseUser.getName());
            preparedStatement.setString(3, warehouseUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10,warehouseUser.getPassword().toCharArray());
            preparedStatement.setString(4, encryptedPassword);
            preparedStatement.setString(5, warehouseUser.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println(se);
        }
    }

    public void update(WarehouseUser warehouseUser){
        String query = "UPDATE warehouse_users SET name = ?, username = ?, password = ?, email = ? WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, warehouseUser.getName());
            preparedStatement.setString(2, warehouseUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10,warehouseUser.getPassword().toCharArray());
            preparedStatement.setString(3, encryptedPassword);
            preparedStatement.setString(4, warehouseUser.getEmail());
            preparedStatement.setInt(5, warehouseUser.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println(se);
        }
    }

    public void setInactive(int code){
        String query = "UPDATE warehouse_users SET active = false WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'warehouse_users' update inactive: "+se);
        }
    }

    public void delete(int code){
        String query = "DELETE FROM warehouse_users WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'warehouse_users' delete: "+se);
        }
    }

    public Optional<WarehouseUser> findByCode(int code){
        String query = "SELECT * FROM warehouse_users WHERE code";
        WarehouseUser warehouseUser = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var warehouseUserCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var username = resultSet.getString("username");
                    var email = resultSet.getString("email");
                    var active = resultSet.getBoolean("active");

                    warehouseUser = new WarehouseUser(warehouseUserCode,name,username,email, active);
                }
            }
        } catch (SQLException se){
            System.out.println(""+se);
        }
        return Optional.ofNullable(warehouseUser);
    }

    public List<WarehouseUser> list(){
        String query = "SELECT * FROM warehouse_users";
        List<WarehouseUser> warehouseUsers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    var warehouseUserCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var username = resultSet.getString("username");
                    var email = resultSet.getString("email");
                    var active = resultSet.getBoolean("active");

                    var warehouseUser = new WarehouseUser(warehouseUserCode,name,username,email,active);
                    warehouseUsers.add(warehouseUser);
                }
            }
        }
        catch (SQLException se){
            System.out.println(""+se);
        }

        return warehouseUsers;
    }

    public Optional<WarehouseUser> findByUserPassword (String username, String password){
        String query = "SELECT * FROM warehouse_users WHERE username = ? AND password = ?";
        WarehouseUser warehouseUser = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var code = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var warehouseUsername = resultSet.getString("username");
                    var email = resultSet.getString("email");
                    var active = resultSet.getBoolean("active");

                    if (active){
                        warehouseUser = new WarehouseUser(code, name, warehouseUsername, email, active);
                    }
                }
            }
        } catch (SQLException se){
            System.out.println(se);
        }
        return Optional.ofNullable(warehouseUser);
    }
}
