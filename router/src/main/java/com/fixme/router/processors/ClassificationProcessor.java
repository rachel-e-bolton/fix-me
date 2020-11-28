package com.fixme.router.processors;

import java.util.logging.Logger;

import com.fixme.commons.messaging.MessageStaticFactory;
import com.fixme.router.App;
import com.fixme.router.request.Request;
import com.fixme.router.request.RequestType;
import com.fixme.router.request.Response;

public class ClassificationProcessor extends RequestHandler {

	public ClassificationProcessor(RequestHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public Response process(Request request) {
		String messageType = request.message.get("35");
		// Logger log = Logger.getLogger("Router");
		
		if (	   messageType.equalsIgnoreCase("B")) {
			request.type = RequestType.BUY;
			App.log.info("Message classified as [BUY]");
		} else if (messageType.equalsIgnoreCase("S")) {
			request.type = RequestType.SELL;
			App.log.info("Message classified as [SELL]");
		} else if (messageType.equalsIgnoreCase("0")) {
			request.type = RequestType.REJECT;
			App.log.info("Message classified as [REJECT]");
		} else if (messageType.equalsIgnoreCase("1")) {
			request.type = RequestType.ACCEPT;
			App.log.info("Message classified as [ACCEPT]");
		} else if (messageType.equalsIgnoreCase("I")) {
			request.type = RequestType.IDENTIFY;
			App.log.info("Message classified as [IDENTIFY]");
			return new MarketIdentifyProcessor(null).process(request);
		} else {
			// Should never happen, but just incase send a fail response
			App.log.warning("Message could not be classified");
			return new Response(request.source, MessageStaticFactory.failResponse("Unknown message type"));
		}
		return nextHandler.process(request);
	}
	
}
