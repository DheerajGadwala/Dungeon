import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;

import dungeon.DungeonControl;
import dungeon.DungeonControlImpl;

import java.util.Scanner;

/**
 * This is the driver class.
 */
public class Driver {
  /**
   * Main method.
   * @param args command line args/
   */
  public static void main(String[] args) {
    DungeonControl sample;
    sample = new DungeonControlImpl(
        Integer.parseInt(args[0]),
        Integer.parseInt(args[1]),
        Boolean.parseBoolean(args[2]),
        Integer.parseInt(args[3])
    );
    sample.createPlayer("Dheeraj");
    Scanner inp = new Scanner(System.in);
    while (true) {
      System.out.println("Possible moves:");
      System.out.println(sample.getPossibleMoves());
      System.out.println("What's your next move? [N,n/ E,e/ S,s/ W,w]");
      char x = inp.next().charAt(0);
      String ret = switch (x) {
        case 'n', 'N' -> sample.movePlayer(NORTH);
        case 'e', 'E' -> sample.movePlayer(EAST);
        case 's', 'S' -> sample.movePlayer(SOUTH);
        case 'w', 'W' -> sample.movePlayer(WEST);
        default -> "invalid input.";
      };
      ;
      System.out.println(ret);
    }

  }
}
