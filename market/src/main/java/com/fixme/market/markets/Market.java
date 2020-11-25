package com.fixme.market.markets;

import java.util.*;

public class Market {
	public String id;
	public String name;
	public List<Instrument> instruments = new ArrayList<Instrument>();

	public Market(String name) {
		this.name = name;
	}

	public Instrument instrumenByName(String name) {
		for (Instrument i : instruments) {
			if (i.name.equalsIgnoreCase(name)) return i;
		}
		return null;
	}

	public Instrument instrumenByCode(String name) {
		for (Instrument i : instruments) {
			if (i.code.equalsIgnoreCase(name)) return i;
		}
		return null;
	}
}
