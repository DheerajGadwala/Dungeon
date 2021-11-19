package controller;

import static dungeongeneral.ShotResult.MISS;

import dungeon.Game;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Odour;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

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
  public void movePlayer(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    //Unused
  }

  @Override
  public Odour getSmellAtPlayerLocation() throws IllegalStateException {
    return null;
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
    return MISS;
  }

  @Override
  public boolean playerHasArrow() {
    return true;
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
