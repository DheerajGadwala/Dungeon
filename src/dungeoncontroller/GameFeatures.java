package dungeoncontroller;

public interface GameFeatures {

  /**
   * Generates a dungeon with given arguments.
   * @param rows number of rows in the dungeon.
   * @param columns number of columns in the dungeon.
   * @param percentage percentage input for the dungeon.
   * @param difficulty difficulty level.
   * @param enableWrap wrap enables dungeon or not.
   * @param interconnectivity interconnectivity of the dungeon.
   * @throws IllegalArgumentException when inputs are not valid.
   */
  void generateDungeon(
          int rows, int columns, int percentage,
          int difficulty, boolean enableWrap,
          int interconnectivity) throws IllegalArgumentException;


}
