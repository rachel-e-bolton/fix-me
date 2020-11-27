package com.fixme.commons.database;

import java.beans.Statement;
import java.sql.*;
import java.io.*;

public class Database {

    public static void InitialiseDB() throws ClassNotFoundException {
        try {
            CreatetransactionsTable();
        } catch (Exception e) {
            System.out.println("InitialiseDB Exception: " + e);
        };
    }

    public static void CreatetransactionsTable() throws ClassNotFoundException, SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS maps (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	fix_message string NOT NULL\n"
                + ");";
        Connection conn = SQLiteHelper.GetInstance();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute(); 
            System.out.println("Transactions table created successfully!");
        } catch (SQLException e) {
            System.out.println("Create Map Table Exception: " + e.getMessage());
        } finally {
            SQLiteHelper.CloseConnection(conn);
        }
    }

    public static void InsertTransactions(String message) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO heros(fix_message) VALUES (?)";
        Connection conn = SQLiteHelper.GetInstance();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, message);
            pstmt.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            SQLiteHelper.CloseConnection(conn);
        }
    }

    public static ResultSet GetAllTransactions() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM transactions";
        Connection conn = SQLiteHelper.GetInstance();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            return (rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            SQLiteHelper.CloseConnection(conn);
        }
        return null;
    }
}