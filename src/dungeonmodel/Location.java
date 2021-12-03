package dungeonmodel;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Odour.LESS_PUNGENT;
import static dungeongeneral.Odour.MORE_PUNGENT;
import static dungeongeneral.Odour.ODOURLESS;

import dungeongeneral.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

class Location implements LocationNode {

  private final HashMap<Direction, LocationNode> neighbours;
  private final Coordinate position;
  private Map<Treasure, Integer> treasures;
  private Entity monster;
  private boolean hasPit;
  private final Map<Item, Integer> items;
  private boolean isDiscovered;

  public Location(
      Coordinate position,
      HashMap<Direction, LocationNode> neighbours
  ) {
    this.position = position;
    this.neighbours = neighbours;
    this.items = new HashMap<>();
    initializeItems();
  }

  public Location(
      Coordinate position
  ) {
    this.position = position;
    this.neighbours = new HashMap<>();
    neighbours.put(NORTH, EmptyLocation.getInstance());
    neighbours.put(EAST, EmptyLocation.getInstance());
    neighbours.put(SOUTH, EmptyLocation.getInstance());
    neighbours.put(WEST, EmptyLocation.getInstance());
    this.treasures = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      treasures.put(t, 0);
    }
    this.items = new HashMap<>();
    initializeItems();
  }

  private void initializeItems() {
    for (Item i: Item.values()) {
      items.put(i, 0);
    }
  }

  private int getNeighbourCount() {
    int count = 0;
    if (!neighbours.get(NORTH).isEmptyNode()) {
      count++;
    }
    if (!neighbours.get(EAST).isEmptyNode()) {
      count++;
    }
    if (!neighbours.get(SOUTH).isEmptyNode()) {
      count++;
    }
    if (!neighbours.get(WEST).isEmptyNode()) {
      count++;
    }
    return count;
  }

  private void validateDiscovery() {
    if (! isDiscovered) {
      throw new IllegalStateException (
              "Can not get information about location before discovering it."
      );
    }
  }

  @Override
  public void addTreasure(Map<Treasure, Integer> treasure)
      throws IllegalArgumentException, IllegalStateException {
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure can not be null.");
    }
    else if (isTunnel()) {
      throw new IllegalStateException("Can not add treasure to a tunnel");
    }
    for (Treasure t: Treasure.values()) {
      this.treasures.replace(t, treasures.get(t) + treasure.get(t));
    }
  }

  @Override
  public boolean hasTreasure() {
    int sum = 0;
    for (Treasure t: Treasure.values()) {
      sum += treasures.get(t);
    }
    return sum > 0;
  }

  @Override
  public boolean isTunnel() {
    return getNeighbourCount() == 2;
  }

  @Override
  public boolean isCave() {
    return getNeighbourCount() != 2;
  }

  @Override
  public boolean isEmptyNode() {
    return false;
  }

  @Override
  public LocationNode getLocationAt(Direction direction) {
    return neighbours.get(direction);
  }

  @Override
  public boolean hasEmptyNodeAt(Direction direction) {
    return neighbours.get(direction).isEmptyNode();
  }

  @Override
  public void setNeighbour(Direction direction, LocationNode location)
      throws IllegalArgumentException {
    if (direction == null || location == null) {
      throw new IllegalArgumentException("direction/ location can not be null.");
    }
    neighbours.put(direction, location);
  }

  @Override
  public Direction getNeighbourDirection(LocationNode neighbour)
      throws IllegalArgumentException {
    for (Direction direction: Direction.values()) {
      if (neighbours.get(direction).equals(neighbour)) {
        return direction;
      }
    }
    throw new IllegalArgumentException("Given node is not a neighbour");
  }

  @Override
  public void decreaseTreasureCount(Treasure treasure) throws IllegalStateException {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure can not be null");
    }
    if (isTunnel()) {
      throw new IllegalStateException("This is a tunnel. Tunnels do not have treasure.");
    }
    if (!hasTreasure()) {
      throw new IllegalStateException("This location has no treasure.");
    }
    else if (treasures.get(treasure) == 0) {
      throw new IllegalArgumentException("No treasure of this type.");
    }
    treasures.replace(treasure, treasures.get(treasure) - 1);
  }

  @Override
  public List<LocationNode> getRequiredNodesHelper(
      List<LocationNode> visited,
      List<LocationNode> queue,
      List<Integer> queueD,
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  ) {
    if (queue.size() == 0) {
      return new ArrayList<>();
    }
    LocationNode current = queue.remove(0);
    int d = queueD.remove(0);
    if (visited.contains(current)) {
      return new ArrayList<>();
    }
    visited.add(current);
    List<LocationNode> ret = new ArrayList<>();
    if (distanceRequirement.test(d) && nodeRequirement.test(current)) {
      ret.add(current);
    }
    for (Direction direction: Direction.values()) {
      if (
          !current.getLocationAt(direction).isEmptyNode()
          && !visited.contains(current.getLocationAt(direction))
      ) {
        queue.add(current.getLocationAt(direction));
        queueD.add(d + 1);
      }
    }
    ret.addAll(
        getRequiredNodesHelper(
            visited, queue, queueD,
            distanceRequirement, nodeRequirement
        )
    );
    return ret;
  }

  @Override
  public List<LocationNode> getRequiredNodes(
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  ) {
    List<LocationNode> visited = new ArrayList<>();
    visited.add(this);
    List<Integer> queue = new ArrayList<>();
    queue.add(0);
    return getRequiredNodesHelper(
        new ArrayList<>(), visited, queue,
        distanceRequirement, nodeRequirement
    );
  }

  @Override
  public Coordinate getCoordinates() {
    return position;
  }

  @Override
  public List<Direction> getPossibleRoutes() {
    validateDiscovery();
    return getPossibleRoutesHelper();
  }

  private List<Direction> getPossibleRoutesHelper() {
    List<Direction> possibilities = new ArrayList<>();
    for (Direction d: Direction.values()) {
      if (!this.hasEmptyNodeAt(d)) {
        possibilities.add(d);
      }
    }
    return possibilities;
  }

  @Override
  public boolean hasNeighbour(LocationNode that) {
    return that.equals(this.getLocationAt(NORTH))
        || that.equals(this.getLocationAt(EAST))
        || that.equals(this.getLocationAt(SOUTH))
        || that.equals(this.getLocationAt(WEST));
  }

  @Override
  public void setMonster(Entity monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("Monster can not be null");
    }
    else if (!this.isCave()) {
      throw new IllegalArgumentException("Can add monsters to caves only.");
    }
    else if (this.hasAliveMonsterHelper()) {
      throw new IllegalArgumentException("This cave already has a monster.");
    }
    this.monster = monster;
  }

  @Override
  public void createPit() {
    this.hasPit = true;
  }

  @Override
  public boolean hasMonster() {
    validateDiscovery();
    return hasMonsterHelper();
  }

  @Override
  public  boolean hasMonsterHelper() {
    return monster != null;
  }

  @Override
  public boolean hasAliveMonster() {
    validateDiscovery();
    return hasAliveMonsterHelper();
  }

  @Override
  public boolean hasAliveMonsterHelper() {
    if (monster == null) {
      return false;
    }
    else {
      return monster.isAlive();
    }
  }

  @Override
  public void setItemCount(Item item, int n) throws IllegalArgumentException {
    if (n <= 0) {
      throw new IllegalArgumentException("Can not set number of items to a non positive number.");
    }
    items.put(item, n);
  }

  @Override
  public void decreaseItemCount(Item item) throws IllegalArgumentException, IllegalStateException {
    if (item == null) {
      throw new IllegalArgumentException("item can not be null.");
    }
    if (!hasItems()) {
      throw new IllegalStateException("This location has no items.");
    }
    if (items.get(item) == 0) {
      throw new IllegalArgumentException(
          "This location does not have any "
          + item.getPlural() + "."
      );
    }
    items.replace(item, items.get(item) - 1);
  }

  @Override
  public void discover() {
    this.isDiscovered = true;
  }

  @Override
  public boolean hasItem(Item item) {
    return items.get(item) > 0;
  }

  @Override
  public Odour getOdour() {
    validateDiscovery();
    return getOdourHelper();
  }

  private Odour getOdourHelper() {
    if (this.hasAliveMonsterHelper()) {
      return MORE_PUNGENT;
    }
    int n = getRequiredNodes(
            (distance) -> distance == 1,
            LocationNode::hasAliveMonsterHelper
    ).size();
    if (n > 0) {
      return MORE_PUNGENT;
    }
    n = getRequiredNodes(
            (distance) -> distance == 2,
            LocationNode::hasAliveMonsterHelper
    ).size();
    if (n > 1) {
      return MORE_PUNGENT;
    }
    else if (n == 1) {
      return LESS_PUNGENT;
    }
    else {
      return ODOURLESS;
    }
  }

  @Override
  public Entity getMonster() throws IllegalStateException {
    if (monster == null) {
      throw new IllegalStateException("No monster at this location.");
    }
    return monster;
  }

  @Override
  public boolean hasPit() {
    return this.hasPit;
  }

  @Override
  public boolean isDiscovered() {
    return isDiscovered;
  }

  @Override
  public Entity getMonsterAtEnd(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
    if (distance < 0 || direction == null) {
      throw new IllegalArgumentException("Invalid Distance/ Direction.");
    }
    if (distance == 0) {
      return this.monster;
    }
    else if (isTunnel()) {
      for (Direction d: Direction.values()) {
        LocationNode neighbour = neighbours.get(d);
        if (d == direction.getOpposite()) {
          continue;
        }
        if (neighbour.isCave()) {
          return neighbour.getMonsterAtEnd(d, distance - 1);
        }
        else if (neighbour.isTunnel()) {
          return neighbour.getMonsterAtEnd(d, distance);
        }
      }
      throw new IllegalStateException("Invalid tunnel");
    }
    else {
      LocationNode neighbour = neighbours.get(direction);
      if (neighbour.isEmptyNode()) {
        return null;
      }
      else if (neighbour.isCave()) {
        return neighbour.getMonsterAtEnd(direction, distance - 1);
      }
      else {
        return neighbour.getMonsterAtEnd(direction, distance);
      }
    }
  }

  @Override
  public ReadOnlyLocation getDesc() {
    return this;
  }

  private Map<Item, Integer> copyItems(Map<Item, Integer> items) {
    Map<Item, Integer> copy = new TreeMap<>();
    for (Item item: items.keySet()) {
      copy.put(item, items.get(item));
    }
    return copy;
  }

  private Map<Treasure, Integer> copyTreasures(Map<Treasure, Integer> treasures) {
    Map<Treasure, Integer> copy = new TreeMap<>();
    for (Treasure treasure: treasures.keySet()) {
      copy.put(treasure, treasures.get(treasure));
    }
    return copy;
  }

  @Override
  public boolean hasItems() {
    validateDiscovery();
    return hasItemsHelper();
  }

  private boolean hasItemsHelper() {
    int sum = 0;
    for (Item i: Item.values()) {
      sum += items.get(i);
    }
    return sum > 0;
  }

  @Override
  public Map<Treasure, Integer> getTreasure() {
    validateDiscovery();
    return getTreasureHelper();
  }

  private Map<Treasure, Integer> getTreasureHelper() {
    return copyTreasures(treasures);
  }

  @Override
  public Map<Item, Integer> getItems() {
    validateDiscovery();
    return getItemsHelper();
  }

  private Map<Item, Integer> getItemsHelper() {
    return copyItems(items);
  }

  @Override
  public boolean hasNoMonster() {
    validateDiscovery();
    return hasNoMonsterHelper();
  }

  @Override
  public  boolean hasNoMonsterHelper() {
    return !hasMonsterHelper();
  }

  @Override
  public boolean hasDeadMonster() {
    validateDiscovery();
    return hasDeadMonsterHelper();
  }

  @Override
  public  boolean hasDeadMonsterHelper() {
    return hasMonsterHelper() && monster.getHealth() == 0;
  }

  @Override
  public boolean hasInjuredMonster() {
    validateDiscovery();
    return hasInjuredMonsterHelper();
  }

  @Override
  public  boolean hasInjuredMonsterHelper() {
    return hasMonsterHelper() && monster.getHealth() == 1;
  }

  @Override
  public boolean hasHealthyMonster() {
    validateDiscovery();
    return hasHealthyMonsterHelper();
  }

  @Override
  public  boolean hasHealthyMonsterHelper() {
    return hasMonsterHelper() && monster.getHealth() == 2;
  }

  @Override
  public String toString() {
    validateDiscovery();
    String type = isCave() ? "cave" : "tunnel";
    StringBuilder stb = new StringBuilder("This is a " + type + "\n");
    stb.append("Coordinates: ").append(position.toString()).append("\n");
    stb.append("Possible routes: ");
    for (Direction d: getPossibleRoutesHelper()) {
      stb.append(d.toString()).append(" ");
    }
    stb.append("\n");
    if (hasTreasure()) {
      stb.append("There's some treasure in this cave: ");
      stb.append(new TreasureList(treasures).toString()).append("\n");
    }
    if (hasItems()) {
      stb.append("There are some items in this cave: ");
      stb.append(new ItemList(items).toString()).append("\n");
    }
    if (hasMonsterHelper()) {
      if (hasDeadMonster()) {
        stb.append("There is a dead monster here.\n");
      }
      else if (hasInjuredMonster()) {
        stb.append("There is an injured monster here.\n");
      }
      else {
        stb.append("There is an alive monster here.\n");
      }
    }
    if (getOdour() != ODOURLESS) {
      stb.append(getOdour().getImplication()).append("\n");
    }
    if (hasPit()) {
      stb.append("This location has a pit!");
    }
    return stb.toString();
  }

}
