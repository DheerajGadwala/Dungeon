import dungeon.DungeonGame;
import dungeoncontroller.DungeonGameController;

import java.io.InputStreamReader;

/**
 * This is the driver class with non deterministic dungeon generation.
 */
public class Driver {
  /**
   * Main method.
   * @param args command line args
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    new DungeonGameController(input, output).playGame(new DungeonGame(
        Integer.parseInt(args[0]),
        Integer.parseInt(args[1]),
        Integer.parseInt(args[2]),
        Integer.parseInt(args[3]),
        Boolean.parseBoolean(args[4]),
        Integer.parseInt(args[5])
    ));
  }
}
