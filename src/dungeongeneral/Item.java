package dungeongeneral;

/**
 * Represents an item in the game.
 */
public enum Item {
  CROOKED_ARROW("crooked arrow", "crooked arrows"),
  POTION("potion", "potions");

  private final String singular;
  private final String plural;

  Item(String singular, String plural) {
    this.singular = singular;
    this.plural = plural;
  }

  /**
   * return singular form of this.
   * @return singular form of this.
   */
  public String getSingular() {
    return singular;
  }

  /**
   * returns plural form of this.
   * @return plural form of this.
   */
  public String getPlural() {
    return plural;
  }

  @Override
  public String toString() {
    return singular;
  }
}
