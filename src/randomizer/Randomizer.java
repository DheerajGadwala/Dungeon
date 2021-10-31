package randomizer;

/**
 * This can be used to generate random or pseudo random numbers for objects which need them.
 * This can generate numbers in a range.
 */
public interface Randomizer {

  /**
   * Generates a random integer between a and b [inclusive].
   * @param a left boundary
   * @param b right boundary
   * @return an integer
   * @throws IllegalArgumentException when a > b.
   */
  int getIntBetween(int a, int b) throws IllegalArgumentException;
}
