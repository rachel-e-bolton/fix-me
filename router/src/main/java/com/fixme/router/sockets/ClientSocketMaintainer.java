package com.fixme.router.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import com.fixme.commons.messaging.*;

import com.fixme.router.App;
import com.fixme.router.processors.*;
import com.fixme.router.request.Request;
import com.fixme.router.request.Response;

public class ClientSocketMaintainer implements Runnable {

    private Socket socket;
    public String socketFriendlyName;
    private static final Logger log = Logger.getLogger("Router");

    public ClientSocketMaintainer(Socket socket) {
        this.socket = socket;
        String ip = socket.getInetAddress().toString();
		Integer port = socket.getPort();
		this.socketFriendlyName = String.format("%s:%d", ip.substring(1, ip.length()), port);
    }

    public void run() {

        log.info(String.format("Starting socket maintainer for %s", socketFriendlyName));

        RequestHandler request = new ValidationProcessor(
            new ClassificationProcessor(
                new RelayProcessor(null)
            )
        );

        String inputLine;

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                Response response = request.process(new Request(socket, new Message(inputLine)));
                if (response != null) {
                    response.send();
                }
                // TODO : Log here for fun times
            }    
        } catch (IOException e) {
            log.severe(String.format("[%s] %s", socketFriendlyName, e.getMessage()));
        }
  
        // Delete RouteEntry when this thread is done processing
        log.info(String.format("Socket maintainer for %s shutting down", socketFriendlyName));
        App.routingTable.deleteEntry(this.socket);
    }
}
