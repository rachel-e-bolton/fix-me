package fix.router.exchange;

import java.util.logging.Logger;

import fix.router.exchange.messaging.Message;

public abstract class MessageHandler {
    protected static final Logger LOGGER = Logger.getLogger( "Router" );
	protected MessageHandler nextHandler;

    public MessageHandler(MessageHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract String process(Message message);
}
