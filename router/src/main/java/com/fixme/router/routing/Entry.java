package com.fixme.router.routing;

import java.net.Socket;

import fix.router.Router;

public class Entry {
    public Socket socket;
    public String name;
    public String type;
    public String id;

    public Entry(Socket socket, String type) {
        this.socket = socket;
        if (type.equalsIgnoreCase("market")) {
            this.type = "market";
            this.id = Router.routingTable.generateMarketId();
        }
        if (type.equalsIgnoreCase("broker")) {
            this.type = "broker";
            this.id = Router.routingTable.generateBrokerId();
        }
    }

    @Override
    public String toString() {
        return String.format("Id:%s, Port:%d, Name:%s", id, socket.getLocalPort(), name);
    }
}
