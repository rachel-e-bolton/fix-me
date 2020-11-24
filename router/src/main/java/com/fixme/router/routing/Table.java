package com.fixme.router.routing;

import java.net.Socket;
import java.util.ArrayList;

public class Table {
    private final ArrayList<Entry> routingTable = new ArrayList<>();
    private Integer IdCount = 0;

    public void addEntry(Entry entry) {
        this.routingTable.add(entry);
        DEBUG_PrintRoutingTable();
    }

    public String generateBrokerId() {
        IdCount++;
        return String.format("B%010d", IdCount);
    }

    public String generateMarketId() {
        IdCount++;
        return String.format("M%010d", IdCount);
    }

    public Entry findEntry(Integer portNumber) {
        return this.routingTable.get(0);
    }

    public Entry findEntry(String name) {
        return this.routingTable.get(0);
    }

    public Entry findEntry(Socket socket) {
        for (Entry entry : routingTable) {
            if (entry.socket == socket) {
                return entry;
            }
        }
        return null;
    }

    public void deleteEntry(Socket socket) {

        for (Entry entry : routingTable) {
            if (entry.socket == socket) {
                this.routingTable.remove(entry);
            }
        }
    }


    public void DEBUG_PrintRoutingTable() {
        System.out.println(this.routingTable);
    }
}
