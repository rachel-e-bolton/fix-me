package fix.broker;

import fix.broker.sockets.SocketManager;

import java.util.logging.Logger;

public class Broker 
{
  private static final Integer port = 5000;
  private static final Logger LOGGER = Logger.getLogger( "Broker" );  

  public static void main( String[] args )
    {
      LOGGER.info("Broker Application Start");

      SocketManager socketManager = new SocketManager("127.0.0.1", port);

      try {
        socketManager.startConnection();
        socketManager.startSenderThread();
        socketManager.startListenerThread();  
      } catch (Exception e) {
        //Add Logging
        socketManager.closeConnection();
        e.printStackTrace();
      }
    }
}
