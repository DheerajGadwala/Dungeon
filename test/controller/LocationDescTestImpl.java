package controller;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.LocationDesc;
import dungeongeneral.MatrixPosition;
import dungeongeneral.Treasure;

import java.util.List;
import java.util.Map;

class LocationDescTestImpl implements LocationDesc {

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
  public MatrixPosition getCoordinates() {
    return null;
  }

  @Override
  public List<Direction> getRoutes() {
    return null;
  }

  @Override
  public String toString() {
    return this.uniqueCode;
  }
}
