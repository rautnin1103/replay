package com.abc.replay.dao;

import com.abc.replay.util.QueryUtil;
import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReplayDao {

    private static final Logger logger = LoggerFactory.getLogger(ReplayDao.class);

    private String dbUrl;
    private String tableName;
    private boolean printQuery;

    private static final String DB_CONNECTION_URL = "jdbc.connection.url";
    private static final String REPLAY_TABLE_NAME = "hbase.replay.table";

    private static final int KAFKA_KEY_COLUMN_INDEX = 1;
    private static final int KAFKA_MESSAGE_COLUMN_INDEX = 2;
    private Properties config;

    public ReplayDao(Properties config) {
        this.config = config;
        setDBConnectionUrl();
        setTableName();
        setPrintQueryOption();
    }

    private void setPrintQueryOption() {
        if(System.getenv("printQuery")!=null && System.getenv("printQuery").equals("true")) {
            printQuery = true;
        }
    }

    private void  setDBConnectionUrl(){
        if(config.getProperty(DB_CONNECTION_URL)!=null) {
            this.dbUrl = config.getProperty(DB_CONNECTION_URL);
        }
        else if(System.getenv(DB_CONNECTION_URL)!=null) {
            this.dbUrl  = System.getenv(DB_CONNECTION_URL);//"jdbc:phoenix:localhost:2183"
        } else {
            throw new IllegalStateException("DB Connection URL not found");
        }
    }

    private void setTableName(){
        if(config.getProperty(REPLAY_TABLE_NAME)!=null) {
            this.tableName = config.getProperty(REPLAY_TABLE_NAME);
        } else if(System.getenv(REPLAY_TABLE_NAME)!=null) {
            this.tableName = System.getenv(REPLAY_TABLE_NAME);//"SYSTEM.eventtracker"
        } else {
            throw new IllegalStateException("Replay Table not found");
        }
    }

    public Pair<String,String> getKafkaMessageToReplay(String fileName) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String resultKey = null;
            String resultMessage=null;
            connection = DriverManager.getConnection(dbUrl);
            String query = QueryUtil.creatSingleFileSearchQuery(fileName, tableName);
            if(printQuery) {
                logger.info("Query::"+query);
            }
            ps  = connection.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            resultMessage  = rs.getString(KAFKA_MESSAGE_COLUMN_INDEX);
            resultKey = rs.getString(KAFKA_KEY_COLUMN_INDEX);
            return new Pair(resultKey,resultMessage);

        } finally {
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
            if(connection != null) {
                try {
                    connection.close();
                }
                catch(Exception e) {}
            }
        }

    }

    public List<Pair<String,String>> getKafkaMessagesToReplay(String fromDate, String toDate) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            List<Pair<String,String>> result = new ArrayList<>();
            connection = DriverManager.getConnection(dbUrl);
            String query = QueryUtil.createDateRangeSearchQuery(fromDate, toDate, tableName);
            if(printQuery) {
                logger.info("Query::"+query);
            }
            ps  = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                String resultKey = rs.getString(KAFKA_KEY_COLUMN_INDEX);
                String kafkaMessage = rs.getString(KAFKA_MESSAGE_COLUMN_INDEX);

                result.add(new Pair(resultKey,kafkaMessage));
            }
            return result;

        } finally {
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
            if(connection != null) {
                try {
                    connection.close();
                }
                catch(Exception e) {}
            }
        }
    }
}
