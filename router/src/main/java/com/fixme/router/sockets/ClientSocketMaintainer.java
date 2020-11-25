package com.fixme.router.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import com.fixme.commons.messaging.*;

import com.fixme.router.App;
import com.fixme.router.processors.*;

public class ClientSocketMaintainer implements Runnable {

    private Socket socket;
    private static final Logger log = Logger.getLogger("ClientSocketMaintainer");

    public ClientSocketMaintainer(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        // setup handlers here

        MessageHandler messageHandler = 
            new ValidationProcessor(
                new RoutingProcessor(null)    
        );

        String inputLine;
        
        try {
            // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          
            while ((inputLine = in.readLine()) != null) {

                Message message = new Message(inputLine);

                message.socket = socket;

                messageHandler.process(message);

            }    
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        // Delete RouteEntry when this thread is done processing
        App.routingTable.deleteEntry(this.socket);
    }
}
