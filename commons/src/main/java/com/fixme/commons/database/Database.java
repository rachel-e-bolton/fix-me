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

  private static final Connection connection = null;
  private static final Logger log = Logger.getLogger("Database");

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
    String sql = "CREATE TABLE IF NOT EXISTS transactions (\n" + "	broker_id varchar NOT NULL,\n"
        + "	market_name varchar NOT NULL,\n"
        + "	instrument_name varchar NOT NULL,\n" + "	quantity integer NOT NULL,\n"
        + " quoted_price double NOT NULL,\n"
        + "	transaction_type varchar CHECK(transaction_type IN ('BUY','SELL')) NOT NULL,\n"
        + " transaction_status varchar CHECK(transaction_status IN ('EXECUTED','REJECTED')) NOT NULL,\n"
        + " created_date datetime NOT NULL" + ");";
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

  public static void checkInstrumentsSchema() throws SQLException, ClassNotFoundException {
    String sql = "CREATE TABLE IF NOT EXISTS instruments (\n" + " market_name varchar NOT NULL,\n"
        + "	instrument_code varchar NOT NULL,\n" + "	instrument_name varchar NOT NULL\n"
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

  public static void registerInstrument(String marketName, String instrumentCode, String instrumentName) throws SQLException, ClassNotFoundException {
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

  public static boolean instrumentExists(String marketName, String instrumentCode)
      throws SQLException, ClassNotFoundException {
    String sql = "SELECT * FROM instruments WHERE market_name = ? AND instrument_code = ?";
    Connection conn = getInstance();

    try {
      PreparedStatement pStatement = conn.prepareStatement(sql);

      pStatement.setString(1, marketName);
      pStatement.setString(2, instrumentCode);

      ResultSet rs = pStatement.executeQuery();

      while (rs.next()) {
        if (rs.getString("market_name") == marketName && rs.getString("instrument_code") == instrumentCode) {
          return true;
        }
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

      while (rs.next()) {
        instruments.add(String.format("[MARKET]: %s - [INSTRUMENT]: %s - [CODE]: %s", rs.getString("market_name"),
            rs.getString("instrument_name"), rs.getString("instrument_code")));
      }
    } catch (Exception e) {
      log.severe(String.format("Exception while checking DB for instrument: [%s]", e.getMessage()));
      System.exit(1);
    } finally {
      closeConnection(conn);
    }
    return instruments;
  }

  public static void recordTransaction(String brokerId, String marketName, String instrumentName,
      Integer quantity, Double quotedPrice, String transactionType, String transactionStatus)
      throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO transactions(broker_id,market_name,instrument_name,quantity,quoted_price,transaction_type,transaction_status,created_date) VALUES (?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
    Connection conn = getInstance();

    try {
      PreparedStatement pStatement = conn.prepareStatement(sql);

      pStatement.setString(1, brokerId);
      pStatement.setString(2, marketName);
      pStatement.setString(3, instrumentName);
      pStatement.setInt(4, quantity);
      pStatement.setDouble(5, quotedPrice);
      pStatement.setString(6, transactionType);
      pStatement.setString(7, transactionStatus);

      pStatement.executeUpdate();
    } catch (Exception e) {
      log.severe(String.format("Exception while adding Transaction: [%s]", e.getMessage()));
      System.exit(1);
    } finally {
      closeConnection(conn);
    }
  }

  public static ArrayList<String> getTransactionHistory() throws SQLException, ClassNotFoundException {
    ArrayList<String> transactions = new ArrayList<String>();
    String sql = "SELECT * FROM transactions";
    Connection conn = getInstance();

    try {
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        transactions.add(String.format("[%s] - [%s] - [%s] - [%d] - [%s] - [%s] - [%s] - [%s]",
            rs.getString("broker_id"), rs.getString("market_name"),
            rs.getString("instrument_name"), rs.getInt("quantity"), rs.getDouble("quoted_price"),
            rs.getString("transaction_type"), rs.getString("transaction_status"), rs.getString("created_date")));
      }
    } catch (Exception e) {
      log.severe(String.format("Exception while fetching Transaction History: [%s]", e.getMessage()));
      System.exit(1);
    } finally {
      closeConnection(conn);
    }

    return transactions;
  }
}
