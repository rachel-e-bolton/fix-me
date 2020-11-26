package com.fixme.router.request;

import java.net.Socket;

import com.fixme.commons.messaging.Message;

public class Request {
	public Socket source;
	public Message message;
	public RequestType type;
	public Response response;

	public Request(Socket source, Message message) {
		this.source = source;
		this.message = message;
	}

	public String socketFriendlyName() {
		String ip = this.source.getInetAddress().toString();
		Integer port = this.source.getPort();

		return String.format("%s:%d", ip.substring(1, ip.length()), port);
	}
}
