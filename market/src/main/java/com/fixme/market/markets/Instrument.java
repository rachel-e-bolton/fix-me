package com.fixme.market.markets;

import java.util.concurrent.ThreadLocalRandom;

public class Instrument {
  public String marketName;
  public String name;
  public String code;
  public Integer availableUnits;
  public Double minBuyPrice;
  public Double maxSellPrice;

  public Instrument(String marketName, String code, String name) {
    this.marketName = marketName;
    this.name = name;
    this.code = code;
    this.availableUnits = ThreadLocalRandom.current().nextInt(0, 1000);
    this.minBuyPrice = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
    this.maxSellPrice = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
  }
}