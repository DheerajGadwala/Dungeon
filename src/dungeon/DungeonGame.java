package dungeon;

import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;

import general.Direction;
import general.MatrixPosition;
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

  private void validateMN(int m, int n) {
    if (m < 0 || n < 0) {
      throw new IllegalArgumentException("Invalid size of grid.");
    }
    else if (m + n - 2 <= 5) {
      throw new IllegalArgumentException("m+n-2 needs be greater than 5.");
    }
  }

  private DungeonGame(
      int m, int n, int percentage, boolean enableWrap,
      int interconnectivity, Randomizer randomizer
  ) throws IllegalArgumentException {
    validateMN(m, n);
    if (randomizer == null){
      throw new IllegalArgumentException("randomizer can not be null");
    }
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage");
    }
    this.randomizer = randomizer;
    this.m = m;
    this.n = n;
    this.percentage = percentage;
    generateValidDungeon(m, n, enableWrap, interconnectivity);
    generateTreasure(percentage);
  }

  /**
   * This is a constructor for Dungeon control class.
   * @param m dimension of the dungeon
   * @param n dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param interconnectivity interconnectivity of the dungeon
   * @throws IllegalArgumentException when given m and n lead to invalid dungeon generation
   *                                  or when interconnectivity is too high.
   */
  public DungeonGame(int m, int n, int percentage, boolean enableWrap, int interconnectivity)
      throws IllegalArgumentException {
    this(m, n, percentage, enableWrap, interconnectivity, new ActualRandomizer());
  }

  /**
   * This is a constructor for Dungeon control class with pseudo random generation of dungeon.
   * @param m dimension of the dungeon
   * @param n dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param interconnectivity interconnectivity of the dungeon
   * @param random numbers to be used in the generation of this dungeon.
   * @throws IllegalArgumentException when given m and n lead to invalid dungeon generation or
   *                                  when interconnectivity is too high or when random is null.
   */
  public DungeonGame(int m, int n, int percentage, boolean enableWrap, int interconnectivity, int ...random)
      throws IllegalArgumentException {
    this(m, n, percentage, enableWrap, interconnectivity, new PseudoRandomizer(random));
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

  private void generateValidDungeon(int m, int n, boolean enableWrap, int interconnectivity) {
    while (this.start == null || this.end == null) {
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
      if (start == null || end == null) {
        continue;
      }
      this.start = start;
      this.end = end;
    }
  }

  @Override
  public void createPlayer(String name)
      throws IllegalArgumentException, IllegalStateException {
    if (player != null) {
      throw new IllegalStateException("Dungeon already has a player.");
    }
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name can not be null or empty.");
    }
    player = new DungeonPlayer(name, start);
  }

  private Map<Treasure, Integer> generateRandomTreasure() {
    Map<Treasure, Integer> ret = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      ret.put(t, randomizer.getIntBetween(1, 3));
    }
    return ret;
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

  @Override
  public String displayMap()
      throws IllegalStateException {
    validatePlayer();
    StringBuilder ret = new StringBuilder();
    String topBorder = "[***]";
    String sideBorder = "[***]";
    ret.append(topBorder.repeat(n + 2));
    ret.append("\n");
    int playerI = player.getPosition().getI();
    int playerJ = player.getPosition().getJ();
    int startI = start.getPosition().getI();
    int startJ = start.getPosition().getJ();
    int endI = end.getPosition().getI();
    int endJ = end.getPosition().getJ();
    for (int i = 0; i < m; i++) {
      for (int k = 0; k < 3; k++) {
        ret.append(sideBorder);
        for (int j = 0; j < n; j++) {
          LocationNode n = dungeon.getLocation(new MatrixPosition(i, j));
          if (k == 0) {
            ret.append(n.hasEmptyNodeAt(NORTH) ? "     " : "  |  ");
          }
          else if (k == 1) {
            ret.append(
                n.hasEmptyNodeAt(WEST) ? "  " : "--")
                .append(
                      i == playerI && j == playerJ ? "P"
                    : i == endI && j == endJ ? "E"
                    : i == startI && j == startJ ? "S"
                    : n.isTunnel() ? "+"
                    : n.hasTreasure() ? "T"
                    : "O"
                ).append(n.hasEmptyNodeAt(EAST) ? "  " : "--");
          }
          else {
            ret.append(n.hasEmptyNodeAt(SOUTH) ? "     " : "  |  ");
          }
        }
        ret.append(sideBorder).append("\n");
      }
    }
    ret.append(topBorder.repeat(n + 2));
    ret.append("\n");
    return ret.toString();
  }

  @Override
  public String displayStaticMap() {
    return dungeon.toString();
  }

  @Override
  public List<Direction> getPossibleMoves()
      throws IllegalStateException {
    return player.getPossibleDirections();
  }

  @Override
  public String movePlayer(Direction direction)
      throws IllegalStateException {
    validatePlayer();
    if (isGameOver()) {
      return "Player has already reached the end, no more moves possible.";
    }
    try {
      player.movePlayer(direction);
      if (getPlayerPosition().equals(getEndPosition())) {
        gameOver = true;
        return "Player has reached the end location!";
      }
      return "Player moved.";
    }
    catch (IllegalStateException e) {
      return "Move not possible.";
    }
  }

  private LocationNode getPlayerLocation() {
    return dungeon.getLocation(player.getPosition());
  }

  private void validatePlayer()
      throws IllegalStateException {
    if (player == null) {
      throw new IllegalStateException("Player has not been created yet.");
    }
  }

  @Override
  public String cedeTreasure()
      throws IllegalStateException {
    LocationNode loc = getPlayerLocation();
    if (loc.hasTreasure()) {
      player.collectTreasure();
      return "Player has collected the treasure from this cave.";
    }
    else if (loc.isTunnel()) {
      return "Player is in a tunnel.";
    }
    else {
      return "No treasure found in this cave.";
    }
  }

  @Override
  public MatrixPosition getPlayerPosition()
      throws IllegalStateException {
    validatePlayer();
    return player.getPosition();
  }

  @Override
  public boolean playerLocationHasTreasure()
      throws IllegalStateException {
    return dungeon.getLocation(player.getPosition()).hasTreasure();
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
    return player.getDescription();
  }

  @Override
  public String getPlayerLocationDescription() throws IllegalStateException {
    validatePlayer();
    return getPlayerLocation().getDescription();
  }

  @Override
  public String getPlayerTreasureDescription() throws IllegalStateException {
    validatePlayer();
    return player.getTreasureDescription();
  }

  @Override
  public String gameStatus()
      throws IllegalStateException {
    if (player == null) {
      return "Game has not started yet, player is yet to be added.";
    }
    if (getPlayerPosition().equals(getStartPosition())) {
      return "Game has started";
    }
    else if (isGameOver()) {
      return "Game has ended!";
    }
    else {
      return "Player is still searching for the end location";
    }
  }

  @Override
  public int getTreasurePercentage() {
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
}
