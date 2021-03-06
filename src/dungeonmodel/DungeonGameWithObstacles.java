package dungeonmodel;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.Sound;
import dungeongeneral.Treasure;
import randomizer.ActualRandomizer;
import randomizer.PseudoRandomizer;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a dungeon game with additional obstacles.
 * This game contains a Moving monster.
 * This game contains a thief.
 * This game's locations have pits.
 * The moving monster and the thief have their own strategies.
 */
public class DungeonGameWithObstacles extends DungeonGame implements GameWithObstacles {

  private final Entity tarrasque;
  private final Entity thief;
  private final EntityStrategy tarrasqueStrategy;
  private final EntityStrategy thiefStrategy;
  private final List<Integer> history;
  private final int percentage;
  private final int difficulty;
  private final boolean enableWrap;
  private final int interconnectivity;

  private DungeonGameWithObstacles(
          int row, int column, int percentage,
          int difficulty, boolean enableWrap,
          int interconnectivity, Randomizer randomizer
  ) throws IllegalArgumentException {
    super(row, column, percentage, difficulty, enableWrap, interconnectivity, randomizer);
    this.percentage = percentage;
    this.difficulty = difficulty;
    this.enableWrap = enableWrap;
    this.interconnectivity = interconnectivity;
    this.tarrasque = new Tarrasque(pickRandomLocation(), this.randomizer);
    this.thief = new Thief(pickRandomLocation());
    this.tarrasqueStrategy = new TarrasqueStrategy(tarrasque, player, randomizer);
    this.thiefStrategy = new ThiefStrategy(thief, player, randomizer);
    generatePits(difficulty);
    this.history = randomizer.getHistory();
  }

  /**
   * Constructor for game reset.
   */
  public DungeonGameWithObstacles(
          int rowCount, int columnCount, int percentage,
          int difficulty, boolean enableWrap, int interconnectivity,
          List<Integer> history) throws IllegalArgumentException {
    this(
            rowCount, columnCount, percentage,
            difficulty, enableWrap, interconnectivity,
            history.stream().mapToInt(i -> i).toArray()
    );
    this.randomizer = new ActualRandomizer();
    dungeon.setRandomizer(randomizer);
    player.setRandomizer(randomizer);
    tarrasque.setRandomizer(randomizer);
    tarrasqueStrategy.setRandomizer(randomizer);
    thiefStrategy.setRandomizer(randomizer);
  }

  private LocationNode pickRandomLocation() {
    List<Coordinate> allPositions = getAllPositions();
    allPositions.remove(start.getCoordinates());
    return dungeon.getLocation(
            allPositions.get(
                    randomizer.getIntBetween(0, allPositions.size() - 1)
            )
    );
  }

  private void generatePits(int difficulty) {
    difficulty = difficulty / 2;
    List<Coordinate> allPositions = getAllPositions();
    allPositions.remove(start.getCoordinates());
    while (difficulty > 0) {
      int x = randomizer.getIntBetween(0, allPositions.size() - 1);
      dungeon.getLocation(allPositions.remove(x)).createPit();
      difficulty--;
    }
  }

  /**
   * Constructor for this type of game. [Random]
   * @param row dimension of the dungeon
   * @param column dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param interconnectivity interconnectivity of the dungeon
   * @throws IllegalArgumentException when given row and column lead to invalid dungeon generation
   *                                  or when interconnectivity is too high or when number of
   *                                  monsters is not at least one.
   */
  public DungeonGameWithObstacles(
          int row, int column, int percentage,
          int difficulty, boolean enableWrap,
          int interconnectivity
  ) throws IllegalArgumentException {
    this(row, column, percentage, difficulty, enableWrap,
            interconnectivity, new ActualRandomizer()
    );
  }

  /**
   * Constructor for this type of game. [Pseudo Random]
   * @param row dimension of the dungeon
   * @param column dimension of the dungeon
   * @param enableWrap tells if the dungeon should be wrapped or not
   * @param difficulty decides the number of monsters and pits.
   * @param interconnectivity interconnectivity of the dungeon
   * @param random numbers to be used in the generation of this dungeon.
   * @throws IllegalArgumentException when given row and column lead to invalid dungeon generation
   *                                  or when interconnectivity is too high or when random is null
   *                                  or when number of monster is not at least one.
   */
  public DungeonGameWithObstacles(
          int row, int column, int percentage,
          int difficulty, boolean enableWrap,
          int interconnectivity, int... random
  ) throws IllegalArgumentException {
    this(row, column, percentage, difficulty, enableWrap,
            interconnectivity, new PseudoRandomizer(random)
    );
  }

  @Override
  public List<Integer> getGenerationSequence() {
    return new ArrayList<Integer>(history);
  }

  @Override
  public void attack() {
    validateGameOver();
    if (player.getCoordinates() == tarrasque.getCoordinates()) {
      player.attack(tarrasque);
      tarrasqueStrategy.nextAction();
      thiefStrategy.nextAction();
      updateGameOverStatus();
    }
    else {
      throw new IllegalStateException("Tarrasque not at player location.");
    }
  }

  @Override
  public void move(Direction direction)
          throws IllegalStateException, IllegalArgumentException {
    super.move(direction);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
    updateGameOverStatus();
  }

  @Override
  public Sound shoot(Direction direction, int distance)
          throws IllegalArgumentException, IllegalStateException {
    final Sound res = super.shoot(direction, distance);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
    updateGameOverStatus();
    return res;
  }

  @Override
  public void cedeTreasure(Treasure treasure)
          throws IllegalStateException, IllegalArgumentException {
    super.cedeTreasure(treasure);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
    updateGameOverStatus();
  }

  @Override
  public void cedeItem(Item item)
          throws IllegalStateException, IllegalArgumentException {
    super.cedeItem(item);
    tarrasqueStrategy.nextAction();
    thiefStrategy.nextAction();
    updateGameOverStatus();
  }

  @Override
  public ReadOnlyLocation getLocation(Coordinate coordinate)
          throws IllegalArgumentException {
    return dungeon.getLocation(coordinate);
  }

  @Override
  public void moveToLocation(ReadOnlyLocation location)
          throws IllegalArgumentException, IllegalStateException {
    validateGameOver();
    if (location == null) {
      throw new IllegalArgumentException("Location can not be null");
    }
    LocationNode thatLocation = (LocationNode) location;
    if (player.getLocation().hasNeighbour(thatLocation)) {
      Direction direction = player.getLocation().getNeighbourDirection(thatLocation);
      move(direction);
    }
    else {
      throw new IllegalArgumentException("Location is not a neighbour.");
    }
  }

  @Override
  public int getRowCount() {
    return row;
  }

  @Override
  public int getColumnCount() {
    return column;
  }

  @Override
  public int getPercentage() {
    return percentage;
  }

  @Override
  public int getDifficulty() {
    return difficulty;
  }

  @Override
  public boolean getEnableWrap() {
    return enableWrap;
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  @Override
  public boolean thiefAtPlayerLocation() {
    return thief.getCoordinates().equals(player.getCoordinates());
  }

  @Override
  public boolean movingMonsterAliveAtPlayerLocation() {
    return tarrasque.getCoordinates().equals(player.getCoordinates()) && tarrasque.isAlive();
  }

  @Override
  public String toString() {
    return  "Rows: " + getRowCount()
            + "\nColumns: " + getColumnCount()
            + "\nPercentage: " + getPercentage()
            + "\nDifficulty: " + getDifficulty()
            + "\nWrap: " + getEnableWrap()
            + "\nInterconnectivity: " + getInterconnectivity();
  }
}
