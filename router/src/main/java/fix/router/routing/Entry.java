package fix.router.routing;

import java.net.Socket;

public class Entry {
    public Socket socket;
    public String name;
    public String type;
    public String id;

    public Entry(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return String.format("Id:%s, Port:%d, Name:%s", id, socket.getLocalPort(), name);
    }
}
