package com.fixme.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private static final Connection connection = null;

  private static Connection createConnection() throws ClassNotFoundException {
    Connection connection = null;
    String uri = "jdbc:sqlite:fixme.db";
    Class.forName("org.sqlite.JDBC");
    try {
        connection = DriverManager.getConnection(uri);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return connection;
  }

  public static Connection getInstance() throws ClassNotFoundException {
    if (connection == null) {
        return createConnection();
    }
    return connection;
  }

  private static void closeConnection(Connection conn) {
    if (conn != null) {
      try {
          conn.close();
      } catch (SQLException e) {
          System.out.println("Could not close database connection.");
      }
    }
  }

  public static void checkBrokerSchema() throws SQLException {
    //Create broker table if it doesn't exist.
    //Table Schema:
    //Broker Name, Broker Password
  }

  public static void checkMarketSchema() throws SQLException {
    //Create market table if it doesn't exist.
    //Table Schema:
    //Market name/identifier, Market Instruments, 
  }
  
  public static void registerBroker() throws SQLException {
    //Add Name, Password and the current Router UID for the session
  }

  public static boolean authenticateBroker() throws SQLException {
    //check username and password
    return false;
  }

  public static void registerMarket() throws SQLException {
    //add a new market and instruments
  }

}
