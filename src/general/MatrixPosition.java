package general;

import static java.util.Objects.hash;

/**
 * Represents the position of an object inside a matrix.
 */
public class MatrixPosition {
  private final int i;
  private final int j;
  private final int hashcode;

  /**
   * Creates a MatrixPosition object.
   * @param i row index
   * @param j column index
   */
  public MatrixPosition(int i, int j) {
    this.i = i;
    this.j = j;
    this.hashcode = hash(i) + hash(j);
  }

  /**
   * returns i of this matrix position.
   * @return i.
   */
  public int getI() {
    return i;
  }

  /**
   * returns j of this matrix position.
   * @return j.
   */
  public int getJ() {
    return j;
  }

  @Override
  public boolean equals(Object that) {
    if (that == null) {
      return false;
    }
    else if (that.getClass() == this.getClass()) {
      MatrixPosition thatMatrixPosition = (MatrixPosition) that;
      return thatMatrixPosition.getI() == i && thatMatrixPosition.getJ() == j;
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
    return String.format("(%d, %d)", i, j);
  }
}