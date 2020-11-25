package com.fixme.router.processors;

import java.io.PrintWriter;
import java.util.logging.Logger;

import com.fixme.commons.messaging.Message;

public abstract class MessageHandler {
    protected static final Logger LOGGER = Logger.getLogger( "Router" );
	protected MessageHandler nextHandler;

    public MessageHandler(MessageHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void process(PrintWriter out, Message message);
    public abstract void process(Message message);
}
