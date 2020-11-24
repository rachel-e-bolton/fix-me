package com.fixme.router.sockets;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import fix.router.Router;
import fix.router.routing.Entry;

public class BrokerSocketServer implements Runnable {
    private static final Logger LOGGER = Logger.getLogger( "BrokerSocketServer" );
    private Integer port;

    public BrokerSocketServer(Integer port) {
        this.port = port;
    }

    public void run() {
        LOGGER.log(Level.INFO, "Starting BrokerSocketServer thread");
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(this.port, 5000, Inet4Address.getByName("0.0.0.0"));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                LOGGER.log(Level.INFO, "Connection " + clientSocket.toString());

                Entry routeEntry = new Entry(clientSocket, "broker");
                Router.routingTable.addEntry(routeEntry);
                Router.executor.submit(new ClientSocketMaintainer(clientSocket, routeEntry));
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
