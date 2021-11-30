package model;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Item.CROOKED_ARROW;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import dungeon.DungeonGame;
import dungeon.Game;
import dungeon.ReadOnlyLocation;
import dungeongeneral.Coordinate;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * To test long traversals of the model.
 */
public class GameTestsTraversing {
  
  /**
   * Test that only the given percentage of caves get treasure.
   * Total number of caves that get the treasure should be equal to
   * the total number of caves multiplied by the given percentage.
   * We traverse through all nodes of a dungeon and find the number of caves with
   * treasure. Then assert that it is as expected.
   */
  @Test
  public void testTreasurePercentage() {
    int percent = 60;
    Game game = new DungeonGame(4, 4, percent, 1, false, 0,
            4,34,14,38,14,26,13,10,17,17,22,4,3,16,4,12,15,2,0,3,15,3,3,3,2,1,7,3,1,
            3,5,3,1,2,3,2,1,1,7,1,2,2,2,3,1,1,2,3,3,1,3,3,1,3,6,5,9,5,10,2,5,1,5,4,6,4,5,5
    );

    //        "                                        \n"
    //      + "  ****cT -- ***It* -- ****t*    ***Ic*  \n"
    //      + "                        ||        ||    \n"
    //      + "                        ||        ||    \n"
    //      + "  ****cT -- ****cT -- ****cT -- ***Ic*  \n"
    //      + "              ||                  ||    \n"
    //      + "              ||                  ||    \n"
    //      + "  ***IcT -- ***IcT    ***It* -- ***Ic*  \n"
    //      + "              ||        ||        ||    \n"
    //      + "              ||        ||        ||    \n"
    //      + "  EM*Ic* -- ****t*    ***IcT    S*P*c*  \n"
    //      + "                                        \n",

    int treasureCount = 0;
    int caveCount = 0;
    Set<Coordinate> visited = new HashSet<>();
    ReadOnlyLocation
            desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      if (desc.isCave()) {
        caveCount++;
      }
      if (desc.hasTreasure()) {
        treasureCount++;
      }
    }
    visited.add(desc.getCoordinates());
    //Hence all nodes are visited
    assertEquals("[(0, 2), (1, 1), (2, 0), (0, 3), (1, 2), (2, 1), (3, 0), (2, 2), (1, 3),"
            + " (3, 1), (2, 3), (3, 2), (3, 3), (0, 0), (0, 1), (1, 0)]", visited.toString());
    // Expected number of caves have treasure
    assertEquals(
            (int) (caveCount * percent / 100.0),
            treasureCount, 1);
  }

  /**
   * Test that only the given percentage of caves get treasure.
   * Total number of caves that get the treasure should be equal to
   * the total number of caves multiplied by the given percentage.
   * We traverse through all nodes of a dungeon and find the number of locations with
   * arrows. Then assert that it is as expected.
   */
  @Test
  public void testArrowPercentage() {
    int percent = 60;
    Game game = new DungeonGame(4, 4, percent, 1, false, 0,
            4,34,14,38,14,26,13,10,17,17,22,4,3,16,4,12,15,2,0,3,15,3,3,3,2,1,7,3,1,
            3,5,3,1,2,3,2,1,1,7,1,2,2,2,3,1,1,2,3,3,1,3,3,1,3,6,5,9,5,10,2,5,1,5,4,6,4,5,5
    );

    //        "                                        \n"
    //      + "  ****cT -- ***It* -- ****t*    ***Ic*  \n"
    //      + "                        ||        ||    \n"
    //      + "                        ||        ||    \n"
    //      + "  ****cT -- ****cT -- ****cT -- ***Ic*  \n"
    //      + "              ||                  ||    \n"
    //      + "              ||                  ||    \n"
    //      + "  ***IcT -- ***IcT    ***It* -- ***Ic*  \n"
    //      + "              ||        ||        ||    \n"
    //      + "              ||        ||        ||    \n"
    //      + "  EM*Ic* -- ****t*    ***IcT    S*P*c*  \n"
    //      + "                                        \n",

    int arrowCount = 0;
    int locationCount = 0;
    Set<Coordinate> visited = new HashSet<>();
    ReadOnlyLocation
            desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (desc.hasItems()) {
        arrowCount++;
      }
    }
    visited.add(desc.getCoordinates());
    //Hence all nodes are visited
    assertEquals("[(0, 2), (1, 1), (2, 0), (0, 3), (1, 2), (2, 1), (3, 0), (2, 2), (1, 3),"
            + " (3, 1), (2, 3), (3, 2), (3, 3), (0, 0), (0, 1), (1, 0)]", visited.toString());
    // Expected number of caves have treasure
    assertEquals(
            (int) (locationCount * percent / 100.0),
            arrowCount, 1);
  }

  /**
   * Test that exactly n monsters are created in the dungeon.
   * Test by traversing a deterministic dungeon.
   * We also check that no monster is in a tunnel.
   */
  @Test
  public void nMonsters() {
    int numberOfMonsters = 6;
    Game game = new DungeonGame(4, 4, 100, numberOfMonsters, false, 2,
            26,18,1,5,27,37,29,33,21,14,15,22,19,0,18,3,2,13,10,11,8,2,8,6,8,7,9,1,7,3,2,
            1,1,3,1,3,3,3,3,1,1,3,2,1,0,3,3,2,1,2,3,3,1,1,2,1,0,1,3,1,5,1,10,2,9,3,9,5,4,4,8,
            3,1,1,5,5,2,5,3,5,2,4,1,2,1,1,1,1,0,4,0,5,5,3,0,0,1);
    game.cedeItem(CROOKED_ARROW);
    Set<Coordinate> visited = new HashSet<>();
    int monsterCount = 0;
    int locationCount = 0;
    int monsterInTunnelCount = 0;
    ReadOnlyLocation desc;
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.cedeItem(CROOKED_ARROW);
    game.cedeItem(CROOKED_ARROW);
    game.cedeItem(CROOKED_ARROW);
    game.cedeItem(CROOKED_ARROW);
    game.cedeItem(CROOKED_ARROW);
    game.shoot(NORTH, 1);
    game.shoot(NORTH, 1);
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.shoot(NORTH, 1);
    game.shoot(NORTH, 1);
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.shoot(SOUTH, 1);
    game.shoot(SOUTH, 1);
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.shoot(NORTH, 1);
    game.shoot(NORTH, 1);
    game.shoot(NORTH, 2);
    game.cedeItem(CROOKED_ARROW);
    game.shoot(NORTH, 2);
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.cedeItem(CROOKED_ARROW);
    game.move(WEST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.cedeItem(CROOKED_ARROW);
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(SOUTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.shoot(EAST, 1);
    game.shoot(EAST, 1);
    game.move(EAST);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    game.move(NORTH);
    desc = game.getLocationDesc();
    if (!visited.contains(desc.getCoordinates())) {
      locationCount++;
      if (!desc.hasNoMonster()) {
        monsterCount++;
        if (desc.isTunnel()) {
          monsterInTunnelCount++;
        }
      }
    }
    visited.add(desc.getCoordinates());
    visited.add(desc.getCoordinates());
    // We visited all locations
    assertEquals(16, locationCount);
    // Hence all nodes are visited
    assertEquals(
            "[(2, 0), (1, 1), (0, 2), (2, 1), (3, 0), (1, 2), (0, 3), (2, 2),"
                    + " (3, 1), (1, 3), (3, 2), (2, 3), (3, 3), (0, 0), (1, 0), (0, 1)]",
            visited.toString());
    // Expected number of caves have treasure
    assertEquals(numberOfMonsters, monsterCount);
    // Assert that no monster was found in a tunnel.
    assertEquals(0, monsterInTunnelCount);
    // Assert Player description because it has stats.
    assertEquals("Player info:\n"
            + "Misses: 0\n"
            + "Hits: 12\n"
            + "Kills: 6\n"
            + "Treasure:\n"
            + "None\n"
            + "Items:\n"
            + "1 potion \n", game.getPlayerDesc().toString());
  }

  /**
   * Visit all nodes in a deterministic dungeon.
   */
  @Test
  public void allLocationsAreReachable() {
    Game game = new DungeonGame(
            5, 4, 0, 1, false, 2,
            32,0,3,48,2,50,32,26,21,24,17,15,10,3,
            11,22,20,23,25,2,13,23,13,18,11,5,12,14,11,3,1
    );
    Set<Coordinate> visited = new HashSet<>();
    game.move(WEST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(WEST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(WEST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(EAST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(WEST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(NORTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(EAST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(NORTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(EAST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(EAST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(NORTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(WEST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(NORTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(NORTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(NORTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(EAST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(WEST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(SOUTH);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.shoot(EAST, 1);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.shoot(EAST, 1); // Kill monster in the end node.
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    game.move(EAST);
    visited.add(
            game.getLocationDesc().getCoordinates() // Add coordinates to set
    );
    assertTrue(game.isGameOver()); // Game ends on visiting the end node.
    assertEquals(
            "[(0, 2), (1, 1), (2, 0), (2, 1), (3, 0), "
                    + "(1, 2), (4, 0), (3, 1), (2, 2), (1, 3), "
                    + "(4, 1), (3, 2), (2, 3), (4, 2), (3, 3), "
                    + "(4, 3), (0, 0), (0, 1), (1, 0)]",
            visited.toString());
    // Hence all nodes are visited.
  }
}
