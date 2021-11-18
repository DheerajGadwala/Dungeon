package dungeoncontroller;

import dungeon.Game;
import general.Treasure;

/**
 * Player picks up given treasure on this command.
 */
class PickUpTreasure implements Command {

  private final Treasure pick;

  /**
   * Constructor of this command.
   * @param pick Treasure to be picked by the treasure.
   */
  PickUpTreasure(Treasure pick) {
    this.pick = pick;
  }

  @Override
  public void execute(Game game) {
    game.cedeTreasure(pick);
  }
}
