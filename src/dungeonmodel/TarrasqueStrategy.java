package dungeonmodel;

import dungeongeneral.Direction;
import randomizer.Randomizer;

import java.util.List;

class TarrasqueStrategy implements EntityStrategy {

  private final Entity tarrasque;
  private final Player player;
  private final Randomizer randomizer;
  private boolean hasMetPlayer;

  TarrasqueStrategy(Entity tarrasque, Player player, Randomizer randomizer) {
    if (tarrasque == null) {
      throw new IllegalArgumentException("Entity can not be null");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player can not be null");
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer can not be null.");
    }
    this.tarrasque = tarrasque;
    this.player = player;
    this.randomizer = randomizer;
    this.hasMetPlayer = false;
  }

  @Override
  public void nextAction() {
    if (player.getPosition().equals(tarrasque.getPosition())) {
      hasMetPlayer = true;
      tarrasque.harm(player);
    }
    else if (hasMetPlayer) {
      Direction direction = tarrasque.getLocation().getNeighbourDirection(player.getLocation());
      tarrasque.move(direction);
    }
    else {
      List<Direction> possibleMoves = tarrasque.getPossibleRoutes();
      int random = randomizer.getIntBetween(0, possibleMoves.size());
      if (random != possibleMoves.size()) {
        tarrasque.move(possibleMoves.get(random));
      }
    }
  }
}
