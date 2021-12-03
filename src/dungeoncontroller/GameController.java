package dungeoncontroller;

import dungeonmodel.Game;

/**
 * This the controller for the text based game.
 */
public interface GameController {

  /**
   * Starts the game.
   * @param game model of the game.
   */
  void playGame(Game game);

}
