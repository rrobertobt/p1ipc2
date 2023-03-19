package com.robertob.p1ipc2.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.robertob.p1ipc2.model.AdminUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbAdminUser {
    private Connection connection;

    public DbAdminUser(Connection connection) {
        this.connection = connection;
    }

    public void insert(AdminUser adminUser){
        String query = "INSERT INTO admin_users (code, name, username, password) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, adminUser.getCode());
            preparedStatement.setString(2, adminUser.getName());
            preparedStatement.setString(3, adminUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10, adminUser.getPassword().toCharArray());
            preparedStatement.setString(4, encryptedPassword);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'admin_users' insert: "+se);
        }
    }

    public void update(AdminUser adminUser){
        String query = "UPDATE admin_users SET name = ? , username = ?, password = ? WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, adminUser.getName());
            preparedStatement.setString(2, adminUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10, adminUser.getPassword().toCharArray());
            preparedStatement.setString(3, encryptedPassword);
            preparedStatement.setInt(4, adminUser.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'admin_users' insert: "+se);
        }
    }

    public Optional<AdminUser> findByCode(int code){
        String query = "SELECT * FROM admin_users WHERE code = ?";
        AdminUser adminUser = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var adminUserCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var username = resultSet.getString("username");

                    adminUser = new AdminUser(adminUserCode, name, username);
                }
            }
        } catch (SQLException se){
            System.out.println("log: error on 'admin_users' find one: "+se);
        }
        return Optional.ofNullable(adminUser);
    }

    public void delete(int code){
        String query = "DELETE FROM admin_users WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'admin_users' delete: "+se);
        }
    }

    public List<AdminUser> list (){
        String query = "SELECT * FROM admin_users";
        List<AdminUser> adminUsers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    var code = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var username = resultSet.getString("username");

                    var adminUser = new AdminUser(code, name, username);
                    adminUsers.add(adminUser);
                }
            }
        } catch (SQLException se){
            System.out.println("log: error on 'admin_users' listing: "+se);
        }

        return adminUsers;
    }
}
