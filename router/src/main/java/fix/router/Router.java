package fix.router;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import fix.router.sockets.SocketServerInitiator;
import fix.router.table.RoutingTable;

// import fix.router.messaging.HeartBeat;
// import fix.router.sockets.SocketServer;

public class Router {
    private static final Logger LOGGER = Logger.getLogger( "Router" );
    public static final RoutingTable routingTable = new RoutingTable();
    public static final ExecutorService executor = Executors.newFixedThreadPool(10000000);

    public static void main( String[] args ) {

        LOGGER.info("Router Application Start");

        // Start socket server thread
        executor.submit(new SocketServerInitiator(5000));
        executor.submit(new SocketServerInitiator(5001));

        // Start the socket maintainer thread


        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.exit(1);
        }
    }
}
