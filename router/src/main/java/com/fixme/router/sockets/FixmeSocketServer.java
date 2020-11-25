package com.fixme.router.sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import com.fixme.router.App;
import com.fixme.commons.messaging.*;
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
        
        log.info("Starting BrokerSocketServer thread");
        try {
            // Instantiate a socket server on 0.0.0.0:5000 for new broker socket connections
            serverSocket = new ServerSocket(this.port, this.backlog, Inet4Address.getByName("0.0.0.0"));

            while (true) {
                // Wait for a connection
                Socket clientSocket = serverSocket.accept();
                
                // Log that a new connection has been received
                log.info("Connection " + clientSocket.toString());

                // Create a new RouteEntry instance
                RouteEntry routeEntry = new RouteEntry(clientSocket, this.type);

                // Add routing entry to Application's routing table, returns ID
                String clientId = App.routingTable.addEntry(routeEntry);

                // Send a logon message to client using PrintWriter
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                // out.println((this.type.equalsIgnoreCase("broker") ? Message.bro : )));
                

                // Spawn a thread to listen for incoming messages from client, handle replies and routing as well
                App.executor.submit(new ClientSocketMaintainer(clientSocket, routeEntry));
            }
        } catch (IOException ex) {
            // IOException, could not start socket server 
            log.severe("Could not start Broker Server: " + ex.getMessage());
        }
    }
}
