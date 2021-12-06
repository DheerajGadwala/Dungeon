package dungeongeneral;

/**
 * Represents all directions.
 */
public enum Direction {
  NORTH("N", "North"), EAST("E", "East"), SOUTH("S", "South"), WEST("W", "West");

  private final String shortForm;
  private final String fullForm;

  Direction(String shortForm, String fullForm) {
    this.shortForm = shortForm;
    this.fullForm = fullForm;
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

  public String getShortForm() {
    return shortForm;
  }

  public String getFullForm() {
    return fullForm;
  }

  @Override
  public String toString() {
    return this.shortForm;

  }
}
