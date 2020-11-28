package com.fixme.commons.orders;

import com.fixme.commons.messaging.Message;

public class Order {
	public Boolean valid = true;
	public String market;
	public String instrument;
	public String amount;
	public String price;
	public String clientId;

	public Order(String market, String instrument, Integer amount, Double price, String clientId) {
		this.market = market;
		this.instrument = instrument;
		this.amount = amount.toString();
		this.price = price.toString();
		this.clientId = clientId;
		this.validateOrder();
	}

	public Order(Message message) {
		this.market =     message.get("M");
		this.instrument = message.get("I");
		this.amount =     message.get("A");
		this.price =      message.get("P");
		this.clientId =   message.get("109");
		this.validateOrder();
	}

	public void validateOrder() {
		// Only checks for negatives at the moment
		if (this.amount.contains("-") || this.price.contains("-")) {
			this.valid = false;
		}
	}
}
