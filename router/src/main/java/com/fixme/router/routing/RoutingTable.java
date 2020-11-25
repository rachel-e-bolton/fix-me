package com.fixme.router.routing;

import java.net.Socket;
import java.util.ArrayList;

public class RoutingTable {
    private final ArrayList<RouteEntry> routingTable = new ArrayList<>();
    private Integer brokerCount = 0;
    private Integer marketCount = 0;

    public String addEntry(RouteEntry entry) {
        this.routingTable.add(entry);
        return (entry.type.equalsIgnoreCase("broker") ? generateBrokerId() : generateMarketId() );
    }

    public String generateBrokerId() {
        return String.format("B%010d", ++brokerCount);
    }

    public String generateMarketId() {
        return String.format("M%010d", ++marketCount);
    }

    public RouteEntry findEntry(String clientId) {
        for (RouteEntry entry : routingTable) {
            if (entry.id.equalsIgnoreCase(clientId)) {
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

    public void deleteEntry(String clientId) {
        for (RouteEntry entry : routingTable) {
            if (entry.id.equalsIgnoreCase(clientId)) {
                this.routingTable.remove(entry);
            }
        }
    }
}
