package controller;

import static dungeongeneral.ShotResult.MISS;

import dungeon.Game;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.LocationDesc;
import dungeongeneral.PlayerDesc;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

import java.io.IOException;

/**
 * All four commands in this mock model always
 * append the input args to the gameLog and
 * throws no exception.
 */
class MockGameLogger implements Game {

  private final String uniqueCode;
  private final Appendable gameLog;

  MockGameLogger(Appendable gameLog, String uniqueCode)  throws IllegalArgumentException {
    if (uniqueCode == null || uniqueCode.isEmpty()) {
      throw new IllegalArgumentException("uniqueCode can not be null or empty");
    }
    if (gameLog == null) {
      throw new IllegalArgumentException("Game log can not be null");
    }
    this.gameLog = gameLog;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void move(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    try {
      gameLog.append(direction.toString()).append("\n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void attack() {

  }

  @Override
  public void cedeTreasure(Treasure treasure)
      throws IllegalStateException, IllegalArgumentException {
    try {
      gameLog.append(treasure.toString()).append("\n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void cedeItem(Item item) throws IllegalStateException, IllegalArgumentException {
    try {
      gameLog.append(item.toString()).append("\n");
    } catch (IOException ignored) {
    }
  }


  @Override
  public PlayerDesc getPlayerDesc() throws IllegalStateException {
    return new PlayerDescTestImpl(uniqueCode);
  }

  @Override
  public LocationDesc getLocationDesc() throws IllegalStateException {
    return new LocationDescTestImpl(uniqueCode);
  }

  @Override
  public ShotResult shoot(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
    try {
      gameLog.append(direction.toString()).append("\n");
      gameLog.append(String.valueOf(distance)).append("\n");
    } catch (IOException ignored) {
    }
    return MISS;
  }

  @Override
  public boolean hasArrow() {
    return true;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean hasPlayerWon() {
    return false;
  }
}
