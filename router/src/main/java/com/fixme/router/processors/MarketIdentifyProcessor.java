package com.fixme.router.processors;

import java.util.logging.Logger;

import com.fixme.commons.messaging.MessageStaticFactory;
import com.fixme.router.App;
import com.fixme.router.request.Request;
import com.fixme.router.request.Response;
import com.fixme.router.routing.RouteEntry;

public class MarketIdentifyProcessor extends RequestHandler {

	public MarketIdentifyProcessor(RequestHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public Response process(Request request) {
		Logger log = Logger.getLogger("ClientSocketMaintainer");
		// Add name to route entry for a market, this allows multiple markets
		String id = request.message.get("109");
		String name = request.message.get("M");

		if (id.length() > 0 && name.length() > 0) {
			RouteEntry market = App.routingTable.findEntry(id);
			market.name = name;
			log.info(String.format("Market name '%s' added to %s in routing table", name, id));
			// Not responding to market, probably should send an ACK
			return null;
		} else {
			return new Response(request.source, MessageStaticFactory.failResponse("Invalid message, require name and id of market"));
		}
	}
	
}
