package fix.router.exchange;

import fix.router.exchange.exceptions.ValidationException;
import fix.router.exchange.messaging.Message;

public class ValidationProcessor extends MessageHandler {

	public ValidationProcessor(MessageHandler nextHandler) {
		super(nextHandler);
	}

	public String process(Message message) {
		LOGGER.info(String.format("Validating"));

		try {
			message.validate();
		} catch (ValidationException e) {
			return (new Message(e)).toString();
		}

		if (nextHandler != null) {
            return this.nextHandler.process(message);
        } else {
            return message.toString();
        }
	}
}
