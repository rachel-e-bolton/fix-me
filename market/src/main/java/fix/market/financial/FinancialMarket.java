package fix.market.financial;

import java.util.*;

public class FinancialMarket {
	public String name;
	public List<Instrument> instruments = new ArrayList<Instrument>();

	public FinancialMarket(String name) {
		this.name = name;
	}

	public Instrument instrumenByName(String name) {
		return new Instrument("code", "name");		
	}
}
