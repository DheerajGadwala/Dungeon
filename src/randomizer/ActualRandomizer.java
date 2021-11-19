package randomizer;

import java.util.Random;

/**
 * This can be used to return random numbers.
 */
public class ActualRandomizer implements Randomizer {

  Random rand = new Random();

  @Override
  public int getIntBetween(int a, int b) throws IllegalArgumentException {
    if (a > b) {
      throw new IllegalArgumentException("invalid range of numbers.");
    }
    int random = rand.nextInt(b - a + 1) + a;
    System.out.println(random);
    return random;
  }
}
