package com.abc.replay.util;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class QueryUtil {


    public static String creatSingleFileSearchQuery(String fileName, String tableName){

        String query=null;
        query = "select ID,message from "+ tableName+ " where ID='"+fileName+"'";
        return query;
    }

    public static String createDateRangeSearchQuery(String fromDate, String toDate, String tableName){
        String query=null;

        return query;
    }





}
