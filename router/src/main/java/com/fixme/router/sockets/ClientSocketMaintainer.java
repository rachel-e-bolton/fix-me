package com.fixme.router.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import com.fixme.commons.messaging.*;

import com.fixme.router.routing.RouteEntry;
import com.fixme.router.App;

public class ClientSocketMaintainer implements Runnable {

    private Socket socket;
    private RouteEntry routeEntry;
    private static final Logger log = Logger.getLogger( "Market" );

    public ClientSocketMaintainer(Socket socket, RouteEntry routerEntry) {
        this.socket = socket;
        this.routeEntry = routerEntry;
    }

    public void run() {
        log.info("msg");
        MessageHandler messageHandler = new ValidationProcessor(
                new RoutingProcessor(null)    
        );

        String inputLine;
        
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send Logon Message
            // if (this.routeEntry.type.equals("market")) {
            //     out.println(Message.marketLogonMessage(this.routeEntry.id));
            // } else if (this.routeEntry.type.equals("broker")) {
            //     out.println(Message.brokerLogonMessage(this.routeEntry.id));
            // }

            while ((inputLine = in.readLine()) != null) {
                Message message = new Message(inputLine);
                message.socket = socket;
                messageHandler.process(out, message);
            }    
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Delete RouteEntry when this thread is done processing
        App.routingTable.deleteEntry(this.socket);
    }
}
