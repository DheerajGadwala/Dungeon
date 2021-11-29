package dungeon;

public class Thief implements Entity {
  public Thief(LocationNode location) {
  }

  @Override
  public boolean isAlive() {
    return true;
  }

  @Override
  public boolean isInjured() {
    return false;
  }

  @Override
  public int getHealth() {
    return 0;
  }

  /**
   * Robs the player's treasure.
   * @param player player this entity will try to harm.
   */
  @Override
  public void harm(Player player) {
    player.getMugged();
  }

  @Override
  public void decreaseHealth(int damage) {

  }
}
