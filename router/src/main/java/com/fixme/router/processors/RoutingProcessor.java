package com.fixme.router.processors;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.stream.Stream;

import com.fixme.router.App;
import com.fixme.commons.messaging.Message;

public class RoutingProcessor extends MessageHandler {
	public RoutingProcessor(MessageHandler nextHandler) {
		super(nextHandler);
	}

	public void process(PrintWriter out, Message message) {
		LOGGER.info(String.format("Got message = %s", message));
		
		String messagetype = message.get("35");

		if (Stream.of("0", "1", "B", "S").anyMatch(messagetype::equalsIgnoreCase)) {
			// Socket socket = App.routingTable
		} else {
			LOGGER.severe(String.format("Unknown message type [%s]", message.get(35)));
			Message response = Message.failResponse(String.format("Unknown message type [%s]", message.get(35)));
			out.println(response.toString());
		}
	}

	// public Message routeMessageAndWaitForReponse(Socket socket, Message message) {
	// 	// Write some code HERE GLEN!!! AAAAHHHHHHHH
	// }
}
