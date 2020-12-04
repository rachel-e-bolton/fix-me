package com.fixme.market.markets;

import java.util.logging.Logger;

import com.fixme.commons.database.Database;

public class CryptoMarket extends Market {
  static {
    System.setProperty("java.util.logging.SimpleFormatter.format",
        "[%1$tF %1$tT] [\u001b[35;1mMARKET\u001b[0m] [%4$-7s] %5$s %n");
  }

  public static final Logger log = Logger.getLogger("Market");

  public CryptoMarket() {
    super("Crypto");

    // This should be pulled from a file to add alternate markets.

    instruments.add(new Instrument("Crypto", "ETH", "Ethereum"));
    instruments.add(new Instrument("Crypto", "XRP", "Ripple"));
    instruments.add(new Instrument("Crypto", "LTC", "Litecoin"));
    instruments.add(new Instrument("Crypto", "USDT", "Tether"));
    instruments.add(new Instrument("Crypto", "BCH", "Bitcoin Cash"));
    instruments.add(new Instrument("Crypto", "LIBRA", "Libra"));
    instruments.add(new Instrument("Crypto", "XMR", "Monero"));
    instruments.add(new Instrument("Crypto", "EOS", "EOS"));
    instruments.add(new Instrument("Crypto", "BNB", "Binance Coin"));
  }

  public void saveInstruments() {
    for (Instrument instrument: super.instruments) {
      try {
        Database.registerInstrument(instrument.marketName, instrument.code, instrument.name);
      } catch (Exception e) {
        e.printStackTrace();
        log.warning(String.format("An exception occured when attempting to write the instrument to the DB: [%s]\nThe application will continue, but reporting my be affected.\n", e.getMessage()));
      }
    }
  }
}
