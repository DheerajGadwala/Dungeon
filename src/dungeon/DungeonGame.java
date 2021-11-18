package dungeon;

import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;
import static general.Item.CROOKED_ARROW;

import general.Direction;
import general.Item;
import general.MatrixPosition;
import general.Odour;
import general.ShotResult;
import general.Treasure;
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

  private final int m;
  private final int n;
  private final int percentage;
  private final Randomizer randomizer;
  private LocationGraph dungeon;
  private LocationNode start;
  private LocationNode end;
  private Player player;
  private boolean gameOver;
  private static final int MIN_SE_DISTANCE = 5;

  private DungeonGame(
      int m, int n, int percentage, int numberOfMonsters, boolean enableWrap,
      int interconnectivity, Randomizer randomizer
  ) throws IllegalArgumentException {
    validateMN(m, n);
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
    this.m = m;
    this.n = n;
    this.percentage = percentage;
    generateValidDungeon(m, n, enableWrap, interconnectivity, numberOfMonsters);
    generateTreasure(percentage);
    generateItems(percentage);
    generateMonsters(numberOfMonsters);
  }

  private void validateMN(int m, int n) {
    if (m < 0 || n < 0) {
      throw new IllegalArgumentException("Invalid size of grid.");
    }
    else if (m + n - 2 <= 5) {
      throw new IllegalArgumentException("m+n-2 needs be greater than 5.");
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
   * @param m dimension of the dungeon
   * @param n dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param interconnectivity interconnectivity of the dungeon
   * @throws IllegalArgumentException when given m and n lead to invalid dungeon generation
   *                                  or when interconnectivity is too high or when number of
   *                                  monsters is not at least one.
   */
  public DungeonGame(int m, int n, int percentage, int numberOfMonsters,
                     boolean enableWrap, int interconnectivity
  ) throws IllegalArgumentException {
    this(m, n, percentage, numberOfMonsters, enableWrap,
        interconnectivity, new ActualRandomizer()
    );
  }

  /**
   * This is a constructor for Dungeon control class with pseudo random generation of dungeon.
   * @param m dimension of the dungeon
   * @param n dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param interconnectivity interconnectivity of the dungeon
   * @param random numbers to be used in the generation of this dungeon.
   * @throws IllegalArgumentException when given m and n lead to invalid dungeon generation or
   *                                  when interconnectivity is too high or when random is null
   *                                  or when number of monster is not at least one.
   */
  public DungeonGame(int m, int n, int percentage, int numberOfMonsters,
                     boolean enableWrap, int interconnectivity, int ...random
  ) throws IllegalArgumentException {
    this(m, n, percentage, numberOfMonsters, enableWrap,
        interconnectivity, new PseudoRandomizer(random)
    );
  }

  private void validatePosition(MatrixPosition position)
      throws IllegalArgumentException {
    if (
        position.getI() < 0
        || position.getI() >= m
        || position.getJ() < 0
        || position.getJ() >= n
    ) {
      throw new IllegalArgumentException("Position not in this dungeon.");
    }
  }

  @Override
  public ShotResult shootArrow(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
    validatePlayer();
    validateGameOver();
    return player.shoot(direction, distance);
  }

  @Override
  public boolean hasPlayerWon() {
    return isGameOver() && player.isAlive();
  }

  private void generateValidDungeon(int m, int n, boolean enableWrap, int interconnectivity, int numberOfMonsters) {
    int monsterFailureCount = 0;
    while (this.start == null || this.end == null) {
      //System.out.println("Here");
      if (monsterFailureCount == 100) {
        throw new IllegalArgumentException("Number of monsters is too high!");
      }
      this.dungeon = new DungeonGraph(randomizer, m, n, enableWrap).getMst(interconnectivity);
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
      if (start == null || end == null || dungeon.getCaveCount()-2 < numberOfMonsters) {
        if (start != null && end != null) {
          monsterFailureCount++;
        }
        continue;
      }
      this.start = start;
      this.end = end;
    }
  }

  @Override
  public void createPlayer()
      throws IllegalArgumentException, IllegalStateException {
    if (isPlayerCreated()) {
      throw new IllegalStateException("Dungeon already has a player.");
    }
    player = new DungeonPlayer(start);
  }

  @Override
  public boolean isPlayerCreated() {
    return player != null;
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
    validatePlayer();
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
  public Odour getSmellAtPlayerLocation() throws IllegalStateException {
    validatePlayer();
    return player.smell();
  }

  private LocationNode getPlayerLocation() {
    return dungeon.getLocation(player.getPosition());
  }

  LocationNode getLocation(MatrixPosition position) {
    return dungeon.getLocation(position);
  }

  private void validatePlayer()
      throws IllegalStateException {
    if (player == null) {
      throw new IllegalStateException("Player has not been created yet.");
    }
  }
  private void validateGameOver()
    throws IllegalStateException {
    if (isGameOver()) {
      throw new IllegalStateException("Action not possible after game is over!");
    }
  }

  Player getPlayer() {
    return player;
  }

  int getN() {
    return n;
  }

  int getM() {
    return m;
  }

  LocationNode getStartNode() {
    return start;
  }

  LocationNode getEndNode() {
    return end;
  }

  @Override
  public void cedeTreasure(Treasure t)
      throws IllegalStateException, IllegalArgumentException {
    validatePlayer();
    validateGameOver();
    player.collectTreasure(t);
  }

  @Override
  public void cedeItem(Item i)
      throws IllegalStateException, IllegalArgumentException {
    validatePlayer();
    validateGameOver();
    player.pickItem(i);
  }

  @Override
  public MatrixPosition getPlayerPosition()
      throws IllegalStateException {
    validatePlayer();
    return player.getPosition();
  }

  @Override
  public boolean playerHasArrow() {
    return player.hasArrow();
  }

  @Override
  public MatrixPosition getStartPosition() {
    return start.getPosition();
  }

  @Override
  public MatrixPosition getEndPosition() {
    return end.getPosition();
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public String getPlayerDescription() throws IllegalStateException {
    validatePlayer();
    return player.toString();
  }

  @Override
  public String getPlayerLocationDescription() throws IllegalStateException {
    validatePlayer();
    return player.getLocationDescription();
  }

  @Override
  public int getPercentage() {
    return percentage;
  }

  @Override
  public List<List<MatrixPosition>> getAllConnections() {
    List<Connection> connections = dungeon.getAllConnections();
    List<List<MatrixPosition>> ret = new ArrayList<>();
    for (Connection connection: connections) {
      List<MatrixPosition> temp = new ArrayList<>();
      temp.add(connection.getMatrixPositionA());
      temp.add(connection.getMatrixPositionB());
      ret.add(temp);
    }
    return ret;
  }

  @Override
  public List<MatrixPosition> getAllPositions() {
    List<MatrixPosition> allPositions = new ArrayList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        allPositions.add(new MatrixPosition(i, j));
      }
    }
    return allPositions;
  }

  @Override
  public boolean caveAtPosition(MatrixPosition position)
      throws IllegalArgumentException {
    validatePosition(position);
    return dungeon.getLocation(position).isCave();
  }

  @Override
  public boolean tunnelAtPosition(MatrixPosition position)
      throws IllegalArgumentException {
    validatePosition(position);
    return dungeon.getLocation(position).isTunnel();
  }

  @Override
  public boolean treasureAtPosition(MatrixPosition position)
      throws IllegalArgumentException {
    validatePosition(position);
    return dungeon.getLocation(position).hasTreasure();
  }

  @Override
  public String toString() {
    validatePlayer();
    StringBuilder ret = new StringBuilder();
    String topBorder = "[************]";
    String sideBorder = "[************]";
    ret.append(topBorder.repeat(getN() + 2));
    ret.append("\n");
    int playerI = getPlayer().getPosition().getI();
    int playerJ = getPlayer().getPosition().getJ();
    int startI = getStartNode().getPosition().getI();
    int startJ = getStartNode().getPosition().getJ();
    int endI = getEndNode().getPosition().getI();
    int endJ = getEndNode().getPosition().getJ();
    for (int i = 0; i < getM(); i++) {
      for (int k = 0; k < 5; k++) {
        ret.append(sideBorder);
        for (int j = 0; j < getN(); j++) {
          LocationNode n = getLocation(new MatrixPosition(i, j));
          if (k == 0 || k == 1) {
            ret.append(n.hasEmptyNodeAt(NORTH) ? "              " : "      ||      ");
          }
          else if (k == 2) {
            ret.append(
                n.hasEmptyNodeAt(WEST) ? "  " : "--")
                .append(i == endI && j == endJ ? " E" : " ~")
                .append(i == startI && j == startJ ? "S" : "~")
                .append(n.hasAliveMonster() ? "M" : "~")
                .append(i == playerI && j == playerJ ? "P" : "~")
                .append(n.hasItem(CROOKED_ARROW) ? "I" : "~")
                .append(n.isTunnel() ? "+" : "~")
                .append(n.hasTreasure() ? "T" : "~")
                .append(n.isCave() ? "O " : "~ ")
                .append(n.hasEmptyNodeAt(EAST) ? "  " : "--");
          }
          else {
            ret.append(n.hasEmptyNodeAt(SOUTH) ? "              " : "      ||      ");
          }
        }
        ret.append(sideBorder).append("\n");
      }
    }
    ret.append(topBorder.repeat(getN() + 2));
    ret.append("\n");
    return ret.toString();
  }
}
