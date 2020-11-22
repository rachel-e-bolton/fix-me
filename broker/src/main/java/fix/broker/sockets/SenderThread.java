package fix.broker.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class SenderThread implements Runnable {
  Socket socket;
  public static final Logger LOGGER = Logger.getLogger( "Broker SenderThread" );

  public SenderThread(Socket s) {
    this.socket = s;
  }

  public void run() {
    while (true) {
      try {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String userInput = in.readLine();
        System.out.println(userInput);
      } catch (Exception e) {
        //Add Logging
        e.printStackTrace();
        break;
      }

    }
  }
}
