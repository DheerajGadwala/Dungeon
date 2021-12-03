package dungeoncontroller;

import dungeonmodel.Game;
import dungeongeneral.Item;

import java.io.IOException;

/**
 * Player picks up given treasure on this command.
 */
class PickUpItem extends AbstractCommand {

  private final Item pick;

  /**
   * Constructor of this command.
   * @param pick Treasure to be picked by the treasure.
   */
  PickUpItem(Item pick, Appendable out) {
    super(out);
    this.pick = pick;
  }

  @Override
  public void execute(Game game) {
    if (game == null) {
      throw new IllegalArgumentException("game can not be null");
    }
    game.cedeItem(pick);
    try {
      out.append("You picked 1 ").append(pick.toString()).append("\n");
    } catch (IOException ignored) {
    }
  }
}
