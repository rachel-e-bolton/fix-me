package fix.broker.sockets;

import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketManager {
  private static final Logger LOGGER = Logger.getLogger( "SocketInitiator" );
  public static final ExecutorService executor = Executors.newFixedThreadPool(10000000);
  private Integer port;
  private String ip;
  private Socket clientSocket;

  public SocketManager(String ip, Integer port) {
    this.port = port;
    this.ip = ip;
  }

  public void startConnection() {
    try {
      clientSocket = new Socket(ip, port);
      LOGGER.log(Level.INFO, "Connection to " + clientSocket.toString() + " established.");
    } catch (Exception e) {
      //Add Logging
      e.printStackTrace();
    }
  }

  public void startSenderThread() {
    executor.submit(new SenderThread(clientSocket));
  }

  public void startListenerThread() {
    executor.submit(new ListenerThread(clientSocket));
  }

  public void closeConnection() {
    try {
      LOGGER.log(Level.INFO, "Closing Connection to " + clientSocket.toString() + " .");
      clientSocket.close();
    } catch (Exception e) {
      //Add Logging
      e.printStackTrace();
    }
  }
}
