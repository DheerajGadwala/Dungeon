package dungeongeneral;

/**
 * Represents result of an arrow shot and what it implies.
 */
public enum ShotResult {
  KILL("You hear a loud howl that is slowly fading away into silence."),
  HIT("You hear a howl filled with agony."),
  MISS("You just hear the hiss of your arrow.");

  private final String implication;

  ShotResult(String implication) {
    this.implication = implication;
  }

  /**
   * Returns implication of the shot result.
   * @return implication.
   */
  public String getImplication() {
    return this.implication;
  }
}
