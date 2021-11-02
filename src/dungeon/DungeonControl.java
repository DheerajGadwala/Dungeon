package dungeon;

import general.Direction;

import java.util.List;

/**
 * This acts as a control over the dungeon graph and the player.
 * One player can be created in this.
 * The player can be operated from this.
 */
public interface DungeonControl {

  /**
   * create a new player with the given name in the dungeon.
   * @param name name of the player.
   * @throws IllegalArgumentException if the name is null.
   * @throws IllegalStateException if a player already exists in the dungeon.
   */
  void createPlayer(String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Generates random treasure in the given percentage of location nodes.
   * @param percentage percentage of locations which are supposed to have treasure.
   * @throws IllegalArgumentException if the percentage is not between 0-100.
   */
  void generateTreasure(int percentage) throws IllegalArgumentException;

  /**
   * Displays map along with player position and start and end locations.
   * @return a string representation of the map.
   */
  String displayMap();

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
  String movePlayer(Direction direction) throws IllegalArgumentException, IllegalStateException;

  /**
   * Makes the player collect treasure from his location if possible.
   * Sends a message about the outcome of the event.
   * @return outcome of the event.
   * @throws IllegalStateException if the player has not been created yet.
   */
  String cedeTreasure() throws IllegalStateException;
}
