package dungeon;

import general.Direction;
import general.Item;
import general.MatrixPosition;
import general.Odour;
import general.ShotResult;
import general.Treasure;

import java.util.List;

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

  // TODO: Make this package private and write internal tests.
  /**
   * Get current position of player.
   * @return current matrix position of player.
   * @throws IllegalStateException if the player has not been created yet.
   */
  MatrixPosition getPlayerPosition() throws IllegalStateException;

  /**
   * Returns true if the player has at least one arrow.
    * @return true if player has at least one arrow else false.
   */
  boolean playerHasArrow();

  /**
   * Get start position.
   * @return matrix position of start location.
   */
  MatrixPosition getStartPosition();

  // TODO: Make this package private and write internal tests.
  /**
   * Get end position.
   * @return matrix position of end location.
   */
  MatrixPosition getEndPosition();

  /**
   * Returns true if player has reached end.
   * Disables further moves.
   * @return true is game is over.
   */
  boolean isGameOver();

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

  // TODO: Make this package private and write internal tests.
  /**
   * returns input percentage.
   * @return returns input percentage.
   */
  int getPercentage();

  // TODO: Make this package private and write internal tests.
  /**
   * Returns all connections between nodes.
   * These are uni directional.
   * @return list of all connections.
   */
  List<List<MatrixPosition>> getAllConnections();

  // TODO: Make this package private and write internal tests.
  /**
   * Returns positions of all location nodes.
   * @return positions of all locations.
   */
  List<MatrixPosition> getAllPositions();

  // TODO: Make this package private and write internal tests.
  /**
   * Checks if there is a cave at the given position.
   * @param position position to be checked.
   * @return true if the position has a cave.
   * @throws IllegalArgumentException if given position that is not in the dungeon graph
   *                                  or if it is null.
   */
  boolean caveAtPosition(MatrixPosition position) throws IllegalArgumentException;

  // TODO: Make this package private and write internal tests.
  /**
   * Checks if there is treasure at the location on the given position.
   * @param position position to be checked.
   * @return true if the location has treasure.
   * @throws IllegalArgumentException if given position that is not in the dungeon graph
   *                                  or if it is null.
   */
  boolean treasureAtPosition(MatrixPosition position) throws IllegalArgumentException;

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
   * returns true if the player has won the game else false.
   * @return true if player has won else false.
   */
  boolean hasPlayerWon();
}
