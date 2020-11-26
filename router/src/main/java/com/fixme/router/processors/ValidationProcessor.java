package com.fixme.router.processors;

import java.util.logging.Logger;

import com.fixme.commons.messaging.MessageStaticFactory;
import com.fixme.commons.messaging.MessageValidation;

import com.fixme.router.request.Request;
import com.fixme.router.request.Response;

public class ValidationProcessor extends RequestHandler {

	public ValidationProcessor(RequestHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public Response process(Request request) {
		Logger log = Logger.getLogger("ValidationProcessor");
		log.info(String.format("Received message from %s => \u001B[36m%s\u001B[0m", request.socketFriendlyName(), request.message.toString()));
		try {
			MessageValidation.validateMessage(request.message.toString());
			return nextHandler.process(request);
		} catch (Exception e) {
			return new Response(request.source, MessageStaticFactory.failResponse(e.getMessage()));
		}
	}
}
