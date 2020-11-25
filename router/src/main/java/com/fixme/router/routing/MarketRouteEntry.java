package com.fixme.router.routing;

import java.net.Socket;

public class MarketRouteEntry extends RouteEntry {
	public MarketRouteEntry(Socket socket) {
		super(socket, "market");
		this.idTag = "109";
	}
}
