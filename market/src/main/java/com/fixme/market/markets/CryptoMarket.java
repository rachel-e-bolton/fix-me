package com.fixme.market.markets;

public class CryptoMarket extends Market{
	public CryptoMarket() {
		super("Crypto");

        // This should be pulled from a file to add alternate markets.

		instruments.add(new Instrument("ETH", "Ethereum"));
        instruments.add(new Instrument("XRP", "Ripple"));
        instruments.add(new Instrument("LTC", "Litecoin"));
        instruments.add(new Instrument("USDT", "Tether"));
        instruments.add(new Instrument("BCH", "Bitcoin Cash"));
        instruments.add(new Instrument("LIBRA", "Libra"));
        instruments.add(new Instrument("XMR", "Monero"));
        instruments.add(new Instrument("EOS", "EOS"));
        instruments.add(new Instrument("BNB", "Binance Coin"));
        instruments.add(new Instrument("ETH", "Ethereum"));
	}
}
