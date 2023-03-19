package com.robertob.p1ipc2.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.robertob.p1ipc2.model.Store;
import com.robertob.p1ipc2.model.StoreUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbStoreUser {
    private Connection connection;

    public DbStoreUser(Connection connection) {
        this.connection = connection;
    }

    public void insert(StoreUser storeUser){
        String query = "INSERT INTO store_users (code, name, username, password, email, store_code) VALUES (?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, storeUser.getCode());
            preparedStatement.setString(2, storeUser.getName());
            preparedStatement.setString(3, storeUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10, storeUser.getPassword().toCharArray());
            preparedStatement.setString(4, encryptedPassword);
            preparedStatement.setString(5, storeUser.getEmail());
            preparedStatement.setInt(6, storeUser.getStore_code());
            preparedStatement.executeUpdate();
        }
        catch (SQLException se){
            System.out.println(se);
        }
    }

    public void update(StoreUser storeUser){
        String query = "UPDATE store_users SET name = ?, username = ?, password = ?, email = ? WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, storeUser.getName());
            preparedStatement.setString(2, storeUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10, storeUser.getPassword().toCharArray());
            preparedStatement.setString(3, encryptedPassword);
            preparedStatement.setString(4, storeUser.getEmail());
            preparedStatement.setInt(5, storeUser.getStore_code());
            preparedStatement.setInt(6, storeUser.getCode());
            preparedStatement.executeUpdate();
        }
        catch (SQLException se){
            System.out.println(se);
        }
    }

    public void setInactive(int code){
        String query = "UPDATE store_users SET active = false WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'store_users' update inactive: "+se);
        }
    }

    public List<StoreUser> list(){
        String query = "SELECT * FROM store_users";
        List<StoreUser> storeUsers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var storeUserCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var username = resultSet.getString("email");
                    var email = resultSet.getString("email");
                    var active = resultSet.getBoolean("active");
                    var storeCode = resultSet.getInt("store_code");

                    var storeUser = new StoreUser(storeUserCode,name,username,email,active,storeCode);
                    storeUsers.add(storeUser);
                }
            }
        }
        catch (SQLException se){
            System.out.println(se);
        }
        return storeUsers;
    }
}
