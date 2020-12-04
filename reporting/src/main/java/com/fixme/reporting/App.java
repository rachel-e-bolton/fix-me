package com.fixme.reporting;

import com.fixme.commons.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Logger;

public class App {

  static {
    System.setProperty("java.util.logging.SimpleFormatter.format",
        "[%1$tF %1$tT] [\u001b[33mREPORTING\u001b[0m] [%4$-7s] %5$s %n");
  }

  private static final Logger log = Logger.getLogger("Reporting");

  public static void printTransactionReport(Scanner in) {
    try {
      ArrayList<String> transactions = Database.getTransactionHistory();

      System.out.println("\nTRANSACTION HISTORY:\n");

      if (transactions.size() > 0) {
        for (String transaction : transactions) {
          System.out.println(transaction);
        }
      } else {
        System.out.println("\nNo transaction history found.\n");
      }
    } catch (Exception e) {
      log.warning(String.format("An error occured while fetching the list of transactions from the database: [%s]",
          e.getMessage()));
    }

    System.out.print("\nReturn to report selection? Y : \n");
    String proceed = in.nextLine();

    while (!(proceed.matches("y|Y")) && !(proceed.matches(""))) {
      System.out.print("\nReturn to report selection? Y : \n");
      proceed = in.nextLine();
    }
  }

  public static void printInstrumentsReport(Scanner in) {
    try {
      ArrayList<String> instruments = Database.getHistoricInstruments();
      Collections.sort(instruments);

      System.out.println("\nThe following instruments have been available for purchase or sale historically:\n");

      if (instruments.size() > 0) {
        for (String instrument : instruments) {
          System.out.println(instrument);
        }
      } else {
        System.out.println("\nNo instruments found.\n");
      }
    } catch (Exception e) {
      log.warning(String.format("An error occured while fetching the list of instruments from the database: [%s]",
          e.getMessage()));
    }

    System.out.print("\nReturn to report selection? Y : \n");
    String proceed = in.nextLine();

    while (!(proceed.matches("y|Y")) && !(proceed.matches(""))) {
      System.out.print("\nReturn to report selection? Y : \n");
      proceed = in.nextLine();
    }
  }

  public static void selectReport(Scanner in, ArrayList<String> messages) {
    clearScreen();
    
    for (String message : messages) {
      log.info(message);
    }
    messages.clear();

    System.out.println("\nSelect a report or enter [Q] to quit reporting:\n");
    System.out.println("[1] - Historic Instruments Report");
    System.out.println("[2] - Transaction History Report\n");

    String selection = in.nextLine();

    if (selection.matches("q|Q")) {
      System.out.println("\nExiting Reporting Module...\n");
      System.exit(0);
    } else if (selection.matches("1")) {
      System.out.println("\nFetching Historic Instrument Report...\n");
      printInstrumentsReport(in);
    } else if (selection.matches("2")) {
      System.out.println("\nFetching Transaction History Report...\n");
      printTransactionReport(in);
    } else {
      messages.add("Please select a valid option...");
      selectReport(in, messages);
    }
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    ArrayList<String> messages = new ArrayList<String>();

    while (true) {
      selectReport(in, messages);
    }
  }
}
