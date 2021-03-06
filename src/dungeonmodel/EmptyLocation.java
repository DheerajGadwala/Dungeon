package dungeonmodel;

import static dungeongeneral.Odour.ODOURLESS;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Odour;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.Treasure;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Singleton Pattern.
 */
class EmptyLocation implements LocationNode {

  private static final EmptyLocation instance = new EmptyLocation();

  private EmptyLocation() {}

  static EmptyLocation getInstance() {
    return instance;
  }

  @Override
  public boolean hasEmptyNodeAt(Direction direction)
      throws IllegalStateException {
    throw new IllegalStateException("This is itself the empty node!");
  }

  @Override
  public void setNeighbour(Direction direction, LocationNode location)
      throws IllegalStateException {
    throw new IllegalStateException("Can not add neighbour to the empty node.");
  }

  @Override
  public Direction getNeighbourDirection(LocationNode neighbour) throws IllegalStateException {
    throw new IllegalStateException("Empty node does not have neighbours.");
  }

  @Override
  public void decreaseTreasureCount(Treasure treasure) throws IllegalStateException {
    throw new IllegalStateException("Can not remove treasure from the empty node.");
  }

  @Override
  public List<LocationNode> getRequiredNodesHelper(
      List<LocationNode> visited,
      List<LocationNode> queue,
      List<Integer> queueD,
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  ) {
    return new ArrayList<>();
  }

  @Override
  public List<LocationNode> getRequiredNodes(
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  ) {
    throw new IllegalStateException("Can not perform this operation from empty node.");
  }

  @Override
  public boolean isTunnel() {
    return false;
  }

  @Override
  public boolean isCave() {
    return false;
  }

  @Override
  public boolean isEmptyNode() {
    return true;
  }

  @Override
  public LocationNode getLocationAt(Direction direction)
      throws IllegalStateException {
    throw new IllegalStateException("Player is on a empty node, this shouldn't be possible!");
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
  public void addTreasure(Map<Treasure, Integer> treasure) throws IllegalStateException {
    throw  new IllegalStateException("can not add treasure to the empty node");
  }

  @Override
  public List<Direction> getPossibleRoutesHelper() {
    return new ArrayList<>();
  }

  @Override
  public Coordinate getCoordinates()
      throws IllegalStateException {
    throw new IllegalStateException("Empty nodes do not have a position!");
  }

  @Override
  public List<Direction> getPossibleRoutes() {
    return new ArrayList<>();
  }

  @Override
  public boolean hasNeighbour(LocationNode that) {
    return false;
  }

  @Override
  public void setMonster(Entity monster) throws IllegalArgumentException {
    throw new IllegalStateException("Can not add monster to sentinel.");
  }

  @Override
  public void createPit() {
    throw new IllegalStateException("Can not add pit to sentinel.");
  }

  @Override
  public boolean hasMonsterHelper() {
    return false;
  }

  @Override
  public boolean hasAliveMonsterHelper() {
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
  public void setItemCount(Item item, int n) {
    throw new IllegalStateException("Can not set arrows here.");
  }

  @Override
  public void decreaseItemCount(Item item) {
    throw new IllegalStateException("Can not decrease arrows here.");
  }

  @Override
  public void discover() {
    throw new IllegalStateException("Can not discover empty location.");
  }

  @Override
  public boolean hasItem(Item item) {
    return false;
  }

  @Override
  public Odour getOdour() {
    return ODOURLESS;
  }

  @Override
  public boolean hasPit() {
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
  public Entity getMonster() throws IllegalStateException {
    return null;
  }

  @Override
  public Entity getMonsterAtEnd(Direction direction, int distance, boolean isStart)
          throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public boolean hasItems() {
    return false;
  }

  @Override
  public ReadOnlyLocation getDesc() {
    return this;
  }

  @Override
  public boolean hasNoMonsterHelper() {
    return false;
  }

  @Override
  public boolean hasDeadMonsterHelper() {
    return false;
  }

  @Override
  public boolean hasInjuredMonsterHelper() {
    return false;
  }

  @Override
  public boolean hasHealthyMonsterHelper() {
    return false;
  }

  @Override
  public void setOtyughRandomizer(Randomizer randomizer) {
    throw new IllegalStateException("No otyughs here.");
  }

  @Override
  public String toString() {
    return "Empty node";
  }
}
