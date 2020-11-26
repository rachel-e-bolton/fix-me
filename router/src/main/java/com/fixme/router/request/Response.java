package com.fixme.router.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.fixme.commons.messaging.Message;

public class Response {
	private Socket destination;
	private Message message;

	public Response (Socket destination, Message message) {
		this.destination = destination;
		this.message = message;
	}

	public void send() throws IOException {
		PrintWriter out = new PrintWriter(this.destination.getOutputStream(), true);
		out.println(this.message.toString());
	}
}
