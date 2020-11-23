package fix.router.exchange;

import fix.router.exchange.messaging.Message;

public class RoutingProcessor extends MessageHandler {
	public RoutingProcessor(MessageHandler nextHandler) {
		super(nextHandler);
	}

	public String process(Message message) {
		LOGGER.info(String.format("Got message = %s", message));
		
		if (nextHandler != null) {
            return this.nextHandler.process(message);
        } else {
            return message.toString();
        }

	}
}
