package dungeoncontroller;

import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;
import static general.Item.BOW;
import static general.Item.CROOKED_ARROW;
import static general.Treasure.DIAMOND;
import static general.Treasure.RUBY;
import static general.Treasure.SAPPHIRE;

import dungeon.Game;
import general.Direction;
import general.Item;
import general.Odour;
import general.Treasure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Controller for the dungeon game.
 */
public class DungeonGameController implements GameController {

  private final Appendable out;
  private final Scanner scan;
  private final HashMap<String, Direction> directionMap;
  private final HashMap<String, Item> itemMap;
  private final HashMap<String, Treasure> treasureMap;

  /**
   * Constructor for the controller.
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonGameController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = new Scanner(in);
    this.directionMap = new HashMap<>();
    this.itemMap = new HashMap<>();
    this.treasureMap = new HashMap<>();
    this.generateInputMaps();
  }

  @Override
  public void playGame(Game game) {
    try {
      if (!game.isPlayerCreated()) {
        game.createPlayer();
      }
      while (!game.isGameOver()) {
        out.append(game.getPlayerLocationDescription());
        Odour smell = game.getSmellAtPlayerLocation();
        out.append(smell.getImplication());
        out.append("\n");
        out.append("What do you do?\nMove, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n");
        char x = getValidInput("[MSITmsit]").charAt(0);
        Direction direction;
        while (true) {
          try {
            switch (x) {
              case 'm':
              case 'M':
                out.append("Direction? [N E S W]\n");
                direction = directionMap.get(getValidInput("[NESWnesw]"));
                new Move(direction).execute(game);
                break;
              case 's':
              case 'S':
                out.append("Direction? [N E S W]\n");
                direction = directionMap.get(getValidInput("[NESWnesw]"));
                out.append("Distance?\n");
                int distance = Integer.parseInt(getValidInput("[0-9]+"));
                new Shoot(direction, distance, out).execute(game);
                break;
              case 'i':
              case 'I':
                out.append("What Item? [Arrow[a/A] Bow[b/B]]\n");
                Item item = itemMap.get(getValidInput("[AaBb]"));
                new PickUpItem(item).execute(game);
                break;
              case 't':
              case 'T':
                out.append("What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n");
                Treasure treasure = treasureMap.get(getValidInput("[RrDdSs]"));
                new PickUpTreasure(treasure).execute(game);
                break;
              default:
                break;
            }
            break;
          } catch (IllegalStateException ise) {
            out.append(ise.getMessage());
            out.append("\n");
            break;
          } catch (IllegalArgumentException iae) {
            out.append(iae.getMessage());
            out.append("\n");
          }
        }
        out.append("#########################################################\n");
      }
      if (game.hasPlayerWon()) {
        out.append("You have escaped from the dungeon! You lucky dog!\n");
      }
      else {
        out.append("The Otyugh is feeding on your body!\n");
      }
      out.append("Final player description:\n");
      out.append(game.getPlayerDescription());
    }
    catch (IOException ignored) {
    }
  }

  private String getValidInput(String pattern) throws IOException {
    while (true) {
      String ret = scan.nextLine();
      if (ret.matches(pattern)) {
        return ret;
      }
      else {
        out.append("Please enter a valid input.\n");
      }
    }
  }

  private void generateInputMaps() {
    directionMap.put("N", NORTH);
    directionMap.put("n", NORTH);
    directionMap.put("S", SOUTH);
    directionMap.put("s", SOUTH);
    directionMap.put("E", EAST);
    directionMap.put("e", EAST);
    directionMap.put("W", WEST);
    directionMap.put("w", WEST);
    itemMap.put("a", CROOKED_ARROW);
    itemMap.put("A", CROOKED_ARROW);
    itemMap.put("b", BOW);
    itemMap.put("B", BOW);
    treasureMap.put("r", RUBY);
    treasureMap.put("R", RUBY);
    treasureMap.put("d", DIAMOND);
    treasureMap.put("D", DIAMOND);
    treasureMap.put("s", SAPPHIRE);
    treasureMap.put("S", SAPPHIRE);
  }

}