package com.fixme.router.processors;

import java.io.PrintWriter;

import com.fixme.commons.messaging.Message;
import com.fixme.commons.messaging.MessageStaticFactory;
import com.fixme.commons.messaging.MessageValidation;

public class ValidationProcessor extends MessageHandler {

	public ValidationProcessor(MessageHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public void process(PrintWriter out, Message message) {


		try {
			MessageValidation.validateMessage(message.toString());
		} catch (Exception ex) {
			// Message was not valid, send failure message
			out.println(MessageStaticFactory.failResponse("Validation Failed " + ex.getMessage()));
			return;
		}

		if (nextHandler != null) {
			this.nextHandler.process(out, message);
		}

		return;
	}

	@Override
	public void process(Message message) {
		// TODO Auto-generated method stub
	}
}
