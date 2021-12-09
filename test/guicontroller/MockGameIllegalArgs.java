package guicontroller;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeongeneral.Sound;
import dungeongeneral.Treasure;
import dungeonmodel.GameWithObstacles;

import java.io.IOException;
import java.util.List;

/**
 * This mock model throws illegal arguments exception when any of the
 * following six methods are called: move, moveToLocation, shoot, attack,
 * cedeTreasure, and cedeItem.
 */
class MockGameIllegalArgs implements GameWithObstacles {

  private final Appendable modelLog;
  private final String uniqueCode;

  MockGameIllegalArgs(Appendable modelLog, String uniqueCode) {
    if (modelLog == null) {
      throw new IllegalArgumentException("model log can not be null");
    }
    if (uniqueCode == null || uniqueCode.isEmpty()) {
      throw new IllegalArgumentException("unique code can not be null or empty");
    }
    this.modelLog = modelLog;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public List<Integer> getGenerationSequence() {
    return null;
  }

  @Override
  public void attack() {
    try {
      modelLog.append(uniqueCode)
              .append("attack called");
      throw new IllegalArgumentException("Because the model said so.");
    } catch (IOException ignored) {
    }
  }

  @Override
  public ReadOnlyLocation getLocation(Coordinate coordinate)
          throws IllegalArgumentException {
    return null;
  }

  @Override
  public void moveToLocation(ReadOnlyLocation location)
          throws IllegalArgumentException, IllegalStateException {
    try {
      modelLog.append(uniqueCode)
              .append("move to location called: ")
              .append(location.toString());
      throw new IllegalArgumentException("Because the model said so.");
    } catch (IOException ignored) {
    }
  }

  @Override
  public int getRowCount() {
    try {
      modelLog.append(uniqueCode)
              .append("get row count called");
    } catch (IOException ignored) {
    }
    return 0;
  }

  @Override
  public int getColumnCount() {
    try {
      modelLog.append(uniqueCode)
              .append("get column count called");
    } catch (IOException ignored) {
    }
    return 0;
  }

  @Override
  public int getPercentage() {
    try {
      modelLog.append(uniqueCode)
              .append("get percentage called");
    } catch (IOException ignored) {
    }
    return 0;
  }

  @Override
  public int getDifficulty() {
    try {
      modelLog.append(uniqueCode)
              .append("get difficulty called");
    } catch (IOException ignored) {
    }
    return 0;
  }

  @Override
  public boolean getEnableWrap() {
    try {
      modelLog.append(uniqueCode)
              .append("get enable wrapped called");
    } catch (IOException ignored) {
    }
    return false;
  }

  @Override
  public int getInterconnectivity() {
    try {
      modelLog.append(uniqueCode)
              .append("get interconnectivity called");
    } catch (IOException ignored) {
    }
    return 0;
  }

  @Override
  public boolean thiefAtPlayerLocation() {
    return false;
  }

  @Override
  public boolean movingMonsterAliveAtPlayerLocation() {
    return false;
  }

  @Override
  public void move(Direction direction) throws IllegalArgumentException, IllegalStateException {
    try {
      modelLog.append(uniqueCode)
              .append("move called: ").append(direction.toString());
      throw new IllegalArgumentException("Because the model said so.");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void cedeTreasure(Treasure treasure)
          throws IllegalStateException, IllegalArgumentException {
    try {
      modelLog.append(uniqueCode)
              .append("cede treasure called: ")
              .append(treasure.toString());
      throw new IllegalArgumentException("Because the model said so.");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void cedeItem(Item item) throws IllegalStateException, IllegalArgumentException {
    try {
      modelLog.append(uniqueCode)
              .append("cede item called: ")
              .append(item.toString());
      throw new IllegalArgumentException("Because the model said so.");
    } catch (IOException ignored) {
    }
  }

  @Override
  public Sound shoot(Direction direction, int distance)
          throws IllegalArgumentException, IllegalStateException {
    try {
      modelLog.append(uniqueCode)
              .append("shoot called: ")
              .append(direction.toString())
              .append(" ")
              .append(String.valueOf(distance));
      throw new IllegalArgumentException("Because the model said so.");
    } catch (IOException ignored) {
    }
    return null;
  }

  @Override
  public ReadOnlyPlayer getPlayerDesc() {
    try {
      modelLog.append(uniqueCode)
              .append("get player description called");
    } catch (IOException ignored) {
    }
    return null;
  }

  @Override
  public ReadOnlyLocation getLocationDesc() {
    try {
      modelLog.append(uniqueCode)
              .append("get location description called");
    } catch (IOException ignored) {
    }
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean hasPlayerWon() {
    return false;
  }

  @Override
  public String toString() {
    return uniqueCode;
  }
}
