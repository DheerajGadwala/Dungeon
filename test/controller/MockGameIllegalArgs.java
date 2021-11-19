package controller;

import static dungeongeneral.Odour.ODOURLESS;

import dungeon.Game;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Odour;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

import java.io.IOException;

/**
 * All four commands in this mock model always
 * append the input args to the gameLog and
 * throw an Illegal Arguments exception.
 */
class MockGameIllegalArgs implements Game {

  private final String uniqueCode;
  private final Appendable gameLog;

  MockGameIllegalArgs(Appendable gameLog, String uniqueCode)  throws IllegalArgumentException {
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
  public void movePlayer(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    try {
      gameLog.append(direction.toString()).append("\n");
    } catch (IOException ignored) {
    }
    throw new IllegalArgumentException("Illegal args, because the model said so.");
  }

  @Override
  public Odour getSmellAtPlayerLocation() throws IllegalStateException {
    return ODOURLESS;
  }

  @Override
  public void cedeTreasure(Treasure treasure)
      throws IllegalStateException, IllegalArgumentException {
    try {
      gameLog.append(treasure.toString()).append("\n");
    } catch (IOException ignored) {
    }
    throw new IllegalArgumentException("Illegal args, because the model said so.");
  }

  @Override
  public void cedeItem(Item item) throws IllegalStateException, IllegalArgumentException {
    try {
      gameLog.append(item.toString()).append("\n");
    } catch (IOException ignored) {
    }
    throw new IllegalArgumentException("Illegal args, because the model said so.");
  }

  @Override
  public String getPlayerDescription() throws IllegalStateException {
    return uniqueCode;
  }

  @Override
  public String getLocationDescription() throws IllegalStateException {
    return uniqueCode;
  }

  @Override
  public ShotResult shootArrow(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
    try {
      gameLog.append(direction.toString()).append("\n");
      gameLog.append(String.valueOf(distance)).append("\n");
    } catch (IOException ignored) {
    }
    throw new IllegalArgumentException("Illegal args, because the model said so.");
  }

  @Override
  public boolean playerHasArrow() {
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