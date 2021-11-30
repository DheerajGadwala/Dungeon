package dungeon;

import dungeongeneral.Coordinate;
import randomizer.ActualRandomizer;
import randomizer.PseudoRandomizer;
import randomizer.Randomizer;

import java.util.List;

public class DungeonGameWithObstacles extends DungeonGame implements GameWithObstacles{

  private final Entity movingMonster;
  private final Entity thief;
  private LocationNode monsterStart;
  private LocationNode thiefStart;


  private DungeonGameWithObstacles(int row, int column, int percentage, int numberOfMonsters, boolean enableWrap, int interconnectivity, Randomizer randomizer) throws IllegalArgumentException {
    super(row, column, percentage, numberOfMonsters, enableWrap, interconnectivity, randomizer);
    this.monsterStart = pickRandomLocation();
    this.movingMonster = new Tarrasque(monsterStart, this.randomizer);
    this.thiefStart = pickRandomLocation();
    this.thief = new Thief(this.thiefStart);
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
    if (player.getPosition() == movingMonster.getPosition()) {
      player.attack(movingMonster);
    }
    else {
      throw new IllegalStateException("Tarrasque not at player location.");
    }
  }
}
