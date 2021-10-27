package randomizer;

import java.util.Random;

public class ActualRandomizer implements Randomizer {

  Random rand = new Random();

  @Override
  public int getIntBetween(int a, int b) {
    return rand.nextInt(b - a + 1) + a;
  }
}
