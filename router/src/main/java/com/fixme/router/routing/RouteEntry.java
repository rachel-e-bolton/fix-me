package com.fixme.router.routing;

import java.net.Socket;

public class RouteEntry {
    public Socket socket;
    public String type;
    public String idTag;
    public String id;
    public String name = "";

    public RouteEntry(Socket socket, String type) {
        this.socket = socket;
        this.type = type;
    }
}
