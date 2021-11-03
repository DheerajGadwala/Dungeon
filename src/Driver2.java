import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;

import dungeon.DungeonGame;
import dungeon.Game;

import java.util.Scanner;

/**
 * This is the driver class with deterministic dungeon generation.
 */
public class Driver2 {

  /**
   * Main method.
   * @param args command line args/
   */
  public static void main(String[] args) {
    Game game;
    game = new DungeonGame(5, 5, true, 4,
        40,60,27,38,24,50,16,52,11,78,38,29,51,9,47,8,
        50,23,57,44,15,22,20,46,3,31,46,8,23,0,0,16,2,11,15,17,
        11,3,8,3,1,3,0,3,3,2,6,2,2,3,3,3,1,2,3,3,3,3,3,1,3,2
        );
    game.createPlayer("Dheeraj");
    game.generateTreasure(70);
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
    System.out.println(game.gameStatus());
  }
}
