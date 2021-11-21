package controller;

import dungeongeneral.Item;
import dungeongeneral.PlayerDesc;
import dungeongeneral.Treasure;

import java.util.Map;

class PlayerDescTestImpl implements PlayerDesc {

  private final String uniqueCode;

  PlayerDescTestImpl(String uniqueCode) {
    this.uniqueCode = uniqueCode;
  }

  @Override
  public Map<Treasure, Integer> getTreasure() {
    return null;
  }

  @Override
  public Map<Item, Integer> getItems() {
    return null;
  }

  @Override
  public int getMissCount() {
    return 0;
  }

  @Override
  public int getHitCount() {
    return 0;
  }

  @Override
  public int getKillCount() {
    return 0;
  }

  @Override
  public String toString() {
    return this.uniqueCode;
  }
}
