package com.robertob.p1ipc2.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.robertob.p1ipc2.model.StoreUser;
import com.robertob.p1ipc2.model.SupervisorUser;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbSupervisorUser {
    private Connection connection;

    public DbSupervisorUser(Connection connection) {
        this.connection = connection;
    }

    public void insert(SupervisorUser supervisorUser){
        String query = "INSERT INTO supervisor_users (code, name, username, password, email) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,supervisorUser.getCode());
            preparedStatement.setString(2,supervisorUser.getName());
            preparedStatement.setString(3,supervisorUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10,supervisorUser.getPassword().toCharArray());
            preparedStatement.setString(4, encryptedPassword);
            preparedStatement.setString(5, supervisorUser.getEmail());
            preparedStatement.executeUpdate();
        }
        catch (SQLException se){
            System.out.println("log: error on 'supervisor_users' insert: "+se);
        }
    }

    public void update(SupervisorUser supervisorUser){
        String query = "UPDATE supervisor_users SET name = ?, username = ?, password = ?, email = ? WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, supervisorUser.getName());
            preparedStatement.setString(2, supervisorUser.getUsername());
            String encryptedPassword = BCrypt.withDefaults().hashToString(10,supervisorUser.getPassword().toCharArray());
            preparedStatement.setString(3, encryptedPassword);
            preparedStatement.setString(4, supervisorUser.getEmail());
            preparedStatement.setInt(5, supervisorUser.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'supervisor_users' update: "+se);
        }
    }

    public void setInactive(int code){
        String query = "UPDATE supervisor_users SET active = false WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'supervisor_users' update inactive: "+se);
        }
    }

    public void delete(int code){
        String query = "DELETE FROM supervisor_users WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'supervisor_users' delete: "+se);
        }
    }

    public Optional<SupervisorUser> findByCode(int code){
        String query = "SELECT * FROM supervisor_users WHERE code = ?";
        SupervisorUser supervisorUser = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var supervisorUserCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var username = resultSet.getString("username");
                    var email = resultSet.getString("email");
                    var active = resultSet.getBoolean("active");

                    supervisorUser = new SupervisorUser(supervisorUserCode,name,username,email,active);
                }
            }
        } catch (SQLException se){
            System.out.println(""+se);
        }
        return Optional.ofNullable(supervisorUser);
    }

    public List<SupervisorUser> list(){
        String query = "SELECT * FROM supervisor_users";
        List<SupervisorUser> supervisorUsers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    var supervisorUserCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var username = resultSet.getString("username");
                    var email = resultSet.getString("email");
                    var active = resultSet.getBoolean("active");

                    var supervisorUser = new SupervisorUser(supervisorUserCode,name,username,email,active);
                    supervisorUsers.add(supervisorUser);
                }
            }
        }
        catch (SQLException se){
            System.out.println(""+se);
        }

        return supervisorUsers;
    }

    public Optional<SupervisorUser> findByUserPassword (String username, String password){
        String query = "SELECT * FROM supervisor_users WHERE username = ? AND password = ?";
        SupervisorUser supervisorUser = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var code = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var supUsername = resultSet.getString("username");
                    var email = resultSet.getString("email");
                    var active = resultSet.getBoolean("active");

                    if (active){
                        supervisorUser = new SupervisorUser(code, name, supUsername, email, active);
                    }
                }
            }
        } catch (SQLException se){
            System.out.println(se);
        }
        return Optional.ofNullable(supervisorUser);
    }
}
