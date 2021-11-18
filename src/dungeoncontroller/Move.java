package dungeoncontroller;

import dungeon.Game;
import general.Direction;

/**
 * Moves the player of a game.
 */
class Move implements Command {

  private final Direction direction;

  /**
   * Constructor of this command.
   * @param direction direction in which the player is to be moved.
   */
  Move(Direction direction) {
    this.direction = direction;
  }

  @Override
  public void execute(Game game) {
    game.movePlayer(direction);
  }
}
