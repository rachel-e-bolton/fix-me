package fix.router.table;

import java.net.Socket;
import java.util.ArrayList;

public class RoutingTable {
    private final ArrayList<RouteEntry> routingTable = new ArrayList<>();

    public void addEntry(RouteEntry entry) {
        this.routingTable.add(entry);
        DEBUG_PrintRoutingTable();
    }

    public RouteEntry findEntry(Integer portNumber) {
        return this.routingTable.get(0);
    }

    public RouteEntry findEntry(String name) {
        return this.routingTable.get(0);
    }

    public RouteEntry findEntry(Socket socket) {
        return this.routingTable.get(0);
    }

    public void DEBUG_PrintRoutingTable() {
        System.out.println("Printing Routing Table");
        for (RouteEntry entry : this.routingTable) {
            System.out.print(entry.name);
            System.out.print(" ");
            System.out.print(entry.socket);
            System.out.print(" ");
            System.out.print("\n");
        }        
    }
}
