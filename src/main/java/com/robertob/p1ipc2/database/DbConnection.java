package com.robertob.p1ipc2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection connection = null;
    private final String dbUrl = "jdbc:mysql://localhost/warehouse";
    private final String dbUser = "robertob";
    private final String dbPassword = "123";

    public Connection getConnection (){
      try{
          Class.forName("com.mysql.cj.jdbc.Driver");
          connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
          System.out.println("log: database connection successful");
          return connection;
      } catch (SQLException | ClassNotFoundException ex){
          System.out.println("log: error on database connection: "+ex);
      }
      return null;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("log: connection closed");
            } catch (SQLException se) {
                System.out.println("log: error on closing connection: "+se);
                se.printStackTrace();
            }
        }
    }
}
