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
 * This mock is always in a 'game not over' state.
 * This mock returns given shotResult and hasArrow inputs
 * as Shoot and playerHasArrow methods respectively.
 */
class MockGameShotResult implements Game {

  private final String uniqueCode;
  private final Appendable gameLog;
  private final ShotResult shotResult;
  private final boolean hasArrow;

  MockGameShotResult(
      Appendable gameLog, String uniqueCode,
      ShotResult shotResult, boolean hasArrow
  ) throws IllegalArgumentException {
    if (uniqueCode == null || uniqueCode.isEmpty()) {
      throw new IllegalArgumentException("uniqueCode can not be null or empty");
    }
    if (gameLog == null) {
      throw new IllegalArgumentException("Game log can not be null");
    }
    if (shotResult == null) {
      throw new IllegalArgumentException("Odour can not be null");
    }
    this.gameLog = gameLog;
    this.uniqueCode = uniqueCode;
    this.shotResult = shotResult;
    this.hasArrow = hasArrow;
  }

  @Override
  public void movePlayer(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    //Unused
  }

  @Override
  public Odour getSmellAtPlayerLocation() throws IllegalStateException {
    return ODOURLESS;
  }

  @Override
  public void cedeTreasure(Treasure treasure)
      throws IllegalStateException, IllegalArgumentException {
    //Unused
  }

  @Override
  public void cedeItem(Item item)
      throws IllegalStateException, IllegalArgumentException {
    //Unused
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
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this.shotResult;
  }

  @Override
  public boolean playerHasArrow() {
    return this.hasArrow;
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
