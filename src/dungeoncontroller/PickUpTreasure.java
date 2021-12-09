package dungeoncontroller;

import dungeongeneral.Treasure;
import dungeonmodel.Game;

import java.io.IOException;

/**
 * Player picks up given treasure on this command.
 */
class PickUpTreasure extends AbstractCommand {

  private final Treasure pick;

  /**
   * Constructor of this command.
   * @param pick Treasure to be picked by the treasure.
   */
  PickUpTreasure(Treasure pick, Appendable out) {
    super(out);
    this.pick = pick;
  }

  @Override
  public void execute(Game game) {
    if (game == null) {
      throw new IllegalArgumentException("game can not be null");
    }
    game.cedeTreasure(pick);
    try {
      out.append("You picked 1 ").append(pick.toString()).append("\n");
    } catch (IOException ignored) {
    }
  }
}
