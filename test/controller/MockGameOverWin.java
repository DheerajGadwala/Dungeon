package controller;

import static dungeongeneral.ShotResult.MISS;

import dungeon.Game;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.LocationDesc;
import dungeongeneral.Odour;
import dungeongeneral.PlayerDesc;
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
  public void move(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    //Unused
  }

  @Override
  public Odour smell() throws IllegalStateException {
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
    return true;
  }
}
