package dungeongeneral;

import java.util.List;

/**
 * ReadOnlyGame for a game wit the newly added obstacles.
 */
public interface ReadOnlyGameWithObstacles extends ReadOnlyGame{

  /**
   * Get Readonly location object at that coordinate.
   * @param coordinate coordinates whose location is being requested.
   * @return readonly location object.
   * @throws IllegalArgumentException when coordinates do not exist in the dungeon.
   */
  ReadOnlyLocation getLocation(Coordinate coordinate) throws IllegalArgumentException;

  /**
   * returns the number of rows in the dungeon.
   * @return the number of rows in the dungeon.
   */
  int getRowCount();

  /**
   * returns the number of columns in the dungeon.
   * @return the number of columns in the dungeon.
   */
  int getColumnCount();

  /**
   * returns the percentage input.
   * @return percentage input.
   */
  int getPercentage();

  /**
   * returns difficulty input.
   * @return difficulty input.
   */
  int getDifficulty();

  /**
   * returns if dungeon is wrapped or not.
   * @return true if dungeon is wrapped else false.
   */
  boolean getEnableWrap();

  /**
   * returns interconnectivity input.
   * @return interconnectivity input.
   */
  int getInterconnectivity();

  /**
   * Returns true if thief is at player location.
   * @return true if thief at player location else false.
   */
  boolean thiefAtPlayerLocation();

  /**
   * Returns true if moving monster is at player location.
   * @return true if moving monster at player location else false.
   */
  boolean movingMonsterAtPlayerLocation();

}
