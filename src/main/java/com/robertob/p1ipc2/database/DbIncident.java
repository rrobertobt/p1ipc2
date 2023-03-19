package com.robertob.p1ipc2.database;

import com.robertob.p1ipc2.model.Incident;
import com.robertob.p1ipc2.model.IncidentDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbIncident {
    private Connection connection;

    public DbIncident(Connection connection) {
        this.connection = connection;
    }

    public void insert(Incident incident, ArrayList<IncidentDetail> incidentDetails) throws SQLException {
        String query = "INSERT INTO incidents (status, store_code, user_code) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, incident.getStatus());
            preparedStatement.setInt(2, incident.getStore_code());
            preparedStatement.setInt(3, incident.getUser_code());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int incidentId = resultSet.getInt("id");

            String detailsQuery = "INSERT INTO incident_details (incident_id, product_code, quantity, description) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(detailsQuery);
            for (IncidentDetail detail: incidentDetails) {
                preparedStatement2.setInt(1, incidentId);
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

    public List<Incident> list(){
        String query = "SELECT * FROM incidents";
        List<Incident> incidents = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    var id = resultSet.getInt("id");
                    var created_date = LocalDateTime.parse(resultSet.getString("created_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    var status = resultSet.getString("status");
                    var storeCode = resultSet.getInt("store_code");
                    var userCode = resultSet.getInt("user_code");

                    var incident = new Incident(id, created_date, status, storeCode, userCode);
                    incidents.add(incident);
                }
            }
        } catch (SQLException se){
            System.out.println(se);
        }
        return incidents;
    }

//    public Optional<Incident> findById (int id){
//        String query = "SELECT * FROM incidents INNER JOIN incident_details ON incident_id = id WHERE id = ?";
//        Incident incident = null;
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
//            preparedStatement.setInt(1, id);
//            try (ResultSet resultSet = preparedStatement.executeQuery()){
//                while (resultSet.next()){
//                    var
//                }
//            }
//        }
//    }
}
