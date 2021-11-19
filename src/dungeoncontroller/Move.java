package dungeoncontroller;

import dungeon.Game;
import dungeongeneral.Direction;

import java.io.IOException;

/**
 * Moves the player of a game.
 */
class Move extends AbstractCommand {

  private final Direction direction;

  /**
   * Constructor of this command.
   * @param direction direction in which the player is to be moved.
   */
  Move(Direction direction, Appendable out) {
    super(out);
    this.direction = direction;
  }

  @Override
  public void execute(Game game) {
    if (game == null) {
      throw new IllegalArgumentException("game can not be null");
    }
    game.movePlayer(direction);
    try {
      out.append("You move towards ").append(direction.toString()).append("\n");
    } catch (IOException ignored) {
    }
  }
}
