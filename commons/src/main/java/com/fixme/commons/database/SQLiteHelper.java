package com.fixme.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteHelper {

    private static Connection conn = null;

    private static Connection CreateConnection() throws ClassNotFoundException {
        Connection conn = null;
        String uri = "jdbc:sqlite:fix-me.db";
        Class.forName("org.sqlite.JDBC");
        try {
            conn = DriverManager.getConnection(uri);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static Connection GetInstance() throws ClassNotFoundException {
        if (conn == null) {
            return CreateConnection();
        }

        return conn;
    }

    public static void CloseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Could not close database connection.");
            }

        }
    }
}