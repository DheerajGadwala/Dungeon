package dungeon;

public interface LocationNodeMA extends LocationNode {

  /**
   * sets a monster at this location.
   * @param monster monster to be set
   * @throws IllegalArgumentException if monster is null.
   */
  void setMonster(Monster monster) throws IllegalArgumentException;

  /**
   * checks if location has a monster.
   * @return true if location has monster else false.
   */
  boolean hasMonster();

}
