package fix.router.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import fix.router.exchange.*;
import fix.router.exchange.messaging.Message;
import fix.router.Router;

public class ClientSocketMaintainer implements Runnable {

    private Socket socket;    
    private final Logger LOGGER = Logger.getLogger("SocketMaintainer");;

    public ClientSocketMaintainer(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        
        MessageHandler messageHandler = new ValidationProcessor(
            new BrokerLogonProcessor(
                new RoutingProcessor(null)    
            )
        );

        String inputLine;
        
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                Message message = new Message(inputLine);
                message.socket = socket;
                out.println(messageHandler.process(message));
            }    
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Router.routingTable.deleteEntry(this.socket);
    }
}
