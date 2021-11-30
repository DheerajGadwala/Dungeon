package controller;

import dungeon.ReadOnlyLocation;
import dungeon.ReadOnlyPlayer;
import dungeongeneral.*;

import java.util.List;
import java.util.Map;

class PlayerDescTestImpl implements ReadOnlyPlayer {

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
  public Coordinate getPosition() {
    return null;
  }

  @Override
  public List<Direction> getPossibleRoutes() {
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
  public boolean isAlive() {
    return false;
  }

  @Override
  public boolean hasArrow() {
    return false;
  }

  @Override
  public ReadOnlyPlayer getDesc() {
    return null;
  }

  @Override
  public ReadOnlyLocation getLocationDescription() {
    return null;
  }

  @Override
  public String toString() {
    return this.uniqueCode;
  }
}
