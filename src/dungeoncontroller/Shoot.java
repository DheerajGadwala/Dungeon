package dungeoncontroller;

import dungeon.Game;
import dungeongeneral.Direction;
import dungeongeneral.ShotResult;

import java.io.IOException;

/**
 * Shoot command, makes the player of the game shoot an arrow in the given direction and distance.
 * This command also appends the result of the execution to the output.
 */
class Shoot extends AbstractCommand {

  private final Direction direction;
  private final int distance;

  /**
   * Constructor of the command.
   * @param direction direction of the shot.
   * @param distance distance of the shot.
   * @param out appendable.
   */
  Shoot(Direction direction, int distance, Appendable out) {
    super(out);
    this.direction = direction;
    this.distance = distance;
  }

  @Override
  public void execute(Game game) throws IllegalArgumentException, IllegalStateException {
    ShotResult sr = game.shoot(direction, distance);
    try {
      out.append(sr.getImplication());
      out.append("\n");
      if (!game.hasArrow()) {
        out.append("You are out of arrows!\n");
      }
    } catch (IOException ignored) {
    }
  }
}
