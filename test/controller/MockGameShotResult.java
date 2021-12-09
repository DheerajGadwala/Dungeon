package controller;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeongeneral.Sound;
import dungeongeneral.Treasure;
import dungeonmodel.Game;

import java.io.IOException;

/**
 * This mock is always in a 'game not over' state.
 */
class MockGameShotResult implements Game {

  private final String uniqueCode;
  private final Appendable gameLog;
  private final Sound sound;

  MockGameShotResult(
          Appendable gameLog, String uniqueCode,
          Sound sound
  ) throws IllegalArgumentException {
    if (uniqueCode == null || uniqueCode.isEmpty()) {
      throw new IllegalArgumentException("uniqueCode can not be null or empty");
    }
    if (gameLog == null) {
      throw new IllegalArgumentException("Game log can not be null");
    }
    if (sound == null) {
      throw new IllegalArgumentException("Odour can not be null");
    }
    this.gameLog = gameLog;
    this.uniqueCode = uniqueCode;
    this.sound = sound;
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
    return new MockReadOnlyPlayer(uniqueCode);
  }

  @Override
  public ReadOnlyLocation getLocationDesc() throws IllegalStateException {
    return new MockReadOnlyLocation(uniqueCode);
  }

  @Override
  public Sound shoot(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
    try {
      gameLog.append(direction.toString()).append("\n");
      gameLog.append(String.valueOf(distance)).append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this.sound;
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
