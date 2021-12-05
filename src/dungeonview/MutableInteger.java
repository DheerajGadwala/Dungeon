package dungeonview;

class MutableInteger {
  private int value;

  MutableInteger(int value) {
    this.value = value;
  }

  void add(int val) {
    this.value += val;
  }

  int getValue() {
    return this.value;
  }
}
