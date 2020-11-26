package com.fixme.router.processors;

import com.fixme.commons.messaging.Message;
import com.fixme.commons.messaging.MessageStaticFactory;
import com.fixme.router.App;
import com.fixme.router.request.Request;
import com.fixme.router.request.RequestType;
import com.fixme.router.request.Response;
import com.fixme.router.routing.MarketRouteEntry;
import com.fixme.router.routing.RouteEntry;

public class RelayProcessor extends RequestHandler {

	public RelayProcessor(RequestHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public Response process(Request request) {
		if (request.type == RequestType.BUY || request.type == RequestType.SELL) {
			String marketName = request.message.get("M");
			RouteEntry market = App.routingTable.findMarket(marketName);

			if (market != null) {
				return new Response(market.socket, request.message);
			} else {
				return new Response(request.source, MessageStaticFactory.failResponse("Market unknown"));
			}
		}

		if (request.type == RequestType.ACCEPT || request.type == RequestType.REJECT) {
			String brokerId = request.message.get("109");
			RouteEntry broker = App.routingTable.findEntry(brokerId);

			if (broker != null) {
				return new Response(broker.socket, request.message);
			} else {
				return new Response(request.source, MessageStaticFactory.failResponse("Broker no longer connected"));
			}
		}
		return new Response(request.source, MessageStaticFactory.failResponse("Unhandled message, contact admin"));
	}
	
}
