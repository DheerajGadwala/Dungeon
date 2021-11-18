package general;

/**
 * Represents various odours in the dungeon and the implication when a player senses them.
 */
public enum Odour {
  ODOURLESS("smells like there aren't any otyughs near by."),
  LESS_PUNGENT("you sense a slightly pungent smell of otyughs."),
  MORE_PUNGENT("you sense a very pungent smell of otyughs, be careful!");

  private final String implication;

  Odour(String implication) {
    this.implication = implication;
  }

  public String getImplication() {
    return this.implication;
  }

}
