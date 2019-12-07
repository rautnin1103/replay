package com.abc.replay;

import com.abc.replay.util.QueryUtil;
import org.apache.commons.math3.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReplayMessageExtractor {

    private static String DB_CONNECTION_URL;
    private static String TABLE_NAME;
    private static int KAFKA_KEY_COLUMN_INDEX = 1;
    private static int KAFKA_MESSAGE_COLUMN_INDEX = 2;

    ReplayMessageExtractor(Properties config) {
        if(config.getProperty("DB_CONNECTION_URL")!=null) {
            DB_CONNECTION_URL = config.getProperty("DB_CONNECTION_URL");
        } else {
            DB_CONNECTION_URL = "jdbc:phoenix:localhost:2183";
        }

        if(config.getProperty("TABLE_NAME")!=null) {
            TABLE_NAME = config.getProperty("TABLE_NAME");
        } else {
            TABLE_NAME = "SAMPLE_TABLE";
        }
    }

    public Pair<String,String> getKafkaMessageToReplay(String fileName) throws SQLException {
        String resultKey = null;
        String resultMessage=null;
        Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
        String query = QueryUtil.creatSingleFileSearchQuery(fileName, TABLE_NAME);
        PreparedStatement ps  = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        resultMessage  = rs.getString(KAFKA_MESSAGE_COLUMN_INDEX);
        resultKey = rs.getString(KAFKA_KEY_COLUMN_INDEX);
        return new Pair(resultKey,resultMessage);
    }

    public List<Pair<String,String>> getKafkaMessagesToReplay(String fromDate, String toDate) throws SQLException {
        List<Pair<String,String>> result = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
        String query = QueryUtil.createDateRangeSearchQuery(fromDate, toDate, TABLE_NAME);
        PreparedStatement ps  = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String resultKey = rs.getString(KAFKA_KEY_COLUMN_INDEX);
            String kafkaMessage = rs.getString(KAFKA_MESSAGE_COLUMN_INDEX);

            result.add(new Pair(resultKey,kafkaMessage));
        }
        return result;
    }
}
