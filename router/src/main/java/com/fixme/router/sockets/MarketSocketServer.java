package com.fixme.router.sockets;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import com.fixme.router.App;
import com.fixme.router.routing.RouteEntry;

public class MarketSocketServer implements Runnable {
    private static final Logger log = Logger.getLogger( "MarketSocketServer" );
    private Integer port;

    public MarketSocketServer(Integer port) {
        this.port = port;
    }

    public void run() {
        log.info("Starting MarketSocketServer thread");
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(this.port, 5001, Inet4Address.getByName("0.0.0.0"));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                log.info("Connection initiated from " + clientSocket.getLocalAddress().toString());

                RouteEntry routeEntry = new RouteEntry(clientSocket, "market");
                App.routingTable.addEntry(routeEntry);
                App.executor.submit(new ClientSocketMaintainer(clientSocket, routeEntry));
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
