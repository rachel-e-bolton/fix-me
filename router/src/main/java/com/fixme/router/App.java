package com.fixme.router;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import fix.router.routing.Table;
import fix.router.sockets.*;

public class App {
    private static final Logger LOGGER = Logger.getLogger( "Router" );
    public static final Table routingTable = new Table();
    public static final ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main( String[] args ) {

        LOGGER.info("Router Application Start");

        executor.submit(new BrokerSocketServer(5000));
        executor.submit(new MarketSocketServer(5001));

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.exit(1);
        }
    }
}
