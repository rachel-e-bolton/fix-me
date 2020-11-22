package fix.broker.sockets;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketController {
  private static final Logger LOGGER = Logger.getLogger( "SocketInitiator" );
  public static final ExecutorService executor = Executors.newFixedThreadPool(10000000);
  private Integer port;
  private String ip;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public SocketController  (String ip, Integer port) {
    this.port = port;
    this.ip = ip;
  }

  public void startConnection() {
    try {
      clientSocket = new Socket(ip, port);
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      LOGGER.log(Level.INFO, "Connection to " + clientSocket.toString() + " established.");
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
  }

  public String sendMessage(String msg) {
    if (clientSocket != null) {
      try {
        out.println(msg);
        out.flush();
        String resp = in.readLine();
        return resp;
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
      return "No response message received.";
    }
    return "Socket connection has not been established.";
  }

  public void closeConnection() {
    try {
      LOGGER.log(Level.INFO, "Closing Connection to " + clientSocket.toString() + " .");
      clientSocket.close();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
  }
}
