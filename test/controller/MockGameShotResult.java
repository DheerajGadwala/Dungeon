package controller;

import dungeon.Game;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

import java.io.IOException;

/**
 * This mock is always in a 'game not over' state.
 * This mock returns given shotResult and hasArrow inputs
 * as Shoot and hasArrow methods respectively.
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
  public void move(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    //Unused
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
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this.shotResult;
  }

  @Override
  public boolean hasArrow() {
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
