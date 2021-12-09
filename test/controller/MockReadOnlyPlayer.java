package controller;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeongeneral.Sound;
import dungeongeneral.Treasure;

import java.util.List;
import java.util.Map;

class MockReadOnlyPlayer implements ReadOnlyPlayer {

  private final String uniqueCode;

  MockReadOnlyPlayer(String uniqueCode) {
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
  public Coordinate getCoordinates() {
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
    return true;
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
  public int getHealth() {
    return 0;
  }

  @Override
  public Sound getPreviousShotResult() {
    return null;
  }

  @Override
  public boolean isAt(ReadOnlyLocation location) {
    return false;
  }

  @Override
  public String toString() {
    return this.uniqueCode;
  }
}
