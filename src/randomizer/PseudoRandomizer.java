package randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * This can be used to get pseudo random numbers.
 * These numbers were fed to it previously.
 */
public class PseudoRandomizer implements Randomizer {

  private final int[] fakeRandom;
  private int pointer;
  private final List<Integer> history;

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
    this.history = new ArrayList<>();
  }

  @Override
  public int getIntBetween(int a, int b) {
    if (a > b) {
      throw new IllegalArgumentException("Invalid range of numbers.");
    }
    history.add(fakeRandom[pointer]);
    return fakeRandom[pointer++];
  }

  @Override
  public List<Integer> getHistory() {
    List<Integer> copy = new ArrayList<>();
    for (int k: history) {
      copy.add(k);
    }
    return copy;
  }
}
