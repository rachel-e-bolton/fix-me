package fix.market.financial;

import java.util.concurrent.ThreadLocalRandom;

public class Instrument {
	public String name;
	public String code;
	public Integer availableUnits;
	public Double minPurchasePrice;

	public Instrument(String code, String name) {
		this.name = name;
		this.code = code;
		this.availableUnits = ThreadLocalRandom.current().nextInt(0, 1000);
		this.minPurchasePrice = Double.valueOf(ThreadLocalRandom.current().nextInt(0, 100));
	}
}
