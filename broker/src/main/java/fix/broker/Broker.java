package fix.broker;

import fix.broker.sockets.SocketController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Broker 
{
  private static final Integer port = 5000;
  private static final Logger LOGGER = Logger.getLogger( "Broker" );  

  public static void main( String[] args )
    {
      LOGGER.info("Broker Application Start");

      SocketController socketController = new SocketController("127.0.0.1", port);

      try {
        socketController.startConnection();
        System.out.println(socketController.sendMessage("Login"));
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }
}
