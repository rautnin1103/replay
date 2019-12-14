package com.abc.replay.util;

public class QueryUtil {


    public static String creatSingleFileSearchQuery(String fileName, String tableName){

        String query=null;
        query = "select ID,replay from "+ tableName+ " where ID='"+fileName+"'";
        return query;
    }

    public static String createDateRangeSearchQuery(String fromDate, String toDate, String tableName){
        String query=null;

        query = "SELECT ID, replay \n" +
                "FROM "+tableName+" \n" +
                "WHERE Created_DATE > to_timestamp('"+fromDate+"') \n" +
                "AND Created_DATE < to_timestamp('"+toDate+"') \n" +
                "AND ((indexing='0' OR indexing='')\n" +
                     "OR (persistence='0' OR persistence=''))";
        return query;
    }





}
