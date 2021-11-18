package dungeon;

import general.MatrixPosition;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import randomizer.ActualRandomizer;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * These tests are written to test the
 * internal functionalities of DungeonGame
 * to test proper functioning of random
 * dungeons. These tests will not be tagged
 * in the self-eval.
 */
public class InternalTests {

  private Randomizer randomizer;
  private DungeonGame game;

  /**
   * Setup for this test suite.
   */
  @Before
  public void setUp() {
    randomizer = new ActualRandomizer();
    game = (DungeonGame) generateRandomDungeon();
  }
  /**
   * Any randomly generated dungeon will pass this test.
   * Every node can be visited from every other node.
   * Since this is a bi directional graph, if we visit all nodes
   * in a single bfs, our dungeon graph is strongly connected.
   * We start the bfs with a random node.
   */
  @Test
  public void testAllLocationsReachable() {
    List<MatrixPosition> nodes = game.getAllPositions();
    Map<MatrixPosition, List<MatrixPosition>> adjacencyList = generateAdjacencyList(
        nodes,
        game.getAllConnections()
    );
    Map<MatrixPosition, Boolean> visited = new HashMap<>();
    for (MatrixPosition node: nodes) {
      visited.put(node, false);
    }
    List<MatrixPosition> queue = new ArrayList<>();
    queue.add(nodes.get(randomizer.getIntBetween(0, nodes.size() - 1)));
    while (queue.size() > 0) {
      MatrixPosition curr = queue.remove(0);
      if (visited.get(curr)) {
        continue;
      }
      visited.replace(curr, true);
      queue.addAll(adjacencyList.get(curr));
    }
    boolean allVisited = true;
    for (MatrixPosition node: nodes) {
      allVisited &= visited.get(node);
    }
    Assert.assertTrue(allVisited);
  }

  /**
   * Any randomly generated dungeon will pass this test.
   * We find the shortest distance from the start node
   * to the other nodes using a bfs.
   * We assert that the distance to the end node is greater than 5.
   */
  @Test
  public void startEndDistance() {
    List<MatrixPosition> nodes = game.getAllPositions();
    Map<MatrixPosition, List<MatrixPosition>> adjacencyList = generateAdjacencyList(
        nodes,
        game.getAllConnections()
    );
    Map<MatrixPosition, Boolean> visited = new HashMap<>();
    for (MatrixPosition node: nodes) {
      visited.put(node, false);
    }
    Map<MatrixPosition, Integer> distance = new HashMap<>();
    for (MatrixPosition node: nodes) {
      distance.put(node, 0);
    }
    List<MatrixPosition> queue = new ArrayList<>();
    queue.add(game.getStartPosition());
    while (queue.size() > 0) {
      MatrixPosition curr = queue.remove(0);
      if (visited.get(curr)) {
        continue;
      }
      visited.replace(curr, true);
      for (MatrixPosition neighbour : adjacencyList.get(curr)) {
        queue.add(neighbour);
        distance.replace(neighbour, distance.get(curr) + 1);
      }
    }
    Assert.assertTrue(distance.get(game.getEndPosition()) > 5);
  }

  private Game generateRandomDungeon() {
    int m = randomizer.getIntBetween(4, 30);
    int n = randomizer.getIntBetween(4, 30);
    boolean wrap = randomizer.getIntBetween(1, 2) == 1;
    int interconnectivity = randomizer.getIntBetween(0, m * n / 2 - 1);
    int percentage = randomizer.getIntBetween(0, 100);
    return new DungeonGame(m, n, percentage, 5, wrap, interconnectivity);
  }

  private Map<MatrixPosition, List<MatrixPosition>> generateAdjacencyList(
      List<MatrixPosition> nodes, List<List<MatrixPosition>> connections
  ) {
    Map<MatrixPosition, List<MatrixPosition>> adjacencyList = new HashMap<>();
    for (MatrixPosition node: nodes) {
      adjacencyList.put(node, new ArrayList<>());
    }
    for (List<MatrixPosition> edge: connections) {
      adjacencyList.get(edge.get(0)).add(edge.get(1));
    }
    return adjacencyList;
  }
}