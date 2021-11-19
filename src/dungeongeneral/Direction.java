package dungeongeneral;

/**
 * Represents all directions.
 */
public enum Direction {
  NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

  private final String shortForm;

  Direction(String shortForm) {
    this.shortForm = shortForm;
  }

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

  @Override
  public String toString() {
    return this.shortForm;

  }
}
