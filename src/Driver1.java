import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;

import dungeon.DungeonGame;
import dungeon.Game;

import java.util.Scanner;

/**
 * This is the driver class with non deterministic dungeon generation.
 */
public class Driver1 {
  /**
   * Main method.
   * @param args command line args
   */
  public static void main(String[] args) {
    Game game;
    game = new DungeonGame(
        Integer.parseInt(args[0]),
        Integer.parseInt(args[1]),
        Integer.parseInt(args[4]),
        Boolean.parseBoolean(args[2]),
        Integer.parseInt(args[3])
    );
    game.createPlayer("Dheeraj");
    Scanner inp = new Scanner(System.in);
    String ret = game.gameStatus();
    do {
      System.out.println(game.displayMap());
      System.out.println(ret);
      System.out.println("Possible moves:");
      System.out.println(game.getPossibleMoves().toString());
      System.out.println(
          (game.playerLocationHasTreasure() ? "[Collect Treasure(c/C)]\n" : "")
          + "[Get player Description(p/P)]\n"
          + "[Get player location Description(l/L)]\n"
          + "[Get player treasure Description(t/T)]\n"
          + "What's your next move? [N,n/ E,e/ S,s/ W,w/ C,c/ p,P/ t,T/ l,L]"
      );
      char x = inp.next().charAt(0);
      ret = "";
      switch (x) {
        case 'n':
        case 'N':
          ret += game.movePlayer(NORTH);
          break;
        case 'e':
        case 'E':
          ret += game.movePlayer(EAST);
          break;
        case 's':
        case 'S':
          ret += game.movePlayer(SOUTH);
          break;
        case 'w':
        case 'W':
          ret += game.movePlayer(WEST);
          break;
        case 'c':
        case 'C':
          ret += game.cedeTreasure();
          break;
        case 'p':
        case 'P':
          ret += game.getPlayerDescription();
          break;
        case 'l':
        case 'L':
          ret += game.getPlayerLocationDescription();
          break;
        case 't':
        case 'T':
          ret += game.getPlayerTreasureDescription();
          break;
        default:
          ret += "invalid input.";
      }
      System.out.println("\n#####################################################\n");
    }
    while (!game.isGameOver());
    System.out.println(game.displayMap());
    System.out.println("Final Player Description: \n" + game.getPlayerDescription());
    System.out.println(game.gameStatus());
  }
}
