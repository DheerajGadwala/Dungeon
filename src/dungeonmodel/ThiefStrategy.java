package dungeonmodel;

import dungeongeneral.Direction;
import randomizer.Randomizer;

import java.util.List;

class ThiefStrategy implements EntityStrategy {

  private final Entity thief;
  private final Player player;
  private Randomizer randomizer;

  ThiefStrategy(Entity thief, Player player, Randomizer randomizer) {
    if (thief == null) {
      throw new IllegalArgumentException("thief can not be null.");
    }
    else if (player == null) {
      throw new IllegalArgumentException("player can not be null.");
    }
    else if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer can not be null");
    }
    this.thief = thief;
    this.player = player;
    this.randomizer = randomizer;
  }

  @Override
  public void nextAction() {
    if (thief.getCoordinates().equals(player.getCoordinates())) {
      thief.harm(player);
    }
    else {
      List<Direction> possibleMoves = thief.getPossibleRoutes();
      int random = randomizer.getIntBetween(0, possibleMoves.size());
      if (random != possibleMoves.size()) {
        thief.move(possibleMoves.get(random));
      }
      if (thief.getCoordinates().equals(player.getCoordinates())) {
        thief.harm(player);
      }
    }
  }

  @Override
  public void setRandomizer(Randomizer randomizer) {
    if (randomizer == null) {
      throw new IllegalArgumentException("randomizer can not be null");
    }
    this.randomizer = randomizer;
  }
}
