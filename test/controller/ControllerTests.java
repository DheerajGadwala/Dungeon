package controller;

import static dungeongeneral.Sound.DYING_HOWL;
import static dungeongeneral.Sound.HISS;
import static dungeongeneral.Sound.HOWL;
import static junit.framework.Assert.assertEquals;

import dungeoncontroller.DungeonControllerTextBasedGame;
import dungeoncontroller.GameController;
import dungeongeneral.Sound;
import dungeonmodel.DungeonGame;
import dungeonmodel.Game;
import org.junit.Before;
import org.junit.Test;
import randomizer.ActualRandomizer;
import randomizer.Randomizer;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Test that the controller works as expected.
 */
public class ControllerTests {

  private Randomizer randomizer;

  /**
   * Setup for this test suit.
   */
  @Before
  public void setUp() {
    this.randomizer = new ActualRandomizer();
  }

  /**
   * Test that if appendable throws IOException, the controller throws an Illegal State Exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    Game m = new DungeonGame(5, 5, 50, 3, false, 3);
    StringReader input = new StringReader("");
    Appendable controllerLog = new FailingAppendable();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
  }
  
  /**
   * If the model decides that the game is over and the player has won,
   * the controller should just relay the information and not make it's own
   * decisions.
   * Visit {@link MockGameOverWin} for clarification of its functionality.
   * Also tests that the controller prints final player description given by
   * the model.
   */
  @Test
  public void testControllerGameOverPlayerWins() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "187\n";
    Game m = new MockGameOverWin(modelLog, uniqueCode);
    Readable input = new StringReader("");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals("", modelLog.toString());
    assertEquals(
        "You have escaped from the dungeon! You lucky dog!\n"
          + "Final player description:\n"
          + uniqueCode,
        controllerLog.toString());
  }

  /**
   * If the model decides that the game is over and the player has won,
   * the controller should just relay the information and not make it's own
   * decisions.
   * Visit {@link MockGameOverLost} for clarification of its functionality.
   * Also tests that the controller prints final player description given by
   * the model.
   */
  @Test
  public void testControllerGameOverPlayerLost() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "188\n";
    Game m = new MockGameOverLost(modelLog, uniqueCode);
    Readable input = new StringReader("");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals("", modelLog.toString());
    assertEquals(
        "The Otyugh is feeding on your body!\n"
            + "Final player description:\n"
            + uniqueCode,
        controllerLog.toString());
  }

  /**
   * Test if controller appends the location description
   * returned by the model and does not change anything.
   * We are returning the unique code from inside the mock
   * model. Hence we get the unique code as the location
   * description.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   */
  @Test
  public void testPlayerLocationDescription() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "190\n";
    Game m = new MockGameLogger(modelLog, uniqueCode);
    Readable input = new StringReader("m\ns\nm\nn");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals("S\nN\n", modelLog.toString());
    assertEquals(
        uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "You move towards S\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "You move towards N\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n",
        controllerLog.toString()
    );
  }

  /**
   * Test controller behaviour when model deems that the
   * input to its methods are illegal. The controller should
   * not make any decisions on its own regardless of weather
   * the model is buggy or not.
   * Test on Move.
   * When the model throws an illegal argument exception,
   * the controller must query for new inputs for the same
   * command again until it the model receives valid inputs.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   */
  @Test
  public void testIllegalArgsMove() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "191\n";
    Game m = new MockGameIllegalArgs(modelLog, uniqueCode);
    Readable input = new StringReader("m\ns\nn\nw\ne");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals("S\nN\nW\nE\n", modelLog.toString());
    assertEquals(

        uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "Illegal args, because the model said so.\n"
            + "Direction? [N E S W]\n"
            + "Illegal args, because the model said so.\n"
            + "Direction? [N E S W]\n"
            + "Illegal args, because the model said so.\n"
            + "Direction? [N E S W]\n"
            + "Illegal args, because the model said so.\n"
            + "Direction? [N E S W]\n",
        controllerLog.toString()
    );
  }

  /**
   * Test controller behaviour when model deems that the
   * input to its methods are illegal. The controller should
   * not make any decisions on its own regardless of weather
   * the model is buggy or not.
   * Test on Shoot.
   * When the model throws an illegal argument exception,
   * the controller must query for new inputs for the same
   * command again until it the model receives valid inputs.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   */
  @Test
  public void testIllegalArgsShoot() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "191\n";
    Game m = new MockGameIllegalArgs(modelLog, uniqueCode);
    Readable input = new StringReader("s\nn\n1\nw\n5");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals(
        "N\n1\nW\n5\n", modelLog.toString()
    );
    assertEquals(
        uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "Distance?\n"
            + "Illegal args, because the model said so.\n"
            + "Direction? [N E S W]\n"
            + "Distance?\n"
            + "Illegal args, because the model said so.\n"
            + "Direction? [N E S W]\n",
        controllerLog.toString()
    );
  }

  /**
   * Test controller behaviour when model deems that the
   * input to its methods are illegal. The controller should
   * not make any decisions on its own regardless of weather
   * the model is buggy or not.
   * Test on cedeTreasure.
   * When the model throws an illegal argument exception,
   * the controller must query for new inputs for the same
   * command again until it the model receives valid inputs.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   */
  @Test
  public void testIllegalArgsCedeTreasure() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "191\n";
    Game m = new MockGameIllegalArgs(modelLog, uniqueCode);
    Readable input = new StringReader("t\nr\nd\ns");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals(
        "ruby\ndiamond\nsapphire\n", modelLog.toString()
    );
    assertEquals(
        uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
            + "Illegal args, because the model said so.\n"
            + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
            + "Illegal args, because the model said so.\n"
            + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
            + "Illegal args, because the model said so.\n"
            + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n",
        controllerLog.toString()
    );
  }

  /**
   * Test controller behaviour when model deems that the
   * input to its methods are illegal. The controller should
   * not make any decisions on its own regardless of weather
   * the model is buggy or not.
   * Test on cedeItem.
   * When the model throws an illegal argument exception,
   * the controller must query for new inputs for the same
   * command again until it the model receives valid inputs.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   */
  @Test
  public void testIllegalArgsCedeItems() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "191\n";
    Game m = new MockGameIllegalArgs(modelLog, uniqueCode);
    Readable input = new StringReader("i\na\np\na");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals(
        "crooked arrow\npotion\ncrooked arrow\n",
        modelLog.toString()
    );
    assertEquals(
        uniqueCode
                + "What do you do?\n"
                + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                + "Illegal args, because the model said so.\n"
                + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                + "Illegal args, because the model said so.\n"
                + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                + "Illegal args, because the model said so.\n"
                + "What Item? [Arrow[a/A] Potion[p/P]]\n",
        controllerLog.toString()
    );
  }


  /**
   * Test controller behaviour when model deems that the
   * input leads the model into an illegal state. The controller
   * should not make any decisions on its own regardless of weather
   * the model is buggy or not.
   * When the model throws an illegal state exception, the controller must
   * query for a new command and not ask for arguments for the same command.
   * Visit {@link MockGameIllegalState} for clarification of its functionality.
   */
  @Test
  public void testIllegalState() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "191\n";
    Game m = new MockGameIllegalState(modelLog, uniqueCode);
    Readable input = new StringReader("i\na\nt\nd\nm\nn\ns\ne\n1");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals(
        "crooked arrow\ndiamond\nN\nE\n1\n",
        modelLog.toString()
    );
    assertEquals(
        uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "What Item? [Arrow[a/A] Potion[p/P]]\n"
            + "Illegal state, because the model said so.\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
            + "Illegal state, because the model said so.\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "Illegal state, because the model said so.\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "Distance?\n"
            + "Illegal state, because the model said so.\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n",
        controllerLog.toString()
    );
  }

  /**
   * Test controller behaviour when the given inputs are invalid. When the
   * inputs can not be matched to valid arguments, the controller must ask
   * the user to enter valid arguments and wait for inputs.
   * The given mock model will log inputs it receive, i.e., the controller
   * blocks other invalid inputs.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * Run on one command each.
   * Only valid inputs reach the model.
   * For every invalid input, we see a message from the controller.
   */
  @Test
  public void testInvalidInputs() {
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "191\n";
    Game m = new MockGameLogger(modelLog, uniqueCode);
    Readable input = new StringReader(
        "eresi\nt\nt\nr\n123\ni\n1cas\na\n\ns\ns\nqwd\nxq\n2\nm\n\n3\ne"
    );
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals(
        "ruby\ncrooked arrow\nS\n2\nE\n",
        modelLog.toString()
    );
    assertEquals(
        uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Please enter a valid input.\n"
            + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
            + "Please enter a valid input.\n"
            + "You picked 1 ruby\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Please enter a valid input.\n"
            + "What Item? [Arrow[a/A] Potion[p/P]]\n"
            + "Please enter a valid input.\n"
            + "You picked 1 crooked arrow\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Please enter a valid input.\n"
            + "Direction? [N E S W]\n"
            + "Distance?\n"
            + "Please enter a valid input.\n"
            + "Please enter a valid input.\n"
            + "You just hear the hiss of your arrow.\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "Please enter a valid input.\n"
            + "Please enter a valid input.\n"
            + "You move towards E\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n",
        controllerLog.toString()
    );
  }

  /**
   * Test controller behaviour when the given inputs are invalid. When the
   * inputs can not be matched to valid arguments, the controller must ask
   * the user to enter valid arguments and wait for inputs.
   * The given mock model will log inputs it receive, i.e., the controller
   * blocks other invalid inputs.
   * Visit {@link MockGameShotResult} for clarification of its functionality.
   * Run on one command each.
   * Only valid inputs reach the model.
   * For every invalid input, we see a message from the controller.
   */
  @Test
  public void testShotResult() {
    int n = randomizer.getIntBetween(1, 3);
    Sound sound = n == 1 ? HISS : n == 2 ? HOWL : DYING_HOWL;
    Appendable modelLog = new StringBuilder();
    String uniqueCode = "191\n";
    Game m = new MockGameShotResult(modelLog, uniqueCode, sound);
    Readable input = new StringReader("s\ne\n1");
    Appendable controllerLog = new StringWriter();
    GameController c = new DungeonControllerTextBasedGame(input, controllerLog);
    c.playGame(m);
    assertEquals(
        "E\n1\n",
        modelLog.toString()
    );
    assertEquals(
        uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
            + "Direction? [N E S W]\n"
            + "Distance?\n"
            + sound.getImplication() + "\n"
            + "##################################################################\n"
            + uniqueCode
            + "What do you do?\n"
            + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n",
        controllerLog.toString()
    );
  }

  /**
   * Test actual model with pseudo-random input
   * on the constructor.
   * Player wins.
   */
  @Test
  public void testActualModel1() {
    Game game = new DungeonGame(4, 5, 50, 5, true, 5,
        20,12,6,0,39,53,6,49,12,7,13,57,12,5,51,36,43,16,42,15,21,
        14,0,24,2,7,3,1,4,9,2,5,8,0,4,3,2,3,3,2,2,1,5,1,2,2,5,1,2,3,1,1,2,1,
        5,2,3,1,18,3,0,1,13,4,16,3,0,4,2,4,10,5,12,1,3,2,5,1,8,2,6,1,2
    );
    Readable input = new StringReader(
        "i\na\ni\na\ni\na\ni\na\ni\na\nm\ne\nm\ne\ns\nn\n1\ns\nn\n1\nm\nn\nt\nd\nt\nr\nt\ns\nt"
            + "\nd\nt\nr\ns\nn\n1\ns\nw\n1\ns\nn\n1\ns\nn\n1\ns\nn\n1\ns\nn\n1\nm\nw\ni\na"
            + "\nm\nn\ns\ns\n1\ns\nw\n3\ni\na\ni\na\nm\nw\nm\nw\nm\nw\ns\nn\n1\ns\nn\n1\nm\nn"
    );
    Appendable controllerLog = new StringWriter();
    GameController controller = new DungeonControllerTextBasedGame(input, controllerLog);
    controller.playGame(game);
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 3 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 2 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 1 crooked arrow \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards E\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 4)\n"
                    + "Possible routes: E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards E\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N W \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a howl filled with agony.\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N W \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a loud howl that is slowly fading away into silence.\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N W \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards N\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 2 diamonds 2 rubies 1 sapphire \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "You picked 1 diamond\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 1 sapphire \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "You picked 1 ruby\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 1 ruby 1 sapphire \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "You picked 1 sapphire\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 1 ruby \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "You picked 1 diamond\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 ruby \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "You picked 1 ruby\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You just hear the hiss of your arrow.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a howl filled with agony.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You just hear the hiss of your arrow.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You just hear the hiss of your arrow.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You just hear the hiss of your arrow.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You just hear the hiss of your arrow.\n"
                    + "You are out of arrows!\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards W\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 4)\n"
                    + "Possible routes: N E W \n"
                    + "There are some items in this cave: 1 crooked arrow \n"
                    + "There is an injured monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 4)\n"
                    + "Possible routes: N E W \n"
                    + "There is an injured monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards N\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 4)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 3 sapphires \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a loud howl that is slowly fading away into silence.\n"
                    + "You are out of arrows!\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 4)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 3 sapphires \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "Not enough arrows.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 4)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 3 sapphires \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 4)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 3 sapphires \n"
                    + "There are some items in this cave: 3 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 4)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 3 sapphires \n"
                    + "There are some items in this cave: 2 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards W\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 3)\n"
                    + "Possible routes: N E S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 2 sapphires \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards W\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 2)\n"
                    + "Possible routes: E S W \n"
                    + "There's some treasure in this cave: 1 diamond 2 rubies 1 sapphire \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards W\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 1)\n"
                    + "Possible routes: N E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a howl filled with agony.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 1)\n"
                    + "Possible routes: N E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a loud howl that is slowly fading away into silence.\n"
                    + "You are out of arrows!\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 1)\n"
                    + "Possible routes: N E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards N\n"
                    + "##################################################################\n"
                    + "You have escaped from the dungeon! You lucky dog!\n"
                    + "Final player description:\n"
                    + "Player info:\n"
                    + "Misses: 5\n"
                    + "Hits: 6\n"
                    + "Kills: 3\n"
                    + "Treasure:\n"
                    + "2 diamonds 2 rubies 1 sapphire \n"
                    + "Items:\n"
                    + "1 potion \n",
            controllerLog.toString()
    );
  }

  /**
   * Test actual model with pseudo-random input
   * on the constructor.
   * Player dies.
   */
  @Test
  public void testActualModel2() {
    Game game = new DungeonGame(4, 5, 50, 5, true, 5,
            20, 12, 6, 0, 39, 53, 6, 49, 12, 7, 13, 57, 12, 5, 51, 36, 43, 16, 42, 15, 21, 14,
            0, 24, 2, 7, 3, 1, 4, 9, 2, 5, 8, 0, 4, 3, 2, 3, 3, 2, 2, 1, 5, 1, 2, 2, 5, 1, 2, 3,
            1, 1, 2, 1, 5, 2, 3, 1, 18, 3, 0, 1, 13, 4, 16, 3, 0, 4, 2, 4, 10, 5, 12, 1, 3, 2, 5,
            1, 8, 2, 6, 1, 1);
    Readable input = new StringReader(
            "s\ne\n2\nm\ne\ns\ne\n3\ns\ne\n1\nm\ne\nm\nn"
    );
    Appendable controllerLog = new StringWriter();
    GameController controller = new DungeonControllerTextBasedGame(input, controllerLog);
    controller.playGame(game);
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You just hear the hiss of your arrow.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards E\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 4)\n"
                    + "Possible routes: E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a howl filled with agony.\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 4)\n"
                    + "Possible routes: E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a howl filled with agony.\n"
                    + "You are out of arrows!\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 4)\n"
                    + "Possible routes: E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards E\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N W \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards N\n"
                    + "##################################################################\n"
                    + "The Otyugh is feeding on your body!\n"
                    + "Final player description:\n"
                    + "Player info:\n"
                    + "Misses: 1\n"
                    + "Hits: 2\n"
                    + "Kills: 0\n"
                    + "Treasure:\n"
                    + "None\n"
                    + "Items:\n"
                    + "1 potion \n",
            controllerLog.toString());
  }

  /**
   * Test actual model with pseudo-random input
   * on the constructor.
   * Player dies.
   * Try invalid moves.
   */
  @Test
  public void testActualModel3() {
    Game game = new DungeonGame(4, 5, 50, 5, true, 5,
            20, 12, 6, 0, 39, 53, 6, 49, 12, 7, 13, 57, 12, 5, 51, 36, 43, 16, 42, 15, 21, 14,
            0, 24, 2, 7, 3, 1, 4, 9, 2, 5, 8, 0, 4, 3, 2, 3, 3, 2, 2, 1, 5, 1, 2, 2, 5, 1, 2, 3,
            1, 1, 2, 1, 5, 2, 3, 1, 18, 3, 0, 1, 13, 4, 16, 3, 0, 4, 2, 4, 10, 5, 12, 1, 3, 2, 5,
            1, 8, 2, 6, 1, 1);
    Readable input = new StringReader(
            "t\nd\nm\ne\nm\ne\ns\nn\n1\ns\nn\n1\nm\nn\nt\ns\nt"
                    + "\ns\nr\ni\na\ni\np\nm\nn\ni\np\na\nm\ns\nm\nw"
    );
    Appendable controllerLog = new StringWriter();
    GameController controller = new DungeonControllerTextBasedGame(input, controllerLog);
    controller.playGame(game);
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "This location has no treasure.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards E\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 4)\n"
                    + "Possible routes: E W \n"
                    + "There are some items in this cave: 4 crooked arrows \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards E\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N W \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a howl filled with agony.\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N W \n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "Distance?\n"
                    + "You hear a loud howl that is slowly fading away into silence.\n"
                    + "##################################################################\n"
                    + "This is a tunnel\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N W \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards N\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 2 diamonds 2 rubies 1 sapphire \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "You picked 1 sapphire\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 2 diamonds 2 rubies \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "No treasure of this type.\n"
                    + "What Treasure? [Diamond[d/D] Ruby[r/R] Sapphire[s/S]]\n"
                    + "You picked 1 ruby\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 2 diamonds 1 ruby \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "This location has no items.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 2 diamonds 1 ruby \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "This location has no items.\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 2 diamonds 1 ruby \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards N\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 0)\n"
                    + "Possible routes: N E S \n"
                    + "There's some treasure in this cave: 3 diamonds 2 rubies 3 sapphires \n"
                    + "There are some items in this cave: 1 crooked arrow \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "This location does not have any potions.\n"
                    + "What Item? [Arrow[a/A] Potion[p/P]]\n"
                    + "You picked 1 crooked arrow\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (0, 0)\n"
                    + "Possible routes: N E S \n"
                    + "There's some treasure in this cave: 3 diamonds 2 rubies 3 sapphires \n"
                    + "you sense a slightly pungent smell of otyughs.\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards S\n"
                    + "##################################################################\n"
                    + "This is a cave\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 2 diamonds 1 ruby \n"
                    + "There is a dead monster here.\n"
                    + "you sense a very pungent smell of otyughs, be careful!\n"
                    + "What do you do?\n"
                    + "Move, Shoot, Pick up Item, Pick up Treasure. [M-S-I-T]\n"
                    + "Direction? [N E S W]\n"
                    + "You move towards W\n"
                    + "##################################################################\n"
                    + "The Otyugh is feeding on your body!\n"
                    + "Final player description:\n"
                    + "Player info:\n"
                    + "Misses: 0\n"
                    + "Hits: 2\n"
                    + "Kills: 1\n"
                    + "Treasure:\n"
                    + "1 ruby 1 sapphire \n"
                    + "Items:\n"
                    + "2 crooked arrows 1 potion \n",
            controllerLog.toString());
  }
}
