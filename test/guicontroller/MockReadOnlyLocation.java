package guicontroller;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Odour;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.Treasure;

import java.util.List;
import java.util.Map;

/**
 * Mock for the read only location.
 */
public class MockReadOnlyLocation implements ReadOnlyLocation {

  private final String uniqueCode;

  MockReadOnlyLocation(String uniqueCode) {
    if (uniqueCode == null) {
      throw new IllegalArgumentException("unique code can not be null.;");
    }
    this.uniqueCode = uniqueCode;
  }

  @Override
  public boolean hasTreasure() throws IllegalStateException {
    return false;
  }

  @Override
  public Map<Treasure, Integer> getTreasure() throws IllegalStateException {
    return null;
  }

  @Override
  public Map<Item, Integer> getItems() throws IllegalStateException {
    return null;
  }

  @Override
  public boolean isCave() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean isTunnel() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasMonster() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasNoMonster() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasDeadMonster() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasInjuredMonster() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasHealthyMonster() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasAliveMonster() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasItem(Item item) throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasItems() throws IllegalStateException {
    return false;
  }

  @Override
  public Coordinate getCoordinates() {
    return null;
  }

  @Override
  public List<Direction> getPossibleRoutes() throws IllegalStateException {
    return null;
  }

  @Override
  public Odour getOdour() throws IllegalStateException {
    return null;
  }

  @Override
  public boolean hasPit() throws IllegalStateException {
    return false;
  }

  @Override
  public boolean hasSignsOfNearbyPit() {
    return false;
  }

  @Override
  public boolean isDiscovered() {
    return false;
  }

  @Override
  public String toString() {
    return uniqueCode;
  }

}
