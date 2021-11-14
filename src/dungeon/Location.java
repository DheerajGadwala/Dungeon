package dungeon;

import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;
import static general.Treasure.DIAMOND;
import static general.Treasure.RUBY;
import static general.Treasure.SAPPHIRE;

import general.Direction;
import general.MatrixPosition;
import general.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

class Location implements LocationNode {

  private final HashMap<Direction, LocationNode> neighbours;
  private final MatrixPosition position;
  private HashMap<Treasure, Integer> treasures;
  private Monster monster;

  public Location(
      MatrixPosition position,
      HashMap<Direction, LocationNode> neighbours
  ) {
    this.position = position;
    this.neighbours = neighbours;
  }

  public Location(
      MatrixPosition position
  ) {
    this.position = position;
    this.neighbours = new HashMap<>();
    neighbours.put(NORTH, EmptyLocation.getInstance());
    neighbours.put(EAST, EmptyLocation.getInstance());
    neighbours.put(SOUTH, EmptyLocation.getInstance());
    neighbours.put(WEST, EmptyLocation.getInstance());
    this.treasures = new HashMap<>();
    treasures.put(DIAMOND, 0);
    treasures.put(SAPPHIRE, 0);
    treasures.put(RUBY, 0);
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

  @Override
  public String getType() {
    return isCave() ? "Cave" : "Tunnel";
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
  public String getDescription() {
    StringBuilder ret = new StringBuilder(String.format(
        "\n%s:\n Coordinates -> %s Neighbours {N, E, S, W}: -> {%s, %s, %s, %s}\n",
        getType(),
        position.toString(),
        neighbours.get(NORTH).getType(),
        neighbours.get(EAST).getType(),
        neighbours.get(SOUTH).getType(),
        neighbours.get(WEST).getType()
    ));
    if (this.isCave()) {
      ret.append(" Treasure at this location-> ");
      for (Treasure t: Treasure.values()) {
        ret.append(t.toString()).append(": ").append(treasures.get(t)).append(" ");
      }
      ret.append('\n');
    }
    return ret.toString();
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
  public boolean hasTreasure() {
    int sum = 0;
    for (Treasure t: treasures.keySet()) {
      sum += treasures.get(t);
    }
    return sum != 0;
  }

  @Override
  public void setMonster(Monster monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("Monster can not be null");
    }
    else if (!this.isCave()) {
      throw new IllegalArgumentException("Can add monsters to caves only.");
    }
    else if (this.hasMonster()) {
      throw new IllegalArgumentException("This cave already has a monster.");
    }
    this.monster = monster;
  }

  @Override
  public boolean hasMonster() {
    return monster != null;
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
  public HashMap<Treasure, Integer> removeTreasure() throws IllegalStateException {
    if (isTunnel()) {
      throw new IllegalStateException("This is a tunnel. Tunnels do not have treasure.");
    }
    HashMap<Treasure, Integer> temp = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      temp.put(t, treasures.get(t));
      treasures.put(t, 0);
    }
    return temp;
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
  public MatrixPosition getPosition() {
    return position;
  }

  @Override
  public boolean hasNeighbour(LocationNode that) {
    return that.equals(this.getLocationAt(NORTH))
        || that.equals(this.getLocationAt(EAST))
        || that.equals(this.getLocationAt(SOUTH))
        || that.equals(this.getLocationAt(WEST));
  }

  @Override
  public String toString() {
    return getDescription();
  }
}
