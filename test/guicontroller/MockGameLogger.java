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
import java.util.Arrays;
import java.util.List;

/**
 * This model logs what functions are called and the inputs received by those functions.
 * on call to getInitialState, this mock model returns a new model of the same type with a
 * unique code 520.
 */
class MockGameLogger implements GameWithObstacles {

  private final Appendable modelLog;
  private final String uniqueCode;

  MockGameLogger(Appendable modelLog, String uniqueCode) {
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
    try {
      modelLog.append(uniqueCode)
              .append("get generation sequence called\n");
    } catch (IOException ignored) {
    }
    return Arrays.asList(
            61,57,37,47,20,23,50,47,18,27,37,28,3,18,19,12,10,23,14,26,6,9,10,11,
            4,4,3,6,2,2,4,1,0,4,2,3,3,1,2,2,3,1,3,2,2,2,3,1,3,14,4,14,1,9,5,5,3,11,2,4,1,9,2,
            2,5,3,4,6,2,7,2,3,1,3,1,0,0,-1,1,1,2,1,3,0,2,2,3
    );
  }

  @Override
  public void attack() {
    try {
      modelLog.append(uniqueCode)
              .append("attack called");
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
    } catch (IOException ignored) {
    }
  }

  @Override
  public int getRowCount() {
    try {
      modelLog.append(uniqueCode)
              .append("get row count called\n");
    } catch (IOException ignored) {
    }
    return 4;
  }

  @Override
  public int getColumnCount() {
    try {
      modelLog.append(uniqueCode)
              .append("get column count called\n");
    } catch (IOException ignored) {
    }
    return 4;
  }

  @Override
  public int getPercentage() {
    try {
      modelLog.append(uniqueCode)
              .append("get percentage called\n");
    } catch (IOException ignored) {
    }
    return 50;
  }

  @Override
  public int getDifficulty() {
    try {
      modelLog.append(uniqueCode)
              .append("get difficulty called\n");
    } catch (IOException ignored) {
    }
    return 3;
  }

  @Override
  public boolean getEnableWrap() {
    try {
      modelLog.append(uniqueCode)
              .append("get enable wrapped called\n");
    } catch (IOException ignored) {
    }
    return true;
  }

  @Override
  public int getInterconnectivity() {
    try {
      modelLog.append(uniqueCode)
              .append("get interconnectivity called\n");
    } catch (IOException ignored) {
    }
    return 2;
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
              .append("move called: ")
              .append(direction.toString());
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
    } catch (IOException ignored) {
    }
  }

  @Override
  public void cedeItem(Item item) throws IllegalStateException, IllegalArgumentException {
    try {
      modelLog.append(uniqueCode)
              .append("cede item called: ")
              .append(item.toString());
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
