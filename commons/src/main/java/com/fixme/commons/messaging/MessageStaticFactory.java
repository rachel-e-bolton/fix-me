package com.fixme.commons.messaging;

import com.fixme.commons.orders.*;

public class MessageStaticFactory {
	public static Message failResponse(String failureMessage) {
		String reply = String.format("35=3|58=Error: %s|", failureMessage);

		Message message = new Message();
		message.rawMessage = MessageChecksum.appendCheckSum(reply);
		return message;
	}

	public static Message fromRawString(String str) {
		Message message = new Message(str);
		return message;
	}

	public static Message socketLogonMessage(String id) {
		String reply = String.format("35=L|109=%s|", id);
		Message message = new Message();
		message.rawMessage = MessageChecksum.appendCheckSum(reply);
		return message;
	}

	public static Message orderMessage(Order order, OrderType type) {

		String reply = String.format("35=S|109=%s|M=%s|I=%s|A=%s|P=%s|", 
				order.clientId, order.market, order.instrument, order.amount, order.price);

		Message message = new Message();
		message.rawMessage = MessageChecksum.appendCheckSum(reply);
		return message;
	}

	public static Message rejectOrder(Order order, String reason) {
		String reply = String.format("35=0|109=%s|M=%s|I=%s|A=%s|P=%s|58=%s|", 
				order.clientId, order.market, order.amount, order.price, reason);

		Message message = new Message(MessageChecksum.appendCheckSum(reply));		
		return message;
	}

	public static Message acceptOrder(Order order) {
		String reply = String.format("35=1|109=%s|M=%s|I=%s|A=%s|P=%s|", 
				order.clientId, order.market, order.amount, order.price);
		
		Message message = new Message(MessageChecksum.appendCheckSum(reply));
		return message;
	}
}
