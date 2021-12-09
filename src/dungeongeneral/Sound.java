package dungeongeneral;

/**
 * Represents the result of an arrow shot and what it implies.
 */
public enum Sound {
  DYING_HOWL("You hear a loud howl that is slowly fading away into silence."),
  HOWL("You hear a howl filled with agony."),
  HISS("You just hear the hiss of your arrow.");

  private final String implication;

  Sound(String implication) {
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
