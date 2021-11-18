package dungeon;

import general.Direction;
import general.Item;
import general.Odour;
import general.ShotResult;
import general.Treasure;

/**
 * This acts as a control over the dungeon graph and the player.
 * One player can be created in this.
 * A locationGraph is created on construction.
 * The player can be operated from this on the location graph.
 * A game is created with treasure in given percentage of caves.
 */
public interface Game {

  /**
   * create a new player with the given name in the dungeon.
   * @throws IllegalStateException if a player already exists in the dungeon.
   */
  void createPlayer() throws IllegalStateException;

  /**
   * Returns true if player is created.
   * @return true if player is created else false.
   */
  boolean isPlayerCreated();

  /**
   * Moves player in the given direction if possible.
   * Sends a message about the outcome of the event.
   * @param direction direction in which the player is to be moved.
   * @throws IllegalArgumentException if direction is null or if the player
   *                                can not be moved in the given direction.
   * @throws IllegalStateException if the player has not been created yet or
   *                                if the game is already over.
   */
  void movePlayer(Direction direction) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the odour at player location.
   * @return odour as perceived by the player.
   * @throws IllegalStateException if player has not been created yet.
   */
  Odour getSmellAtPlayerLocation() throws IllegalStateException;

  /**
   * Makes the player collect treasure from his location if possible.
   * @param t Treasure type to be ceded.
   * @throws IllegalStateException if the player has not been created yet or
   *                              location is a tunnel or if the game is already over.
   * @throws IllegalArgumentException if t is null or if the treasure
   *                                  is not the player's location.
   */
  void cedeTreasure(Treasure t) throws IllegalStateException, IllegalArgumentException;

  /**
   * Makes the player collect item from his location if possible.
   * @param i Item to be ceded.
   * @throws IllegalStateException if the player has not been created yet or
   *                              if the item is not in the player's location or
   *                              if the game is already over.
   * @throws IllegalArgumentException if the item is null.
   */
  void cedeItem(Item i) throws IllegalStateException, IllegalArgumentException;

  /**
   * Returns description of the player, along with
   * his current treasure and location details.
   * @return description of the player.
   * @throws IllegalStateException when player has not been added yet.
   */
  String getPlayerDescription() throws IllegalStateException;

  /**
   * Returns description of the player's location,
   * along with the treasure at that location if any.
   * @return description of the player's location.
   * @throws IllegalStateException when player has not been added yet.
   */
  String getPlayerLocationDescription() throws IllegalStateException;

  /**
   * Game's player shoots an arrow in the given direction and distance.
   * @param direction direction of the shot.
   * @param distance distance of the shot.
   * @return result of the shot.
   * @throws IllegalArgumentException if direction or distance are invalid.
   * @throws IllegalStateException when game is over.
   */
  ShotResult shootArrow(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns true if the player has at least one arrow.
   * @return true if player has at least one arrow else false.
   */
  boolean playerHasArrow();

  /**
   * Returns true if player has reached end.
   * Disables further moves.
   * @return true is game is over.
   */
  boolean isGameOver();
  /**
   * returns true if the player has won the game else false.
   * @return true if player has won else false.
   */
  boolean hasPlayerWon();
}
