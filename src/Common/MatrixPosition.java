package Common;

public class MatrixPosition {
  private final int i;
  private final int j;

  public MatrixPosition(int i, int j) {
    this.i = i;
    this.j = j;
  }

  private int getI() {
    return i;
  }

  private int getJ() {
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
    else{
      return false;
    }
  }
}
