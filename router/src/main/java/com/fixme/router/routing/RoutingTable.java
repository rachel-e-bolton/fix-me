package com.fixme.router.routing;

import java.net.Socket;
import java.util.ArrayList;

import com.fixme.router.routing.*;

public class RoutingTable {
    private final ArrayList<RouteEntry> routingTable = new ArrayList<>();
    private Integer brokerCount = 0;
    private Integer marketCount = 0;

    public void addEntry(RouteEntry entry) {
        entry.id = generateId(entry.type);
        this.routingTable.add(entry);
    }

    private String generateId(String type) {
        if (type.equals("broker")) {
            return String.format("B%010d", ++brokerCount);
        } else {
            return String.format("M%010d", ++marketCount);
        }
    }

    public RouteEntry findEntry(String clientId) {
        for (RouteEntry entry : routingTable) {
            if (entry.id.equalsIgnoreCase(clientId)) {
                return entry;
            }
        }
        return null;
    }

    public RouteEntry findMarket(String name) {
        for (RouteEntry entry : routingTable) {
            if (entry.name.equalsIgnoreCase(name)) {
                return entry;
            }
        }
        return null;
    }


    public RouteEntry findEntry(Socket socket) {
        for (RouteEntry entry : routingTable) {
            if (entry.socket == socket) {
                return entry;
            }
        }
        return null;
    }

    public void deleteEntry(Socket socket) {
        for (RouteEntry entry : routingTable) {
            if (entry.socket == socket) {
                this.routingTable.remove(entry);
            }
        }
    }

    public void deleteEntry(RouteEntry routeEntry) {
        for (RouteEntry entry : routingTable) {
            if (entry.id.equalsIgnoreCase(routeEntry.id)) {
                this.routingTable.remove(entry);
            }
        }
    }
}
