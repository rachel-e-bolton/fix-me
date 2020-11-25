package com.fixme.commons.messaging;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Message {
	public Boolean valid = true;
	public Socket socket = null;
	protected String rawMessage = null;
	private List<String> rawParts = null;

	public Message() {
	}

	public Message(String rawMessage) {
		this.rawMessage = rawMessage.trim();
		this.rawParts = Arrays.asList(this.rawMessage.split("\\|"));
	}

	public String get(String key) {
		for (String part : rawParts) {
			if (part.startsWith(key + "=")) {
				return part.substring(key.length() + 1, part.length());
			}
		}
		return "";
	}

	@Override
    public String toString() { 
        return rawMessage; 
	}
}
