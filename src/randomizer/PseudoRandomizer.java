package randomizer;

/**
 * This can be used to get pseudo random numbers.
 * These numbers were fed to it previously.
 */
public class PseudoRandomizer implements Randomizer {

  int[] fakeRandom;
  int pointer;

  /**
   * Creates a PseudoRandomizer object.
   * @param random numbers to be returned.
   * @throws IllegalArgumentException when array of numbers is null.
   */
  public PseudoRandomizer(int... random) {
    if (random == null) {
      throw new IllegalArgumentException("Invalid random numbers");
    }
    this.fakeRandom = random;
    this.pointer = 0;
  }

  @Override
  public int getIntBetween(int a, int b) {
    if (a > b) {
      throw new IllegalArgumentException("Invalid range of numbers.");
    }
    System.out.println(fakeRandom[pointer]);
    return fakeRandom[pointer++];
  }
}
