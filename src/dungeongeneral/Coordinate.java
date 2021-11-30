package dungeongeneral;

import static java.util.Objects.hash;

/**
 * Represents the position of an object inside a matrix.
 */
public class Coordinate {
  private final int row;
  private final int column;
  private final int hashcode;

  /**
   * Creates a MatrixPosition object.
   * @param row row index
   * @param column column index
   */
  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
    this.hashcode = hash(row) + hash(column);
  }

  /**
   * returns i of this matrix position.
   * @return i.
   */
  public int getRow() {
    return row;
  }

  /**
   * returns j of this matrix position.
   * @return j.
   */
  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(Object that) {
    if (that == null) {
      return false;
    }
    else if (that.getClass() == this.getClass()) {
      Coordinate thatCoordinate = (Coordinate) that;
      return thatCoordinate.getRow() == row && thatCoordinate.getColumn() == column;
    }
    else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return hashcode;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", row, column);
  }

}
