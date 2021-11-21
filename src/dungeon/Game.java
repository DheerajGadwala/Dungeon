package dungeon;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.LocationDesc;
import dungeongeneral.Odour;
import dungeongeneral.PlayerDesc;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

/**
 * This acts as a control over the dungeon graph and the player.
 * A {@link Player} and a {@link LocationGraph} are created on construction
 * of the game. Random {@link LocationNode} are chosen as start and end.
 * The player can be operated from this on the location graph.
 * A game is created with treasure in given percentage of caves and
 * items in the given percentage of locations. The dungeon has {@link Monster}s
 * in its locations. The number of monsters is equal to the given difficulty.
 * The player can move, pick up treasure and items and shoot arrows.
 */
public interface Game {

  /**
   * Moves player in the given direction if possible.
   * Sends a message about the outcome of the event.
   * @param direction direction in which the player is to be moved.
   * @throws IllegalArgumentException if direction is null or if the player
   *                                can not be moved in the given direction.
   * @throws IllegalStateException if the game is already over.
   */
  void move(Direction direction) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the odour at player location.
   * @return odour as perceived by the player.
   */
  Odour smell();

  /**
   * Makes the player collect treasure from his location if possible.
   * @param treasure Treasure type to be ceded.
   * @throws IllegalStateException if location is a tunnel or if the
   *                                location has no treasure at all or
   *                                if the game is already over.
   * @throws IllegalArgumentException if treasure is null or if the treasure
   *                                  is not the player's location.
   */
  void cedeTreasure(Treasure treasure) throws IllegalStateException, IllegalArgumentException;

  /**
   * Makes the player collect item from his location if possible.
   * @param item Item to be ceded.
   * @throws IllegalStateException if location has no items at all or
   *                                if the game is already over.
   * @throws IllegalArgumentException if given item type is not at the
   *                                  player's location.
   */
  void cedeItem(Item item) throws IllegalStateException, IllegalArgumentException;

  /**
   * Returns description of the player, which
   * includes: game stats, player's treasure and
   * items.
   * @return description of the player.
   */
  PlayerDesc getPlayerDesc();

  /**
   * Returns description of the player's location.
   * It includes the type of of location, treasure if any,
   * items if any and details about the monster, if it exists
   * in the location.
   * @return description of the player's location.
   */
  LocationDesc getLocationDesc();

  /**
   * Game's player shoots an arrow in the given direction and distance.
   * @param direction direction of the shot.
   * @param distance distance of the shot.
   * @return result of the shot.
   * @throws IllegalArgumentException if direction or distance are invalid.
   * @throws IllegalStateException when game is over.
   */
  ShotResult shoot(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns true if the player has at least one arrow.
   * @return true if player has at least one arrow else false.
   */
  boolean hasArrow();

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

  /**
   * To string of the game returns a map of the game in its then
   * state.
   * Representations:
   * t - tunnel
   * c - cave
   * T - treasure
   * I - items
   * S - Start
   * E - End
   * M = Monster
   * * - nothing
   * @return map of the game.
   */
  @Override
  String toString();
}
