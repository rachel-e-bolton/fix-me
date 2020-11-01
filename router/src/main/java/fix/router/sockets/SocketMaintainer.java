package fix.router.sockets;

import java.io.*;
import java.net.*;

public class SocketMaintainer implements Runnable {
    Socket socket;
    public SocketMaintainer(Socket s) {
        this.socket = s;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting...");
                
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String dataReceived = in.readLine();
                System.out.println(dataReceived);

                if (dataReceived != null) {

                    SocketMessageDispatcher dispatch = new SocketMessageDispatcher(dataReceived.strip());

                    if (dispatch.ABORT) {
                        out.println("abort");
                        socket.close();
                        System.out.println("Aborting...");
                        break;
                    } else {
                        out.println(dispatch.response());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
