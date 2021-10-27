package randomizer;

public class PseudoRandomizer implements Randomizer {

  int[] fakeRandom;
  int pointer;

  /**
   * Creates a PseudoRandomizer object.
   * @param random numbers to be returned.
   */
  public PseudoRandomizer(int... random) {
    this.fakeRandom = random;
    this.pointer = 0;
  }

  @Override
  public int getIntBetween(int a, int b) {
    return fakeRandom[pointer++];
  }
}
