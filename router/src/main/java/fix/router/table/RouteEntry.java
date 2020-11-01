package fix.router.table;

import java.net.Socket;

public class RouteEntry {
    public Socket socket;
    public String name;
    public String type;

    public RouteEntry(Socket socket, String name, String type) {
        this.socket = socket;
        this.name   = name;
        this.type   = type;
    }
}
