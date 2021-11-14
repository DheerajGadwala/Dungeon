package dungeon;

import general.Direction;
import general.MatrixPosition;

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
   * @param name name of the player.
   * @throws IllegalArgumentException if the name is null.
   * @throws IllegalStateException if a player already exists in the dungeon.
   */
  void createPlayer(String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Displays map along with player position and start and end locations.
   * Meaning of symbols:
   * P: player
   * O: Cave without treasure
   * T: Tunnel without treasure
   * +: Tunnel
   * S: Start
   * E: End
   * Precedence:
   * P E S + T O
   * @return a string representation of the map.
   * @throws IllegalStateException when player has not been added yet.
   */
  String displayMap() throws IllegalStateException;

  /**
   * Displays map without players and treasures.
   * @return a string representation of the map.
   */
  String displayStaticMap();

  /**
   * Returns all possible moves from the player's location.
   * @return List of directions to the player's current locations' neighbours.
   * @throws IllegalStateException when a player has not been created yet.
   */
  List<Direction> getPossibleMoves() throws IllegalStateException;

  /**
   * Moves player in the given direction if possible.
   * Sends a message about the outcome of the event.
   * @param direction direction in which the player is to be moved.
   * @return outcome of the event.
   * @throws IllegalArgumentException if direction is null.
   * @throws IllegalStateException if the player has not been created yet.
   */
  String movePlayer(Direction direction) throws IllegalArgumentException;

  /**
   * Makes the player collect treasure from his location if possible.
   * Sends a message about the outcome of the event.
   * @return outcome of the event.
   * @throws IllegalStateException if the player has not been created yet.
   */
  String cedeTreasure() throws IllegalStateException;

  /**
   * Get current position of player.
   * @return current matrix position of player.
   * @throws IllegalStateException if the player has not been created yet.
   */
  MatrixPosition getPlayerPosition() throws IllegalStateException;

  /**
   * checks if player location has treasure.
   * @return true if player's location has treasure.
   * @throws IllegalStateException if player has not been created yet.
   */
  boolean playerLocationHasTreasure() throws IllegalStateException;

  /**
   * Get start position.
   * @return matrix position of start location.
   */
  MatrixPosition getStartPosition();

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

  /**
   * Returns a description of the player's treasure.
   * @return description of the treasure the player has.
   * @throws IllegalStateException when player has not been added yet.
   */
  String getPlayerTreasureDescription() throws IllegalStateException;

  /**
   * Returns status of the game.
   * @return status of the game.
   */
  String gameStatus();

  /**
   * returns percentage of caves that have treasure.
   * @return percentage of caves that have treasure.
   */
  int getTreasurePercentage();

  /**
   * Returns all connections between nodes.
   * These are uni directional.
   * @return list of all connections.
   */
  List<List<MatrixPosition>> getAllConnections();

  /**
   * Returns positions of all location nodes.
   * @return positions of all locations.
   */
  List<MatrixPosition> getAllPositions();

  /**
   * Checks if there is a cave at the given position.
   * @param position position to be checked.
   * @return true if the position has a cave.
   * @throws IllegalArgumentException if given position that is not in the dungeon graph
   *                                  or if it is null.
   */
  boolean caveAtPosition(MatrixPosition position) throws IllegalArgumentException;

  /**
   * Checks if there is a tunnel at the given position.
   * @param position position to be checked.
   * @return true if the position has a tunnel.
   * @throws IllegalArgumentException if given position that is not in the dungeon graph
   *                                  or if it is null.
   */
  boolean tunnelAtPosition(MatrixPosition position) throws IllegalArgumentException;

  /**
   * Checks if there is treasure at the location on the given position.
   * @param position position to be checked.
   * @return true if the location has treasure.
   * @throws IllegalArgumentException if given position that is not in the dungeon graph
   *                                  or if it is null.
   */
  boolean treasureAtPosition(MatrixPosition position) throws IllegalArgumentException;
}
