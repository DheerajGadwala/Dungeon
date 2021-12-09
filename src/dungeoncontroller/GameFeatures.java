package dungeoncontroller;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.Treasure;

/**
 * Features of a game that can be used by the view.
 * This can be used to start a new game, perform
 * commands on the game, restart/ reset/ quit the game.
 */
public interface GameFeatures {

  /**
   * Generates a dungeon with given arguments.
   * @param rows number of rows in the dungeon.
   * @param columns number of columns in the dungeon.
   * @param percentage percentage input for the dungeon.
   * @param difficulty difficulty level.
   * @param enableWrap wrap enables dungeon or not.
   * @param interconnectivity interconnectivity of the dungeon.
   * @throws IllegalArgumentException when inputs are not valid.
   */
  void startNewGame(
          int rows, int columns, int percentage,
          int difficulty, boolean enableWrap,
          int interconnectivity) throws IllegalArgumentException;

  /**
   * Calls move on the model and refreshes the view.
   * Illegal argument exception from the model are ignored.
   * On Illegal state exception from the model a message is displayed on the view.
   * @param direction direction in which the player will be moved in the model.
   */
  void move(Direction direction);

  /**
   * calls move to location on the view and refreshes the view.
   * Illegal argument exception from the model are ignored.
   * On Illegal state exception from the model a message is displayed on the view.
   * @param location location to which the player is to be moved.
   */
  void moveToLocation(ReadOnlyLocation location);

  /**
   * Calls shoot function of the model and refreshes the view.
   * Illegal argument exception from the model are ignored.
   * On Illegal state exception from the model a message is displayed on the view.
   * @param direction direction of shoot for the model
   * @param distance distance of shoot for the model
   */
  void shoot(Direction direction, int distance);

  /**
   * Calls pick treasure on the model and refreshes the view.
   * Illegal argument exception from the model are ignored.
   * On Illegal state exception from the model a message is displayed on the view.
   * @param treasure treasure to be picked.
   */
  void pickTreasure(Treasure treasure);

  /**
   * Calls pick item on the model and refreshes the view.
   * Illegal argument exception from the model are ignored.
   * On Illegal state exception from the model a message is displayed on the view.
   * @param item item to be picked.
   */
  void pickItem(Item item);

  /**
   * Calls attack on the model and refreshes the view.
   * Illegal argument exception from the model are ignored.
   * On Illegal state exception from the model a message is displayed on the view.
   */
  void attack();

  /**
   * Makes the view visible.
   */
  void playGame();

  /**
   * Passes a new model to the view and refreshes it.
   */
  void restartGame();

  /**
   * Passes a new model to the view with the same start state as
   * the previous one and refreshes it.
   */
  void resetGame();

  /**
   * Exits the program.
   */
  void quitGame();
}
