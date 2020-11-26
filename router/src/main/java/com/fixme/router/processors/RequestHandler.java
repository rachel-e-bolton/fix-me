package com.fixme.router.processors;

import com.fixme.router.request.Request;
import com.fixme.router.request.Response;

public abstract class RequestHandler {

	protected RequestHandler nextHandler;

	public RequestHandler(RequestHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public abstract Response process(Request request);
}
