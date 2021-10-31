package locationgraph;

import static common.Direction.EAST;
import static common.Direction.NORTH;
import static common.Direction.SOUTH;
import static common.Direction.WEST;

import common.Direction;
import common.MatrixPosition;
import common.Treasure;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a collection of locations and their connections.
 * This can generate Minimal Spanning Trees of itself.
 */
public class DungeonGraph implements LocationGraphAdt {

  private final List<LocationNode> locationNodes;
  private final List<Connection> connections;
  private final Randomizer randomizer;
  private final int m;
  private final int n;

  /**
   * Creates a en empty locations graph.
   * This constructor is used for mst generation.
   * @param randomizer used for randomization during mst generation
   * @param m dimension of the matrix [maximum]
   * @param n dimension of the matrix [maximum]
   * @throws IllegalArgumentException if randomizer is null, or if m or n are less than 1.
   */
  private DungeonGraph(Randomizer randomizer, int m, int n)
      throws IllegalArgumentException {
    constructionHelper(randomizer, m, n);
    this.locationNodes = new ArrayList<>();
    this.connections = new ArrayList<>();
    this.randomizer = randomizer;
    this.m = m;
    this.n = n;
  }

  /**
   * Creates a location complete location graph with m*n location nodes.
   * @param randomizer used for randomization during mst generation.
   * @param m dimension of the matrix [maximum]
   * @param n dimension of the matrix [maximum]
   * @param wrap if matrix has wrap edges or not
   * @throws IllegalArgumentException if randomizer is null, or if m or n are less than 1.
   */
  public DungeonGraph(Randomizer randomizer, int m, int n, boolean wrap)
      throws IllegalArgumentException {
    constructionHelper(randomizer, m, n);
    this.locationNodes = new ArrayList<>();
    this.connections = new ArrayList<>();
    this.randomizer = randomizer;
    this.m = m;
    this.n = n;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        this.createLocationNode(new MatrixPosition(i, j));
      }
    }
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        this.addConnections(i, j, m, n, wrap);
      }
    }
  }

  private void constructionHelper(Randomizer randomizer, int m, int n)
      throws IllegalArgumentException {
    if (randomizer == null) {
      throw new IllegalArgumentException("randomizer can not be null.");
    }
    if (m < 1 || n < 1) {
      throw new IllegalArgumentException("invalid dimensions.");
    }
  }

  private void addConnections(int i, int j, int m, int n, boolean wrap) {
    if (wrap) {
      this.addConnection(
          new MatrixPosition(i, j),
          new MatrixPosition(((i - 1) % m + m) % m, j),
          NORTH
      );
      this.addConnection(
          new MatrixPosition(i, j),
          new MatrixPosition(i, (j + 1) % n),
          EAST
      );
      this.addConnection(
          new MatrixPosition(i, j),
          new MatrixPosition((i + 1) % m, j),
          SOUTH
      );
      this.addConnection(
          new MatrixPosition(i, j),
          new MatrixPosition(i, ((j - 1) % n + n) % n),
          WEST
      );
    }
    else {
      if (i - 1 > 0) {
        this.addConnection(new MatrixPosition(i, j), new MatrixPosition(i - 1, j), NORTH);
      }
      if (j + 1 < n) {
        this.addConnection(new MatrixPosition(i, j), new MatrixPosition(i, j + 1), EAST);
      }
      if (i + 1 < m) {
        this.addConnection(new MatrixPosition(i, j), new MatrixPosition(i + 1, j), SOUTH);
      }
      if (j - 1 > 0) {
        this.addConnection(new MatrixPosition(i, j), new MatrixPosition(i, j - 1), WEST);
      }
    }
  }

  private boolean containsPosition(MatrixPosition position) {
    for (LocationNode node: locationNodes) {
      if (node.getPosition().equals(position)) {
        return true;
      }
    }
    return false;
  }

  private boolean containsConnection(MatrixPosition position1, MatrixPosition position2) {
    for (Connection edge: connections) {
      MatrixPosition nodePos1 = edge.getVertexA().getPosition();
      MatrixPosition nodePos2 = edge.getVertexB().getPosition();
      if (
          (nodePos1.equals(position1) && nodePos2.equals(position2))
      ) {
        return true;
      }
    }
    return false;
  }

  private LocationNode getLocation(MatrixPosition position) {
    for (LocationNode node: locationNodes) {
      if (node.getPosition().equals(position)) {
        return node;
      }
    }
    throw new IllegalArgumentException("No location at the given position!");
  }

  @Override
  public void createLocationNode(MatrixPosition position)
      throws IllegalArgumentException, IllegalStateException {
    if (position == null) {
      throw new IllegalArgumentException("Position can not be null!");
    }
    if (containsPosition(position)) {
      throw new IllegalStateException(
          "A location already exists at the given position: "
              + position.toString());
    }
    locationNodes.add(new Location(position));
  }

  private void validateEdge(
      MatrixPosition position1,
      MatrixPosition position2,
      Direction direction
  ) throws IllegalArgumentException, IllegalStateException {
    if (direction == null) {
      throw new IllegalArgumentException("direction can not be null!");
    }
    validateEdge(position1, position2);
  }

  private void validateEdge(
      MatrixPosition position1,
      MatrixPosition position2
  ) throws IllegalArgumentException, IllegalStateException {
    if (position1 == null || position2 == null) {
      throw new IllegalArgumentException("Position(s) can not be null!");
    }
    if (!containsPosition(position1) || !containsPosition(position2)) {
      throw new IllegalStateException("No location at one of the given positions.");
    }
  }

  @Override
  public void addConnection(
      MatrixPosition position1,
      MatrixPosition position2,
      Direction direction
  )
      throws IllegalArgumentException, IllegalStateException {
    validateEdge(position1, position2, direction);
    if (containsConnection(position1, position2)) {
      throw new IllegalStateException("Edge already exists between these locations");
    }
    LocationNode node1 = getLocation(position1);
    LocationNode node2 = getLocation(position2);
    node1.setNeighbour(direction, node2);
    this.connections.add(new Connection(node1, node2));

  }

  @Override
  public void removeConnection(MatrixPosition position1, MatrixPosition position2)
      throws IllegalArgumentException, IllegalStateException {
    validateEdge(position1, position2);
    if (!containsConnection(position1, position2)) {
      throw new IllegalStateException("Edge does not exist between these locations");
    }
    LocationNode node1 = getLocation(position1);
    LocationNode node2 = getLocation(position2);
    node1.setNeighbour(node1.getNeighbourDirection(node2), Wall.getInstance());
    node2.setNeighbour(node2.getNeighbourDirection(node1), Wall.getInstance());
  }

  @Override
  public void addTreasure(MatrixPosition position, Treasure treasure)
      throws IllegalArgumentException, IllegalStateException {
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure can not be null.");
    }
    if (position == null) {
      throw new IllegalArgumentException("position can not be null");
    }
    if (!this.containsPosition(position)) {
      throw new IllegalStateException("Position not found in graph.");
    }
    if (this.getLocation(position).isTunnel()) {
      throw new IllegalStateException("Can not add treasure to a tunnel");
    }
    this.getLocation(position).addTreasure(treasure);
  }

  /**
   * Helps with Kruskal's Union Find.
   */
  private void makeSet(List<DungeonGraph> allGraphs, Connection edge) {
    DungeonGraph graph = new DungeonGraph(randomizer, m, n);
    MatrixPosition vertexA = edge.getMatrixPositionA();
    MatrixPosition vertexB = edge.getMatrixPositionB();
    Direction d = edge.getDirection();
    graph.createLocationNode(vertexA);
    graph.createLocationNode(vertexB);
    graph.addConnection(vertexA, vertexB, d);
    graph.addConnection(vertexB, vertexA, d.getOpposite());
    allGraphs.add(graph);
  }

  /**
   * Helps with Kruskal's Union Find.
   */
  private void merge(DungeonGraph that) {
    for (LocationNode k: that.locationNodes) {
      try {
        this.createLocationNode(k.getPosition());
      }
      catch (IllegalStateException ignored) {
      }
    }
    for (Connection k: that.connections) {
      try {
        MatrixPosition v1 = k.getMatrixPositionA();
        MatrixPosition v2 = k.getMatrixPositionB();
        Direction d = k.getDirection();
        this.addConnection(v1, v2, d);
        this.addConnection(v2, v1, d.getOpposite());
      }
      catch (IllegalStateException ignored) {
      }
    }
  }

  /**
   * Helps with Kruskal's Union Find.
   */
  private List<DungeonGraph> unify(
      List<DungeonGraph> allGraphs,
      MatrixPosition vertexA,
      MatrixPosition vertexB
  ) {
    DungeonGraph unifiedGraph = new DungeonGraph(randomizer, m, n);
    List<DungeonGraph> finalGraphs = new ArrayList<>();
    for (DungeonGraph currentGraph: allGraphs) {
      boolean notAdded = true;
      for (Connection c: currentGraph.connections) {
        if (c.hasVertexAt(vertexA) || c.hasVertexAt(vertexB)) {
          unifiedGraph.merge(currentGraph);
          notAdded = false;
          break;
        }
      }
      if (notAdded) {
        finalGraphs.add(currentGraph);
      }
    }
    finalGraphs.add(unifiedGraph);
    return finalGraphs;
  }

  /**
   * Helps with Kruskal's Union Find.
   */
  private boolean isAddable(List<DungeonGraph> allGraphs, Connection edge) {
    for (DungeonGraph graph: allGraphs) {
      boolean aExists = graph.containsPosition(edge.getMatrixPositionA());
      boolean bExists = graph.containsPosition(edge.getMatrixPositionB());
      if (aExists && bExists) {
        return false;
      }
    }
    return true;
  }

  /**
   * Helps with Kruskal's Union Find.
   */
  private List<DungeonGraph> add(List<DungeonGraph> allGraphs, Connection edge) {
    for (DungeonGraph graph: allGraphs) {
      boolean added = false;
      for (Connection c: graph.connections) {
        if (c.isConnected(edge)) {
          if (!c.isConnectedToA(edge)) {
            graph.createLocationNode(edge.getMatrixPositionA());
          }
          else if (!c.isConnectedToB(edge)) {
            graph.createLocationNode(edge.getMatrixPositionB());
          }
          MatrixPosition vertexA = edge.getMatrixPositionA();
          MatrixPosition vertexB = edge.getMatrixPositionB();
          Direction d = edge.getDirection();
          graph.addConnection(vertexA, vertexB, d);
          graph.addConnection(vertexB, vertexA, d.getOpposite());
          added = true;
          break;
        }
      }
      if (added) {
        return allGraphs;
      }
    }
    makeSet(allGraphs, edge);
    return allGraphs;
  }

  @Override
  public LocationGraphAdt getMst() {
    List<DungeonGraph> allGraphs = new ArrayList<>();
    List<Connection> connections = new ArrayList<>(this.connections);
    while (!(
            allGraphs.size() == 1
        &&  allGraphs.get(0).getNumberOfNodes() == this.getNumberOfNodes()
        ) && connections.size() > 0) {
      int x = randomizer.getIntBetween(0, connections.size() - 1);
      Connection c = connections.remove(x);
      if (isAddable(allGraphs, c)) {
        allGraphs = add(allGraphs, c);
        allGraphs = unify(allGraphs, c.getMatrixPositionA(), c.getMatrixPositionB());
      }
    }
    return allGraphs.get(0);
  }

  @Override
  public int getNumberOfNodes() {
    return this.locationNodes.size();
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    String topBorder = "[*]";
    String sideBorder = "[*]";
    ret.append(topBorder.repeat(7 + 2));
    ret.append("\n");
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 3; k++) {
        ret.append(sideBorder);
        for (int j = 0; j < 7; j++) {
          LocationNode n = this.getLocation(new MatrixPosition(i, j));
          if (k == 0) {
            ret.append(n.hasWallAt(NORTH) ? "   " : " | ");
          }
          else if (k == 1) {
            ret.append(
                n.hasWallAt(WEST) ? " " : "-").append("O").append(n.hasWallAt(EAST) ? " " : "-"
            );
          }
          else {
            ret.append(n.hasWallAt(SOUTH) ? "   " : " | ");
          }
        }
        ret.append(sideBorder).append("\n");
      }
    }
    ret.append(topBorder.repeat(7 + 2));
    ret.append("\n");
    return ret.toString();
  }
}
