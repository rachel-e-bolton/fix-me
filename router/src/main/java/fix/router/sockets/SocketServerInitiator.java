package fix.router.sockets;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import fix.router.Router;
import fix.router.table.RouteEntry;
import fix.router.table.RoutingTable;

public class SocketServerInitiator implements Runnable {
    private static final Logger LOGGER = Logger.getLogger( "SocketServerInitiator" );
    private Integer port;

    public SocketServerInitiator(Integer port) {
        this.port = port;
    }

    public void run() {
        LOGGER.log(Level.INFO, "Starting SocketServerInitiator thread");
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(this.port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                LOGGER.log(Level.INFO, "Connection " + clientSocket.toString());
                Router.routingTable.addEntry(new RouteEntry(clientSocket, clientSocket.toString(), "unauthenticated"));
                Router.executor.submit(new SocketMaintainer(clientSocket));
            }

            // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
