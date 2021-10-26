package Common;

public enum Direction {
  NORTH, EAST, SOUTH, WEST;

  public static Direction getOpposite(Direction direction) {
    if (direction == NORTH) {
      return SOUTH;
    }
    else if(direction == SOUTH) {
      return NORTH;
    }
    else if(direction == EAST) {
      return WEST;
    }
    else {
      return EAST;
    }
  }
}
