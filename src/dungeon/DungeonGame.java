package dungeon;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Item.CROOKED_ARROW;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.MatrixPosition;
import dungeongeneral.Odour;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;
import randomizer.ActualRandomizer;
import randomizer.PseudoRandomizer;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the dungeon control implementation.
 * A player is created in this on request.
 * This creates a random dungeon of the given dimensions on construction.
 * This decides the start and end locations.
 * The player can be moved through this on request.
 */
public class DungeonGame implements Game {

  private final int row;
  private final int column;
  private final Randomizer randomizer;
  private LocationGraph dungeon;
  private LocationNode start;
  private LocationNode end;
  private final Player player;
  private boolean gameOver;
  private static final int MIN_SE_DISTANCE = 5;

  private DungeonGame(
      int row, int column, int percentage, int numberOfMonsters,
      boolean enableWrap, int interconnectivity, Randomizer randomizer
  ) throws IllegalArgumentException {
    validateMN(row, column);
    if (randomizer == null) {
      throw new IllegalArgumentException("randomizer can not be null");
    }
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage");
    }
    if (numberOfMonsters < 1) {
      throw new IllegalArgumentException("Number of monsters should be at least one.");
    }
    this.randomizer = randomizer;
    this.row = row;
    this.column = column;
    generateValidDungeon(row, column, enableWrap, interconnectivity, numberOfMonsters);
    generateTreasure(percentage);
    generateItems(percentage);
    generateMonsters(numberOfMonsters);
    this.player = new DungeonPlayer(this.start);
  }

  private void validateMN(int row, int column) {
    if (row < 0 || column < 0) {
      throw new IllegalArgumentException("Invalid size of grid.");
    }
    else if (row + column - 2 <= 5) {
      throw new IllegalArgumentException("row+column-2 needs be greater than 5.");
    }
  }

  private void generateTreasure(int percentage)
      throws IllegalArgumentException {
    List<LocationNode> allCaves = dungeon.getCaves();
    double toBeAddedIn = allCaves.size() * percentage / 100.0;
    while (toBeAddedIn - 1 >= 0) {
      int x = randomizer.getIntBetween(0, allCaves.size() - 1);
      allCaves.remove(x).addTreasure(generateRandomTreasure());
      toBeAddedIn--;
    }
  }

  private void generateMonsters(int numberOfMonsters) {
    List<LocationNode> allCaves = dungeon.getCaves();
    end.setMonster(new Otyugh(randomizer));
    numberOfMonsters--;
    allCaves.remove(start);
    allCaves.remove(end);
    while (numberOfMonsters > 0) {
      int x = randomizer.getIntBetween(0, allCaves.size() - 1);
      allCaves.remove(x).setMonster(new Otyugh(randomizer));
      numberOfMonsters--;
    }
  }

  private void generateItems(int percentage) {
    List<MatrixPosition> allPositions = getAllPositions();
    double toBeAddedIn = allPositions.size() * percentage / 100.0;
    while (toBeAddedIn - 1 >= 0) {
      int x = randomizer.getIntBetween(0, allPositions.size() - 1);
      int val = randomizer.getIntBetween(1, 5);
      dungeon.getLocation(allPositions.remove(x)).setItemCount(CROOKED_ARROW, val);
      toBeAddedIn--;
    }
  }

  /**
   * This is a constructor for Dungeon control class.
   * @param row dimension of the dungeon
   * @param column dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param interconnectivity interconnectivity of the dungeon
   * @throws IllegalArgumentException when given row and column lead to invalid dungeon generation
   *                                  or when interconnectivity is too high or when number of
   *                                  monsters is not at least one.
   */
  public DungeonGame(int row, int column, int percentage, int numberOfMonsters,
                     boolean enableWrap, int interconnectivity
  ) throws IllegalArgumentException {
    this(row, column, percentage, numberOfMonsters, enableWrap,
        interconnectivity, new ActualRandomizer()
    );
  }

  /**
   * This is a constructor for Dungeon control class with pseudo random generation of dungeon.
   * @param row dimension of the dungeon
   * @param column dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param interconnectivity interconnectivity of the dungeon
   * @param random numbers to be used in the generation of this dungeon.
   * @throws IllegalArgumentException when given row and column lead to invalid dungeon generation
   *                                  or when interconnectivity is too high or when random is null
   *                                  or when number of monster is not at least one.
   */
  public DungeonGame(int row, int column, int percentage, int numberOfMonsters,
                     boolean enableWrap, int interconnectivity, int ...random
  ) throws IllegalArgumentException {
    this(row, column, percentage, numberOfMonsters, enableWrap,
        interconnectivity, new PseudoRandomizer(random)
    );
  }

  @Override
  public ShotResult shootArrow(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
    validateGameOver();
    return player.shoot(direction, distance);
  }

  @Override
  public boolean hasPlayerWon() {
    return isGameOver() && player.isAlive();
  }

  private void generateValidDungeon(
      int row, int column, boolean enableWrap,
      int interconnectivity, int numberOfMonsters) {
    int monsterFailureCount = 0;
    while (this.start == null || this.end == null) {
      System.out.println("here");
      if (monsterFailureCount == 100) {
        throw new IllegalArgumentException("Number of monsters is too high!");
      }
      this.dungeon = new DungeonGraph(
          randomizer, row, column, enableWrap
      ).getMst(interconnectivity);
      List<MatrixPosition> possibleStarts = getAllPositions();
      LocationNode start = null;
      LocationNode end = null;
      while (possibleStarts.size() > 0) {
        int x = randomizer.getIntBetween(0, possibleStarts.size() - 1);
        start = dungeon.getLocation(possibleStarts.remove(x));
        if (start.isTunnel()) {
          start = null;
          continue;
        }
        List<LocationNode> possibleEndpoints = start.getRequiredNodes(
            (d) -> d > MIN_SE_DISTANCE,
            (node) -> true
        );
        if (possibleEndpoints.size() == 0) {
          continue;
        }
        int y = randomizer.getIntBetween(0, possibleEndpoints.size() - 1);
        end = possibleEndpoints.get(y);
        if (end.isTunnel()) {
          end = null;
          continue;
        }
        break;
      }
      if (start == null || end == null || dungeon.getCaveCount() - 2 < numberOfMonsters) {
        if (start != null && end != null) {
          monsterFailureCount++;
        }
        continue;
      }
      this.start = start;
      this.end = end;
    }
  }
  
  private Map<Treasure, Integer> generateRandomTreasure() {
    Map<Treasure, Integer> ret = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      ret.put(t, randomizer.getIntBetween(1, 3));
    }
    return ret;
  }

  @Override
  public void movePlayer(Direction direction)
      throws IllegalStateException, IllegalArgumentException {
    validateGameOver();
    player.movePlayer(direction);
    if (getPlayerLocation().hasAliveMonster()) {
      try {
        getPlayerLocation().getMonster().attack(player);
      }
      catch (IllegalStateException ignored) {
      }
      if (!player.isAlive()) {
        gameOver = true;
      }
    }
    else if (getPlayerPosition().equals(getEndPosition())) {
      gameOver = true;
    }
  }

  @Override
  public Odour getSmellAtPlayerLocation() {
    return player.smell();
  }

  private LocationNode getPlayerLocation() {
    return dungeon.getLocation(player.getPosition());
  }

  private LocationNode getLocation(MatrixPosition position) {
    return dungeon.getLocation(position);
  }
  
  private void validateGameOver()
      throws IllegalStateException {
    if (isGameOver()) {
      throw new IllegalStateException("Action not possible after game is over!");
    }
  }
  
  @Override
  public void cedeTreasure(Treasure treasure)
      throws IllegalStateException, IllegalArgumentException {
    validateGameOver();
    player.collectTreasure(treasure);
  }

  @Override
  public void cedeItem(Item item)
      throws IllegalStateException, IllegalArgumentException {
    validateGameOver();
    player.pickItem(item);
  }

  /**
   * Get current position of player.
   * @return current matrix position of player.
   */
  private MatrixPosition getPlayerPosition() {
    return player.getPosition();
  }

  @Override
  public boolean playerHasArrow() {
    return player.hasArrow();
  }

  /**
   * Get end position.
   * @return matrix position of end location.
   */
  MatrixPosition getEndPosition() {
    return end.getPosition();
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public String getPlayerDescription() {
    return player.toString();
  }

  @Override
  public String getLocationDescription() {
    return player.getLocationDescription();
  }

  /**
   * Returns positions of all location nodes.
   * @return positions of all locations.
   */
  List<MatrixPosition> getAllPositions() {
    List<MatrixPosition> allPositions = new ArrayList<>();
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        allPositions.add(new MatrixPosition(i, j));
      }
    }
    return allPositions;
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    int playerI = player.getPosition().getRow();
    int playerJ = player.getPosition().getColumn();
    int startI = start.getPosition().getRow();
    int startJ = start.getPosition().getColumn();
    int endI = end.getPosition().getRow();
    int endJ = end.getPosition().getColumn();
    for (int i = 0; i < row; i++) {
      for (int k = 0; k < 3; k++) {
        for (int j = 0; j < column; j++) {
          LocationNode column = getLocation(new MatrixPosition(i, j));
          if (k == 0) {
            ret.append(column.hasEmptyNodeAt(NORTH) ? "          " : "    ||    ");
          }
          else if (k == 1) {
            ret.append(
                column.hasEmptyNodeAt(WEST) ? " " : "-")
                .append(i == endI && j == endJ ? " E" : i == startI && j == startJ ? " S" : " *")
                .append(column.hasAliveMonster() ? "M" : "*")
                .append(i == playerI && j == playerJ ? "P" : "*")
                .append(column.hasItem(CROOKED_ARROW) ? "I" : "*")
                .append(column.isTunnel() ? "t" : "c")
                .append(column.hasTreasure() ? "T " : "* ")
                .append(column.hasEmptyNodeAt(EAST) ? " " : "-");
          }
          else {
            ret.append(column.hasEmptyNodeAt(SOUTH) ? "          " : "    ||    ");
          }
        }
        ret.append("\n");
      }
    }
    return ret.toString();
  }
}
