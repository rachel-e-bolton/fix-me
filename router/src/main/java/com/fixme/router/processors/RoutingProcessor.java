package com.fixme.router.processors;

import java.io.PrintWriter;
// import java.net.Socket;
import java.util.logging.Logger;
import java.util.stream.Stream;

// import com.fixme.router.App;
import com.fixme.commons.messaging.Message;
import com.fixme.commons.messaging.MessageStaticFactory;

public class RoutingProcessor extends MessageHandler {
	public RoutingProcessor(MessageHandler nextHandler) {
		super(nextHandler);
	}

	public void process(Message message) {}

	public void process(PrintWriter out, Message message) {
		Logger log = Logger.getLogger( "Market" );
		log.info(String.format("Got message = %s", message));
		
		String messagetype = message.get("35");

		if (Stream.of("0", "1", "B", "S").anyMatch(messagetype::equalsIgnoreCase)) {
			// Socket socket = App.routingTable
		} else {
			log.severe(String.format("Unknown message type [%s]", message.get("35")));
			Message response = MessageStaticFactory.failResponse(String.format("Unknown message type [%s]", message.get("35")));
			out.println(response.toString());
		}
	}
}
