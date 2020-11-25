package com.fixme.router.routing;

import java.net.Socket;

public class BrokerRouteEntry extends RouteEntry {
	public BrokerRouteEntry(Socket socket) {
		super(socket, "broker");
		this.idTag = "109";
	}
}
