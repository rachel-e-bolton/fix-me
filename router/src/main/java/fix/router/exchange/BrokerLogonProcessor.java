package fix.router.exchange;

import fix.router.Router;
import fix.router.exchange.messaging.*;
import fix.router.routing.Entry;

public class BrokerLogonProcessor extends MessageHandler {
	public BrokerLogonProcessor(MessageHandler nextHandler) {
		super(nextHandler);
	}

	public String process(Message message) {

		if (message.get(35).equals("A")) {
			String clientName = message.get(1);
			
			Entry entry = Router.routingTable.findEntry(message.socket);
			if (entry.id != null) {
				return (new Message(new Exception("Already logged in"))).toString();
			}

			entry.id = Router.routingTable.generateBrokerId();
			entry.name = clientName;
			entry.type = "broker";
			String reply = String.format("35=A|109=%s|", entry.id);
			return Checksum.appendCheckSum(reply);
		}

		if (message.get(109).length() < 1) {
			return (new Message(new Exception("You must logon to trades"))).toString();
		}
		
		if (nextHandler != null) {
            return this.nextHandler.process(message);
        } else {
            return message.toString();
        }

	}
}