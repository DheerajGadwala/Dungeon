package dungeoncontroller;

import dungeon.Game;

/**
 * Represents a command that can be executed on a model.
 */
interface Command {

  /**
   * executes command on the model of a game.
   * @param game game model on which the command is to be executed.
   * @throws IllegalArgumentException depends on the command.
   * @throws IllegalStateException depends on the command.
   */
  public void execute(Game game)
      throws IllegalArgumentException, IllegalStateException;
}
