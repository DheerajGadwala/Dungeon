package dungeongeneral;

/**
 * Represents all types of treasures.
 */
public enum Treasure {
  DIAMOND("diamond", "diamonds"), RUBY("ruby", "rubies"), SAPPHIRE("sapphire", "sapphires");

  private final String singular;
  private final String plural;

  Treasure(String singular, String plural) {
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
    return this.singular;
  }
}
