package dungeoncontroller;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Item.POTION;
import static dungeongeneral.Treasure.DIAMOND;
import static dungeongeneral.Treasure.RUBY;
import static dungeongeneral.Treasure.SAPPHIRE;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Treasure;
import dungeonmodel.Game;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Controller for the dungeon game.
 */
public class DungeonControllerTextBasedGame implements GameController {

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
  public DungeonControllerTextBasedGame(Readable in, Appendable out) {
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
      while (!game.isGameOver()) {
        out.append(game.getLocationDesc().toString());
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
                new Move(direction, out).execute(game);
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
                out.append("What Item? [Arrow[a/A] Potion[p/P]]\n");
                Item item = itemMap.get(getValidInput("[AaPp]"));
                new PickUpItem(item, out).execute(game);
                break;
              case 't':
              case 'T':
                out.append("What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n");
                Treasure treasure = treasureMap.get(getValidInput("[RrDdSs]"));
                new PickUpTreasure(treasure, out).execute(game);
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
        out.append("##################################################################\n");
      }
      if (game.hasPlayerWon()) {
        out.append("You have escaped from the dungeon! You lucky dog!\n");
      } else {
        out.append("The Otyugh is feeding on your body!\n");
      }
      out.append("Final player description:\n");
      out.append(game.getPlayerDesc().toString());
    } catch (IOException ioe) {
      throw new IllegalStateException("IO problem.");
    }
    catch (NoSuchElementException ignored) {
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
    itemMap.put("p", POTION);
    itemMap.put("P", POTION);
    treasureMap.put("r", RUBY);
    treasureMap.put("R", RUBY);
    treasureMap.put("d", DIAMOND);
    treasureMap.put("D", DIAMOND);
    treasureMap.put("s", SAPPHIRE);
    treasureMap.put("S", SAPPHIRE);
  }

}
