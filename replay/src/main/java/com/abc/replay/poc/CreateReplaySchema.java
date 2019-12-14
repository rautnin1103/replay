package com.abc.replay.poc;

import java.sql.*;

public class CreateReplaySchema {
    public static void main(String[] args) {
        // Create variables
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:phoenix:localhost:2183");

            // Create a JDBC statement
            statement = connection.createStatement();

            //statement.executeUpdate("delete from SYSTEM.CATALOG where TABLE_NAME='SYSTEM.eventtracker'");

            //statement.executeUpdate("DROP TABLE 'SYSTEM.eventtracker'");

            //connection.commit();

            // Execute our statements
            statement.executeUpdate("CREATE TABLE SYSTEM.eventtracker_1  (\n" +
                    "       ID VARCHAR,\n" +
                    "       Created_DATE DATE NOT NULL,\n" +
                    "       preprocess VARCHAR ,\n" +
                    "       preprocess_DATE DATE ,\n" +
                    "       iReach VARCHAR ,\n" +
                    "       iReach_DATE DATE ,\n" +
                    "       indexing VARCHAR,\n" +
                    "       indexing_DATE DATE ,\n" +
                    "       persistence VARCHAR,\n" +
                    "       persistence_DATE DATE ,\n" +
                    "       replay VARCHAR ,\n" +
                    "       CONSTRAINT pk PRIMARY KEY (ID , Created_DATE ROW_TIMESTAMP)\n" +
                    ")\n");
            connection.commit();


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
