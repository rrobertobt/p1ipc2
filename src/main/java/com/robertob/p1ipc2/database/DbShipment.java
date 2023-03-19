package com.robertob.p1ipc2.database;

import com.robertob.p1ipc2.model.Shipment;
import com.robertob.p1ipc2.model.ShipmentDetail;
import jdk.jshell.spi.ExecutionControl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbShipment {
    private Connection connection;

    public DbShipment(Connection connection) {
        this.connection = connection;
    }

    public void insert(int storeCode, int warehouseUserCode, int order_id, ArrayList<ShipmentDetail> shipmentDetails) throws SQLException {
        String query = "INSERT INTO shipments (store_code, warehouse_user_code, order_id) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, storeCode);
            preparedStatement.setInt(2, warehouseUserCode);
            preparedStatement.setInt(3, order_id);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int shipmentId = resultSet.getInt("id");

            String detailsQuery = "INSERT INTO shipment_details (shipment_id, product_code, quantity, cost) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(detailsQuery);
            for (ShipmentDetail detail : shipmentDetails){
                preparedStatement2.setInt(1, shipmentId);
                preparedStatement2.setInt(2, detail.getProduct_code());
                preparedStatement2.setInt(3, detail.getQuantity());
                preparedStatement2.setDouble(4, detail.getCost());
                preparedStatement2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException se) {
            connection.rollback();
            System.out.println(se);
        }
    }

    public void update (int shipmentId, ArrayList<ShipmentDetail> shipmentDetails) throws SQLException {
        // 1. Retrieve the list of existing shipment_details records for the given shipment_id
        List<ShipmentDetail> existingDetails = new ArrayList<>();
        try (PreparedStatement existingDetailsQuery = connection.prepareStatement("SELECT * FROM shipment_details WHERE shipment_id = ?");) {
            connection.setAutoCommit(false);
            existingDetailsQuery.setInt(1, shipmentId);
            ResultSet resultSet = existingDetailsQuery.executeQuery();
            while (resultSet.next()){
                ShipmentDetail shipmentDetail = new ShipmentDetail(
                        resultSet.getInt("id"),
                        resultSet.getInt("shipment_id"),
                        resultSet.getInt("product_code"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("cost")
                );
                existingDetails.add(shipmentDetail);
            }
            // 2. Iterate over the list of shipment_details and check if each record needs to be updated, created, or deleted
            for (ShipmentDetail detail : shipmentDetails){
                if (existingDetails.contains(detail)){
                    String updateQuery = "UPDATE shipment_details SET product_code = ?, quantity = ?, cost = ? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setInt(1, detail.getProduct_code());
                    preparedStatement.setInt(2, detail.getQuantity());
                    preparedStatement.setDouble(3, detail.getCost());
                    preparedStatement.setInt(4, detail.getId());
                    preparedStatement.executeUpdate();
                } else {
                    String createQuery = "INSERT INTO shipment_details (shipment_id, product_code, quantity, cost) VALUES (?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(createQuery);
                    preparedStatement.setInt(1, shipmentId);
                    preparedStatement.setInt(2, detail.getProduct_code());
                    preparedStatement.setInt(3, detail.getQuantity());
                    preparedStatement.setDouble(4, detail.getCost());
                    preparedStatement.executeUpdate();
                }
            }

            // 3. Delete any records that no longer exist in the updated list
            for (ShipmentDetail detail : existingDetails) {
                if (!shipmentDetails.contains(detail)) {
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shipment_details WHERE id = ?");
                    preparedStatement.setInt(1, detail.getId());
                    preparedStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException se){
            connection.rollback();
            System.out.println(se);
        }
    }

    public void insertWithIdAndDepartureDate(Shipment shipment) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not available");
    }
}
