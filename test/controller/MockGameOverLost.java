package controller;

import static dungeongeneral.ShotResult.MISS;

import dungeon.Game;
import dungeon.ReadOnlyLocation;
import dungeon.ReadOnlyPlayer;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

/**
 * This mock is always in a 'game over' and 'player lost' state.
 */
class MockGameOverLost implements Game {

  private final String uniqueCode;

  MockGameOverLost(Appendable gameLog, String uniqueCode)  throws IllegalArgumentException {
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
  public void cedeItem(Item item) throws IllegalStateException, IllegalArgumentException {
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
    return MISS;
  }

  @Override
  public boolean hasArrow() {
    return true;
  }

  @Override
  public boolean isGameOver() {
    return true;
  }

  @Override
  public boolean hasPlayerWon() {
    return false;
  }
}
