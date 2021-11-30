package dungeon;

import dungeongeneral.*;
import randomizer.ActualRandomizer;
import randomizer.PseudoRandomizer;
import randomizer.Randomizer;

import java.util.List;

public class DungeonGameWithObstacles extends DungeonGame implements GameWithObstacles{

  private final Entity tarrasque;
  private final Entity thief;
  private final TarrasqueStrategy tarrasqueStrategy;
  private final ThiefStrategy thiefStrategy;

  private DungeonGameWithObstacles(int row, int column, int percentage, int numberOfMonsters, boolean enableWrap, int interconnectivity, Randomizer randomizer) throws IllegalArgumentException {
    super(row, column, percentage, numberOfMonsters, enableWrap, interconnectivity, randomizer);
    this.tarrasque = new Tarrasque(pickRandomLocation(), this.randomizer);
    this.thief = new Thief(pickRandomLocation());
    this.tarrasqueStrategy = new TarrasqueStrategy(tarrasque, player, randomizer);
    this.thiefStrategy = new ThiefStrategy(thief, player, randomizer);

  }

  public DungeonGameWithObstacles(
          int row, int column, int percentage,
          int numberOfMonsters, boolean enableWrap,
          int interconnectivity
  ) throws IllegalArgumentException {
    this(row, column, percentage, numberOfMonsters, enableWrap,
            interconnectivity, new ActualRandomizer()
    );
  }

  public DungeonGameWithObstacles(
          int row, int column, int percentage,
          int numberOfMonsters, boolean enableWrap,
          int interconnectivity, int... random
  ) throws IllegalArgumentException {
    this(row, column, percentage, numberOfMonsters, enableWrap,
            interconnectivity, new PseudoRandomizer(random)
    );
  }

  private LocationNode pickRandomLocation() {
    List<Coordinate> allPositions = getAllPositions();
    allPositions.remove(start.getCoordinates());
    return dungeon.getLocation(
            allPositions.get(
                    randomizer.getIntBetween(0, allPositions.size())
            )
    );
  }

  @Override
  public void attack() {
    validateGameOver();
    if (player.getPosition() == tarrasque.getPosition()) {
      player.attack(tarrasque);
    }
    else {
      throw new IllegalStateException("Tarrasque not at player location.");
    }
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
  }

  @Override
  public void move(Direction direction)
          throws IllegalStateException, IllegalArgumentException {
    super.move(direction);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
  }

  @Override
  public ShotResult shoot(Direction direction, int distance)
          throws IllegalArgumentException, IllegalStateException {
    ShotResult result = super.shoot(direction, distance);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
    return result;
  }

  @Override
  public void cedeTreasure(Treasure treasure)
          throws IllegalStateException, IllegalArgumentException {
    super.cedeTreasure(treasure);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
  }

  @Override
  public void cedeItem(Item item)
          throws IllegalStateException, IllegalArgumentException {
    super.cedeItem(item);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
  }

}
