package fix.router.sockets;

import java.util.logging.Level;
import java.util.logging.Logger;

import fix.router.Router;

public class SocketMessageDispatcher {

    private static final Logger LOGGER = Logger.getLogger( "SocketMessageDispatcher" );
    public Boolean ABORT = false;

    public SocketMessageDispatcher(String message) throws Exception {
        LOGGER.log(Level.INFO, "Received message for dispatch [" + message + "]");
        if (!messageIsValid(message)) {
            throw new Exception("Message is not valid :(");
        }
    }

    private Boolean messageIsValid(String message) {
        return true;
    }

    public void routeMessage(String message) {
        if (message.equalsIgnoreCase("abort")) {
            this.ABORT = true;
        }
    }

    public String response() {
        return "asd";
    }
}
