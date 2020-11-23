package fix.router.exchange.messaging;

import java.net.Socket;
import java.util.*;

import fix.router.exchange.exceptions.ValidationException;

public class Message {

	public Boolean valid = true;
	public Socket socket = null;
	private String rawMessage = null;
	private List<String> rawParts = null;

	public Message() {
	}

	public Message(String rawMessage) {
		this.rawMessage = rawMessage.trim();
		this.rawParts = Arrays.asList(this.rawMessage.split("\\|"));
	}

	public Message(Exception exp) {
		String reply = String.format("35=3|58=Error: %s|", exp.getMessage());

		String checksum = Checksum.calculateChecksum(reply);
		rawMessage = reply + "10=" + checksum + "|";
	}

	public void validate() throws ValidationException {
		Validation.validateMessageFormat(rawParts);
		Validation.validateCodePosition(rawParts);
		Validation.validateDuplicateCodes(rawParts);
		Validation.validateChecksum(rawParts);
	}

	public String get(Integer code) {
		String key = Integer.toString(code);
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

	public static Message failResponse(String failureMessage) {
		String reply = String.format("35=3|58=Error: %s|", failureMessage);

		Message message = new Message();
		message.rawMessage = Checksum.appendCheckSum(reply);
		return message;
	}

	public static Message fromRawString(String str) {
		Message message = new Message();
		message.rawMessage = str.trim();
		message.rawParts = Arrays.asList(message.rawMessage.split("\\|"));
		return message;
	}

}
