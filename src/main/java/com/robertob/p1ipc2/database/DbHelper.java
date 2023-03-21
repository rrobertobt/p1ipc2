package com.robertob.p1ipc2.database;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.Objects;

@Setter @Getter
public class DbHelper {
    private Connection connection;

    public DbHelper(Connection connection) {
        this.connection = connection;
    }
    public DbHelper() {
    }
    public boolean checkForRecords(){
        boolean hasRecords = false;
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, null, new String[] { "TABLE" });

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (Objects.equals(tableName, "sys_config")){continue;}
                System.out.println(tableName);
                Statement statement = connection.createStatement();
                ResultSet countResultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
                countResultSet.next();
                int rowCount = countResultSet.getInt(1);
                if (rowCount > 0) {
                    hasRecords = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasRecords;
    }

}
