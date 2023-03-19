package com.robertob.p1ipc2.database;

import com.robertob.p1ipc2.model.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbStore {
    private Connection connection;

    public DbStore(Connection connection) {
        this.connection = connection;
    }

    public void insert(Store store){
        String query = "INSERT INTO stores (code, name, address, supervised, warehouse_user_code) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, store.getCode());
            preparedStatement.setString(2,store.getName());
            preparedStatement.setString(3, store.getAddress());
            preparedStatement.setBoolean(4,store.isSupervised());
            preparedStatement.setInt(5,store.getWarehouseUserCode());
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public void update(Store store){
        String query = "UPDATE stores SET name = ?, address = ?, supervised = ? WHERE code = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, store.getName());
            preparedStatement.setString(2,store.getAddress());
            preparedStatement.setBoolean(3, store.isSupervised());
            preparedStatement.setInt(4,store.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public void delete (int code){
        String query = "DELETE FROM stores WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'stores' delete: "+se);
        }
    }

    public Optional<Store> findByCode(int code){
        String query = "SELECT * FROM stores WHERE code = ?";
        Store store = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,code);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var storeCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var address = resultSet.getString("address");
                    var supervised = resultSet.getBoolean("supervised");
                    var warehouseUserCode = resultSet.getInt("warehouse_user_code");

                    store = new Store(storeCode,name,address,supervised,warehouseUserCode);
                }
            }
        } catch (SQLException se) {
            System.out.println(""+se);
        }
        return Optional.ofNullable(store);
    }

    public List<Store> list(){
        String query = "SELECT * FROM stores";
        List<Store> stores = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var storeCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var address = resultSet.getString("address");
                    var supervised = resultSet.getBoolean("supervised");
                    var warehouseUserCode = resultSet.getInt("warehouse_user_code");

                    var store = new Store(storeCode,name,address,supervised,warehouseUserCode);
                    stores.add(store);
                }
            }
        } catch (SQLException se) {
            System.out.println(""+se);
        }
        return stores;
    }
}
