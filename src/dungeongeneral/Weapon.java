package dungeongeneral;

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
