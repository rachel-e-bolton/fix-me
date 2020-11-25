package com.fixme.router;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.fixme.router.routing.RoutingTable;
import com.fixme.router.sockets.*;

public class App {
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format","[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }
    public static final RoutingTable routingTable = new RoutingTable();
    public static final ExecutorService executor = Executors.newFixedThreadPool(100);
    private static final Logger log = Logger.getLogger("Router");

    public static void main( String[] args ) {

        log.info("Router Application Start");

        executor.submit(new FixmeSocketServer(5000, "broker"));
        executor.submit(new FixmeSocketServer(5001, "market"));

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            log.warning("Interupt requested : " + ex.getMessage());
            System.exit(1);
        }
    }
}
