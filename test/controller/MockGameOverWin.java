package controller;

import static dungeongeneral.Sound.HISS;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeongeneral.Sound;
import dungeongeneral.Treasure;
import dungeonmodel.Game;

/**
 * This mock is always in a 'game over' and 'player won' state.
 */
class MockGameOverWin implements Game {

  private final String uniqueCode;

  MockGameOverWin(Appendable gameLog, String uniqueCode)  throws IllegalArgumentException {
    if (uniqueCode == null || uniqueCode.isEmpty()) {
      throw new IllegalArgumentException("uniqueCode can not be null or empty");
    }
    if (gameLog == null) {
      throw new IllegalArgumentException("Game log can not be null");
    }
    this.uniqueCode = uniqueCode;
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
    return HISS;
  }

  @Override
  public boolean isGameOver() {
    return true;
  }

  @Override
  public boolean hasPlayerWon() {
    return true;
  }
}
