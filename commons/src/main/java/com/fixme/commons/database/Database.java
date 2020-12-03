package com.fixme.commons.database;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
  static {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [\u001b[36;1mDATABASE\u001b[0m] [%4$-7s] %5$s %n");
  }

  private static final Connection connection = null;
  private static final Logger log = Logger.getLogger( "Database" );

  private static Connection createConnection() throws ClassNotFoundException {
    Connection connection = null;
    String uri = "jdbc:sqlite:fixme.db";
    Class.forName("org.sqlite.JDBC");
    try {
        connection = DriverManager.getConnection(uri);
    } catch (SQLException e) {
        log.severe(String.format("Connection to database cannot be established: [%s]", e.getMessage()));
        System.exit(1);
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
        log.severe(String.format("Connection to database cannot be closed: [%s]", e.getMessage()));
        System.exit(1);
      }
    }
  }

  public static void checkTransactionsSchema() throws SQLException, ClassNotFoundException {
    String sql = "CREATE TABLE IF NOT EXISTS transactions (\n"
    + "	broker_id varchar NOT NULL,\n"
    + "	market_name varchar NOT NULL,\n"
    + "	instrument varchar NOT NULL,\n"
    + "	quantity integer NOT NULL,\n"
    + " quoted_price double NOT NULL,\n"
    + "	transaction_type varchar CHECK(transaction_type IN ('BUY','SELL')) NOT NULL,\n"
    + " transaction_status varchar CHECK(transaction_status IN ('EXECUTED','REJECTED')) NOT NULL"
    + ");";
    Connection conn = getInstance();
    
    try {
      Statement statement = conn.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      log.severe(String.format("Exception when checking Transaction Table Schema: [%s]", e.getMessage()));
      System.exit(1);
    } finally {
      closeConnection(conn);
    }
  }

  public static void checkInstrumentsSchema() throws SQLException, ClassNotFoundException{
    String sql = "CREATE TABLE IF NOT EXISTS instruments (\n"
    + "	market_name varchar NOT NULL,\n"
    + "	instrument_code varchar NOT NULL,\n"
    + "	instrument_name varchar NOT NULL,\n"
    + " PRIMARY KEY (market_name,instrument_code)"
    + ");";
    Connection conn = getInstance();
    
    try {
      Statement statement = conn.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      log.severe(String.format("Exception when checking Instruments Table Schema: [%s]", e.getMessage()));
      System.exit(1);
    } finally {
      closeConnection(conn);
    }
  }

  public static void registerInstrument(String marketName, String instrumentCode, String instrumentName, Integer quantity, Double minBuyPrice, Double maxSellPrice) throws SQLException, ClassNotFoundException {    
    if (!(instrumentExists(marketName, instrumentCode))) {
      String sql = "INSERT INTO instruments(market_name,instrument_code,instrument_name) VALUES (?,?,?)";
      Connection conn = getInstance();

      try {
         PreparedStatement pStatement = conn.prepareStatement(sql);
  
         pStatement.setString(1, marketName);
         pStatement.setString(2, instrumentCode);
         pStatement.setString(3, instrumentName);
  
         pStatement.executeUpdate();
       } catch (Exception e) {
        log.severe(String.format("Exception while adding Instrument: [%s]", e.getMessage()));
        System.exit(1);
       } finally {
         closeConnection(conn);
       }
    }
  }

  public static boolean instrumentExists(String marketName, String instrumentCode) throws SQLException, ClassNotFoundException {
    String sql = "SELECT (count(*) > 0) as found FROM instruments WHERE market_name LIKE ? AND instrument_code LIKE ?";
    Connection conn = getInstance();

    try {
      PreparedStatement pStatement = conn.prepareStatement(sql);

      pStatement.setString(1, marketName);
      pStatement.setString(2, instrumentCode);

      ResultSet rs = pStatement.executeQuery();

      if (rs.next()) {
        return true;
      }
    } catch (Exception e) {
      log.severe(String.format("Exception while checking DB for instrument: [%s]", e.getMessage()));
      System.exit(1);
    } finally {
      closeConnection(conn);
    }
    return false;
  }

  public static ArrayList<String> getHistoricInstruments() throws SQLException, ClassNotFoundException {
    ArrayList<String> instruments = new ArrayList<String>();

    String sql = "SELECT * FROM instruments";
    Connection conn = getInstance();

    try {
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(sql);

      while(rs.next()) {
        instruments.add(String.format("[MARKET]: [%s] - [INSTRUMENT]: [%s] - [CODE]: [%s]", rs.getString("market_name"), rs.getString("instrument_name"), rs.getString("instrument_code")));
      }
    } catch (Exception e) {
      log.severe(String.format("Exception while checking DB for instrument: [%s]", e.getMessage()));
      System.exit(1);
    } finally {
      closeConnection(conn);
    }
    return instruments;
  }

  public static void recordTransaction() throws SQLException {
    //add a buy or sell transaction to the transactions table.
  }

}
