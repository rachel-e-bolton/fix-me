package com.fixme.market.markets;

import java.util.concurrent.ThreadLocalRandom;

public class Instrument {
	public String name;
	public String code;
	public Integer availableUnits;
	public Double minBuyPrice;
	public Double maxSellPrice;

	public Instrument(String code, String name) {
		this.name = name;
		this.code = code;
		this.availableUnits = ThreadLocalRandom.current().nextInt(0, 1000);
		this.minBuyPrice = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		this.maxSellPrice = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
	}
}