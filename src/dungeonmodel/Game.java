package dungeonmodel;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyGame;
import dungeongeneral.Sound;
import dungeongeneral.Treasure;

/**
 * This acts as a control over the dungeon graph and the player.
 * A {@link Player} and a {@link LocationGraph} are created on construction
 * of the game. Random {@link LocationNode} are chosen as start and end.
 * The player can be operated from this on the location graph.
 * A game is created with treasure in given percentage of caves and
 * items in the given percentage of locations. The dungeon has {@link Entity}s
 * in its locations. The number of otyughs is equal to the given difficulty.
 * The player can move, pick up treasure and items and shoot arrows.
 */
public interface Game extends ReadOnlyGame {

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
   * Game's player shoots an arrow in the given direction and distance.
   * @param direction direction of the shot.
   * @param distance distance of the shot.
   * @return result of the shot.
   * @throws IllegalArgumentException if direction or distance are invalid.
   * @throws IllegalStateException when game is over.
   */
  Sound shoot(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException;
}
