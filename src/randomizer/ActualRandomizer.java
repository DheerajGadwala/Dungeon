package randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This can be used to return random numbers.
 */
public class ActualRandomizer implements Randomizer {

  private final Random rand = new Random();
  private final List<Integer> history;

  /**
   * Creates a randomizer object.
   */
  public ActualRandomizer() {
    this.history = new ArrayList<>();
  }

  @Override
  public int getIntBetween(int a, int b) throws IllegalArgumentException {
    if (a > b) {
      throw new IllegalArgumentException("invalid range of numbers.");
    }
    int random = rand.nextInt(b - a + 1) + a;
    history.add(random);
    return random;
  }

  @Override
  public List<Integer> getHistory() {
    return new ArrayList<>(history);
  }
}
