import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;

import dungeon.DungeonGame;
import dungeon.Game;

import java.util.Scanner;

/**
 * This is the driver class with non deterministic dungeon generation.
 * We show player location and description at every turn.
 */
public class Driver3 {

  /**
   * Main method.
   * @param args command line args/
   */
  public static void main(String[] args) {
    Game game;
    game = new DungeonGame(
        Integer.parseInt(args[0]),
        Integer.parseInt(args[1]),
        Boolean.parseBoolean(args[2]),
        Integer.parseInt(args[3])
    );
    game.createPlayer("Dheeraj");
    game.generateTreasure(Integer.parseInt(args[4]));
    Scanner inp = new Scanner(System.in);
    StringBuilder ret = new StringBuilder(game.gameStatus());
    do {
      System.out.println(game.displayMap());
      System.out.println(ret);
      System.out.println("\nPlayer Description:\n" + game.getPlayerDescription());
      System.out.println("Player Location Description:" + game.getPlayerLocationDescription());
      System.out.println("Possible moves:");
      System.out.println(game.getPossibleMoves().toString());
      System.out.println(
          (game.playerLocationHasTreasure() ? "[Collect Treasure(c/C)]\n" : "")
              + "What's your next move? [N,n/ E,e/ S,s/ W,w/ C,c]"
      );
      char x = inp.next().charAt(0);
      ret = new StringBuilder();
      switch (x) {
        case 'n':
        case 'N':
          ret.append(game.movePlayer(NORTH));
          break;
        case 'e':
        case 'E':
          ret.append(game.movePlayer(EAST));
          break;
        case 's':
        case 'S':
          ret.append(game.movePlayer(SOUTH));
          break;
        case 'w':
        case 'W':
          ret.append(game.movePlayer(WEST));
          break;
        case 'c':
        case 'C':
          ret.append(game.cedeTreasure());
          break;
        default:
          ret.append("invalid input.");
      }
    }
    while (!game.isGameOver());
    System.out.println(game.displayMap());
    System.out.println(game.gameStatus());
  }
}
