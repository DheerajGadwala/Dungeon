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
 * This mock is always in a 'game not over' state.
 * This mock returns given odour input as smell
 * method's output.
 * Stores odour passed in the constructor as a state and returns it.
 */
class MockGameOdour implements Game {

  private final String uniqueCode;
  private final Odour odour;

  MockGameOdour(Appendable gameLog, String uniqueCode, Odour odour)
      throws IllegalArgumentException {
    if (uniqueCode == null || uniqueCode.isEmpty()) {
      throw new IllegalArgumentException("uniqueCode can not be null or empty");
    }
    if (gameLog == null) {
      throw new IllegalArgumentException("Game log can not be null");
    }
    if (odour == null) {
      throw new IllegalArgumentException("Odour can not be null");
    }
    this.uniqueCode = uniqueCode;
    this.odour = odour;
  }

  @Override
  public void move(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    //Unused
  }

  @Override
  public Odour smell() throws IllegalStateException {
    return this.odour;
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
    return false;
  }

  @Override
  public boolean hasPlayerWon() {
    return false;
  }
}
