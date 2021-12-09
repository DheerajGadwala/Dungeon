package dungeongeneral;

/**
 * This represents all possible weapons in the dungeon.
 */
public enum Weapon {
  BOW("bow", "bows"),
  SWORD("sword", "swords"),
  AXE("axe", "axes");

  private final String singular;
  private final String plural;

  Weapon(String singular, String plural) {
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

  /**
   * Damage that this weapon causes.
   * @return damage that this weapon causes.
   */
  public int getDamage() {
    if (this == BOW) {
      return 1;
    }
    else if (this == SWORD) {
      return 4;
    }
    else if (this == AXE) {
      return 8;
    }
    else {
      return 0;
    }
  }

  @Override
  public String toString() {
    return singular;
  }
}
