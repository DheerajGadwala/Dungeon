package dungeoncontroller;

import dungeon.Game;
import general.Item;
import general.Treasure;

/**
 * Player picks up given treasure on this command.
 */
class PickUpItem implements Command {

  private final Item pick;

  /**
   * Constructor of this command.
   * @param pick Treasure to be picked by the treasure.
   */
  PickUpItem(Item pick) {
    this.pick = pick;
  }

  @Override
  public void execute(Game game) {
    game.cedeItem(pick);
  }
}
