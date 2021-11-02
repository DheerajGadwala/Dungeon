package dungeon;

import static general.Direction.NORTH;
import static general.Direction.EAST;
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
public class DungeonControlImpl implements DungeonControl {

  private final int m;
  private final int n;
  private final Randomizer randomizer;
  private LocationGraph dungeon;
  private LocationNode start;
  private LocationNode end;
  private Player player;

  private void validateMN(int m, int n) {
    if (m < 0 || n < 0) {
      throw new IllegalArgumentException("Invalid size of grid.");
    }
    else if (m + n - 2 <= 5) {
      throw new IllegalArgumentException("m+n-2 needs be greater than 5.");
    }
  }

  public DungeonControlImpl(int m, int n, boolean enableWrap, int interconnectivity)
      throws IllegalArgumentException {
    validateMN(m, n);
    this.randomizer = new ActualRandomizer();
    this.m = m;
    this.n = n;
    generateValidDungeon(m, n, enableWrap, interconnectivity);
  }

  public DungeonControlImpl(int m, int n, boolean enableWrap, int interconnectivity, int ...random)
      throws IllegalArgumentException {
    if (random == null) {
      throw new IllegalArgumentException("Random numbers can not be null");
    }
    validateMN(m, n);
    this.randomizer = new PseudoRandomizer(random);
    this.m = m;
    this.n = n;
    generateValidDungeon(m, n, enableWrap, interconnectivity);
  }

  private List<MatrixPosition> getAllPositions () {
    List<MatrixPosition> allPositions = new ArrayList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        allPositions.add(new MatrixPosition(i, j));
      }
    }
    return allPositions;
  }

  private void generateValidDungeon(int m, int n, boolean enableWrap, int interconnectivity) {
    while (this.start == null || this.end == null) {
      this.dungeon = new DungeonGraph(randomizer, m, n, enableWrap).getMst(interconnectivity);
      List<MatrixPosition> possibleStarts = getAllPositions();
      LocationNode start = null;
      LocationNode end = null;
      while (possibleStarts.size() > 0) {
        int x = randomizer.getIntBetween(0, possibleStarts.size()-1);
        start = dungeon.getLocation(possibleStarts.remove(x));
        List<LocationNode> possibleEndpoints = start.getDistantNodes(5);
        if (possibleEndpoints.size() == 0) {
          continue;
        }
        int y = randomizer.getIntBetween(0, possibleEndpoints.size()-1);
        end = possibleEndpoints.get(y);
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
  public void createPlayer(String name) throws IllegalArgumentException, IllegalStateException {
    if (player != null) {
      throw new IllegalStateException("Dungeon already has a player.");
    }
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name can not be null or empty.");
    }
    player = new DungeonPlayer(name, start);
    System.out.println(displayMap());
  }

  private Map<Treasure, Integer> generateRandomTreasure() {
    Map<Treasure, Integer> ret = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      ret.put(t, randomizer.getIntBetween(0, 3));
    }
    return ret;
  }

  @Override
  public void generateTreasure(int percentage) throws IllegalArgumentException {
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage");
    }
    List<LocationNode> allCaves = dungeon.getCaves();
    int toBeAddedIn = allCaves.size()*percentage/100;
    while (toBeAddedIn > 0) {
      int x = randomizer.getIntBetween(0, allCaves.size()-1);
      allCaves.remove(x).addTreasure(generateRandomTreasure());
      toBeAddedIn--;
    }
  }

  @Override
  public String displayMap() {
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
                    : "O"
                ).append(n.hasEmptyNodeAt(EAST) ? "  " : "--"
                );
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
  public List<Direction> getPossibleMoves() throws IllegalStateException {
    return player.getPossibleDirections();
  }

  @Override
  public String movePlayer(Direction direction) throws IllegalArgumentException, IllegalStateException {
    try {
      player.movePlayer(direction);
    }
    catch (IllegalStateException e) {
      return displayMap()+" \nMove not possible.";
    }
    return displayMap()+" \nPlayer moved.";
  }

  @Override
  public String cedeTreasure() throws IllegalStateException {
    return null;
  }
}
