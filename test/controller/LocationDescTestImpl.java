package controller;

import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.*;

import java.util.List;
import java.util.Map;

class LocationDescTestImpl implements ReadOnlyLocation {

  private final String uniqueCode;

  LocationDescTestImpl(String uniqueCode) {
    this.uniqueCode = uniqueCode;
  }

  @Override
  public boolean hasItems() {
    return false;
  }

  @Override
  public boolean hasTreasure() {
    return false;
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
  public boolean isCave() {
    return false;
  }

  @Override
  public boolean isTunnel() {
    return false;
  }

  @Override
  public boolean hasMonster() {
    return false;
  }

  @Override
  public boolean hasNoMonster() {
    return false;
  }

  @Override
  public boolean hasDeadMonster() {
    return false;
  }

  @Override
  public boolean hasInjuredMonster() {
    return false;
  }

  @Override
  public boolean hasHealthyMonster() {
    return false;
  }

  @Override
  public boolean hasAliveMonster() {
    return false;
  }

  @Override
  public boolean hasItem(Item item) {
    return false;
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
  public Odour getOdour() {
    return null;
  }

  @Override
  public boolean hasPit() {
    return false;
  }

  @Override
  public boolean isDiscovered() {
    return false;
  }

  @Override
  public String toString() {
    return this.uniqueCode;
  }
}
