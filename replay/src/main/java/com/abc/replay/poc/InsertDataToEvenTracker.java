package com.abc.replay.poc;

import java.sql.*;

public class InsertDataToEvenTracker {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:phoenix:localhost:2183");

            // Create a JDBC statement
            statement = connection.createStatement();
//
            statement.executeUpdate("upsert into SYSTEM.eventtracker_1 values " +
                    "('/reach/abc/xyz/somefile.zip_3'," +
                    "'2019-12-01 00:00:59'," +
                    "'1'," +
                    "'2019-12-01 00:00:59'," +
                    "'1'," +
                    "'2019-12-01 00:00:59'," +
                    "'0'," +
                    "''," +
                    "''," +
                    "''," +
                    "'{sample json message}')");
                    //statement.executeUpdate("upsert into javatest values (2,'Java Application')");
            connection.commit();

            // Query for table
            ps = connection.prepareStatement("select ID,replay from SYSTEM.eventtracker_1 where ID='/reach/abc/xyz/somefile.zip'");
            rs = ps.executeQuery();
            System.out.println("Table Values");
            while(rs.next()) {
                String myKey = rs.getString(1);
                String myColumn = rs.getString(2);
                System.out.println("\tRow: " + myKey + " = " + myColumn);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(ps != null) {
                try {
                    ps.close();
                }
                catch(Exception e) {}
            }
            if(rs != null) {
                try {
                    rs.close();
                }
                catch(Exception e) {}
            }
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(Exception e) {}
            }
            if(connection != null) {
                try {
                    connection.close();
                }
                catch(Exception e) {}
            }
        }
    }
}
