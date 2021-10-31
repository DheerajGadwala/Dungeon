package external;

import locationgraph.DungeonGraph;
import locationgraph.LocationGraphAdt;
import org.junit.Test;
import randomizer.ActualRandomizer;

public class DungeonGraphTest {

  LocationGraphAdt sample;

  @org.junit.Before
  public void setUp() throws Exception {
    sample = new DungeonGraph(new ActualRandomizer(), 4, 7, true);
  }

  @Test
  public void testA() {
    sample = sample.getMst();
    System.out.println(sample.toString());
  }
}