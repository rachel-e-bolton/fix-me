package com.fixme.router.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import fix.router.exchange.*;
import fix.router.exchange.messaging.Message;
import fix.router.routing.Entry;
import fix.router.Router;

public class ClientSocketMaintainer implements Runnable {

    private Socket socket;
    private Entry routeEntry;
    private static final Logger log = Logger.getLogger( "Market" );

    public ClientSocketMaintainer(Socket socket, Entry routerEntry) {
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
            if (this.routeEntry.type.equals("market")) {
                out.println(Message.marketLogonMessage(this.routeEntry.id));
                out.flush();
            } else if (this.routeEntry.type.equals("broker")) {
                out.println(Message.brokerLogonMessage(this.routeEntry.id));
                out.flush();
            }

            while ((inputLine = in.readLine()) != null) {
                Message message = new Message(inputLine);
                message.socket = socket;
                messageHandler.process(out, message);
                out.flush();
            }    
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Router.routingTable.deleteEntry(this.socket);
    }
}
