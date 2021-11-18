package dungeoncontroller;

import dungeon.Game;
import general.Direction;
import general.ShotResult;

import java.io.IOException;

/**
 * Shoot command, makes the player of the game shoot an arrow in the given direction and distance.
 * This command also appends the result of the execution to the output.
 */
class Shoot implements Command {

  private final Direction direction;
  private final int distance;
  private final Appendable out;

  /**
   * Constructor of the command.
   * @param direction direction of the shot.
   * @param distance distance of the shot.
   * @param out appendable.
   */
  Shoot(Direction direction, int distance, Appendable out) {
    this.direction = direction;
    this.distance = distance;
    this.out = out;
  }

  @Override
  public void execute(Game game) throws IllegalArgumentException, IllegalStateException {
    ShotResult sr = game.shootArrow(direction, distance);
    try {
      out.append(sr.getImplication());
      out.append("\n");
      if (!game.playerHasArrow()) {
        out.append("You are out of arrows!\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
