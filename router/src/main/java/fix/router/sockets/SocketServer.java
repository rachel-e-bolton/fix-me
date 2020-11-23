package fix.router.sockets;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import fix.router.Router;
import fix.router.routing.Entry;

public class SocketServer implements Runnable {
    private static final Logger LOGGER = Logger.getLogger( "SocketServer" );
    private Integer port;

    public SocketServer(Integer port) {
        this.port = port;
    }

    public void run() {
        LOGGER.log(Level.INFO, "Starting SocketServerInitiator thread");
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(this.port, 500, Inet4Address.getByName("0.0.0.0"));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                LOGGER.log(Level.INFO, "Connection " + clientSocket.toString());
                Router.routingTable.addEntry(new Entry(clientSocket));
                Router.executor.submit(new ClientSocketMaintainer(clientSocket));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
