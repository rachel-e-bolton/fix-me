package com.fixme.router.sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import com.fixme.router.App;
import com.fixme.commons.messaging.*;
import com.fixme.router.routing.BrokerRouteEntry;
import com.fixme.router.routing.MarketRouteEntry;
import com.fixme.router.routing.RouteEntry;

public class FixmeSocketServer implements Runnable {
    private static final Logger log = Logger.getLogger( "BrokerSocketServer" );
    private Integer port;
    private Integer backlog;
    private String type;

    public FixmeSocketServer(Integer port, String type) {
        // TCP port to listen on
        this.port = port;

        // Either broker or market
        this.type = type;

        // How many requests to queue before dropping
        this.backlog = 1000;
    }

    public void run() {
        ServerSocket serverSocket = null;
        
        log.info(String.format("Starting %sSocketServer thread on port %d", this.type, this.port));
        try {
            // Instantiate a socket server on 0.0.0.0:500X for new broker/market socket connections
            serverSocket = new ServerSocket(this.port, this.backlog, Inet4Address.getByName("0.0.0.0"));

            while (true) {
                // Wait for a connection
                Socket clientSocket = serverSocket.accept();
                
                // Log that a new connection has been received
                String ip = clientSocket.getInetAddress().toString();
                log.info(String.format("Connection from %s:%d", ip.substring(1, ip.length()), clientSocket.getPort()));

                // Create a new RouteEntry instance
                RouteEntry routeEntry;
                if (this.type.equalsIgnoreCase("broker")) {
                    routeEntry = new BrokerRouteEntry(clientSocket);
                } else {
                    routeEntry = new MarketRouteEntry(clientSocket);
                }
                
                // Add routing entry to Application's routing table, returns ID
                App.routingTable.addEntry(routeEntry);

                // Send a logon message to client using PrintWriter
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(MessageStaticFactory.socketLogonMessage(routeEntry.id));
                
                // Spawn a thread to listen for incoming messages from client, handle replies and routing as well
                App.executor.submit(new ClientSocketMaintainer(clientSocket));
            }
        } catch (IOException ex) {
            log.severe("Could not start Broker Server: " + ex.getMessage());
        }
    }
}
