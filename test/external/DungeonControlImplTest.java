package external;

import dungeon.DungeonControl;
import dungeon.DungeonControlImpl;
import org.junit.Before;
import org.junit.Test;

import static general.Direction.NORTH;

public class DungeonControlImplTest {

  DungeonControl sample;

  @Before
  public void setUp() {
    sample = new DungeonControlImpl(10, 10, false, 0);
    sample.createPlayer("Dheeraj");
  }

  @Test
  public void testA() {
    System.out.println(sample.movePlayer(NORTH));
  }
}