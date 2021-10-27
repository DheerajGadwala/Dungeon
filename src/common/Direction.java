package common;

/**
 * Represents all directions.
 */
public enum Direction {
  NORTH, EAST, SOUTH, WEST;

  /**
   * Gives the opposite direction to the given direction.
   * @param direction given direction
   * @return opposite direction
   */
  public static Direction getOpposite(Direction direction) {
    if (direction == NORTH) {
      return SOUTH;
    }
    else if (direction == SOUTH) {
      return NORTH;
    }
    else if (direction == EAST) {
      return WEST;
    }
    else {
      return EAST;
    }
  }
}
