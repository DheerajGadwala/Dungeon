import dungeoncontroller.DungeonControllerForGUI;
import dungeonmodel.DungeonGame;
import dungeonmodel.Game;
import dungeoncontroller.DungeonControllerTextBasedGame;

import java.io.InputStreamReader;

/**
 * This is the driver class with non deterministic dungeon generation.
 */
public class TextBasedGameDriver {

  /**
   * Main method.
   * @param args command line args
   */
  public static void main(String[] args) {
    if(args.length==0) {
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      Game game = new DungeonGame(
              Integer.parseInt(args[0]),
              Integer.parseInt(args[1]),
              Integer.parseInt(args[2]),
              Integer.parseInt(args[3]),
              Boolean.parseBoolean(args[4]),
              Integer.parseInt(args[5])
      );
      new DungeonControllerTextBasedGame(input, output).playGame(game);
    }
    else {
      new DungeonControllerForGUI();
    }

  }
}
