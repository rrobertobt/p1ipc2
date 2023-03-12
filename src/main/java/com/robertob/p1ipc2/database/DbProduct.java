package com.robertob.p1ipc2.database;

import com.robertob.p1ipc2.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbProduct {
    private Connection connection;

    public DbProduct(Connection connection) {
        this.connection = connection;
    }

    public void insert(Product product){
        String query = "INSERT INTO products (code, name, cost, price, stock) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,product.getCode());
            preparedStatement.setString(2,product.getName());
            preparedStatement.setDouble(3,product.getCost());
            preparedStatement.setDouble(4,product.getPrice());
            preparedStatement.setInt(5,product.getStock());
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            System.out.println("log: error on 'products' insert: "+se);
        }
    }

    public List<Product> list(){
        String query = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var code = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var cost = resultSet.getDouble("cost");
                    var price = resultSet.getDouble("price");
                    var stock = resultSet.getInt("stock");

                    var product = new Product(code,name,cost,price,stock);
                    products.add(product);
                }
            }
        } catch (SQLException se){
            System.out.println("log: error on 'products' listing: "+se);
        }
        return products;
    }

    public Optional<Product> findByCode(int code){
        String query = "SELECT * FROM products WHERE code = ?";
        Product product = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var productCode = resultSet.getInt("code");
                    var name = resultSet.getString("name");
                    var cost = resultSet.getDouble("cost");
                    var price = resultSet.getDouble("price");
                    var stock = resultSet.getInt("stock");

                    product = new Product(productCode,name,cost,price,stock);
                }
            }
        } catch (SQLException se){
            System.out.println("log: error on 'products' find one: "+se);
        }
        return Optional.ofNullable(product);
    }

    public void update(Product product){
        String query = "UPDATE products SET name = ?, cost = ?, price = ?, stock = ? WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,product.getName());
            preparedStatement.setDouble(2, product.getCost());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDouble(4, product.getStock());
            preparedStatement.setInt(5, product.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            System.out.println("log: error on 'products' update: "+se);
        }
    }

    public void delete(int code){
        String query = "DELETE FROM products WHERE code = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,code);
            preparedStatement.executeUpdate();
        }
        catch (SQLException se){
            System.out.println("log: error on 'products' delete: "+se);
        }
    }
}
