package controller;

import dungeon.Game;
import dungeon.ReadOnlyLocation;
import dungeon.ReadOnlyPlayer;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

import java.io.IOException;

/**
 * All four commands in this mock model always
 * append the input args to the gameLog and
 * throw an Illegal State exception.
 */
class MockGameIllegalState implements Game {

  private final String uniqueCode;
  private final Appendable gameLog;

  MockGameIllegalState(Appendable gameLog, String uniqueCode)  throws IllegalArgumentException {
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
    throw new IllegalStateException("Illegal state, because the model said so.");
  }

  @Override
  public void cedeTreasure(Treasure treasure)
      throws IllegalStateException, IllegalArgumentException {
    try {
      gameLog.append(treasure.toString()).append("\n");
    } catch (IOException ignored) {
    }
    throw new IllegalStateException("Illegal state, because the model said so.");
  }

  @Override
  public void cedeItem(Item item)
      throws IllegalStateException, IllegalArgumentException {
    try {
      gameLog.append(item.toString()).append("\n");
    } catch (IOException ignored) {
    }
    throw new IllegalStateException("Illegal state, because the model said so.");
  }


  @Override
  public ReadOnlyPlayer getPlayerDesc() throws IllegalStateException {
    return new PlayerDescTestImpl(uniqueCode);
  }

  @Override
  public ReadOnlyLocation getLocationDesc() throws IllegalStateException {
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
    throw new IllegalStateException("Illegal state, because the model said so.");
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
