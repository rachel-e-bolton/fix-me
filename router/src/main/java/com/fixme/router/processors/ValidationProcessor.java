package com.fixme.router.processors;

import java.io.PrintWriter;

import com.fixme.commons.messaging.Message;

public class ValidationProcessor extends MessageHandler {

	public ValidationProcessor(MessageHandler nextHandler) {
		super(nextHandler);
	}

	public void process(PrintWriter out, Message message) {

		try {
			message.validate();
			LOGGER.info("Message has been validated");
		} catch (Exception e) {
			out.println((new Message(e)).toString());;
		}

		if (nextHandler != null) {
            this.nextHandler.process(out, message);
        } else {
            out.println(message.toString());
        }
	}
}
