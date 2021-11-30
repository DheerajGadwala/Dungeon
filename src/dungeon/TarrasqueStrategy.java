package dungeon;

class TarrasqueStrategy implements EntityStrategy {

  private final Entity tarrasque;
  private final Player player;

  TarrasqueStrategy(Entity tarrasque, Player player) {
    if (tarrasque == null) {
      throw new IllegalArgumentException("Entity can not be null");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player can not be null");
    }
    this.tarrasque = tarrasque;
    this.player = player;
  }

  @Override
  public void nextAction() {
    if (player.getPosition().equals(tarrasque.getPosition())) {
      tarrasque.harm(player);
    }
    else {

    }
  }
}
