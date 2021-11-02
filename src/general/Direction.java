package general;

/**
 * Represents all directions.
 */
public enum Direction {
  NORTH, EAST, SOUTH, WEST;

  /**
   * Gives the opposite direction to the given direction.
   * @return opposite direction
   */
  public Direction getOpposite() {
    if (this == NORTH) {
      return SOUTH;
    }
    else if (this == SOUTH) {
      return NORTH;
    }
    else if (this == EAST) {
      return WEST;
    }
    else {
      return EAST;
    }
  }
}
