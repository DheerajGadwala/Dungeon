package guicontroller;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Item.POTION;
import static dungeongeneral.Treasure.DIAMOND;
import static dungeongeneral.Treasure.RUBY;
import static dungeongeneral.Treasure.SAPPHIRE;
import static junit.framework.Assert.assertEquals;

import dungeoncontroller.DungeonControllerForGui;
import dungeoncontroller.GameFeatures;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.Treasure;
import dungeonmodel.DungeonGameWithObstacles;
import dungeonmodel.GameWithObstacles;
import dungeonview.GameView;
import org.junit.Before;
import org.junit.Test;
import randomizer.ActualRandomizer;
import randomizer.Randomizer;

import java.io.StringWriter;

/**
 * Tests that show that the GUI Controller works as expected.
 */
public class GuiControllerTests {

  private Randomizer randomizer;

  /**
   * Set up for this test suite.
   */
  @Before
  public void setUp() {
    randomizer = new ActualRandomizer();
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   */
  @Test
  public void testStartNewGameValidInputs() {
    int rows = randomizer.getIntBetween(4, 20);
    int cols = randomizer.getIntBetween(4, 20);
    int percent = randomizer.getIntBetween(0, 100);
    int difficulty = randomizer.getIntBetween(1, rows * cols / 5);
    boolean enableWrap = randomizer.getIntBetween(0, 1) == 0;
    int interconnectivity = randomizer.getIntBetween(0, 5);
    GameWithObstacles model = new DungeonGameWithObstacles(
            rows, cols, percent, difficulty,
            enableWrap, interconnectivity
    );
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(model, mockView);
    controller.playGame();
    assertEquals(
            uniqueCode
            + "Game features set.\n"
            + uniqueCode
            + "New Game Started\n"
            + "Rows: " + rows + "\n"
            + "Columns: " + cols + "\n"
            + "Percentage: " + percent + "\n"
            + "Difficulty: " + difficulty + "\n"
            + "Wrap: " + enableWrap + "\n"
            + "Interconnectivity: " + interconnectivity + "\n"
            + uniqueCode
            + "Refreshed.\n"
            + uniqueCode
            + "View made visible.\n",
            viewLog.toString()
    );
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   * The controller calls show message on view to show an appropriate message of
   * what went wrong.
   * invalid rows/ cols.
   * Test on actual model.
   */
  @Test
  public void testStartNewGameInValidInputs1() {
    int rows = 3;
    int cols = 4;
    int percent = 30;
    int difficulty = 1;
    boolean enableWrap = true;
    int interconnectivity = 2;
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(mockView);
    controller.startNewGame(rows, cols, percent, difficulty, enableWrap, interconnectivity);
    assertEquals(
            uniqueCode
                    + "Game features set.\n"
                    + uniqueCode
                    + "display message: \n"
                    + "row+column-2 needs be greater than 5.",
            viewLog.toString()
    );
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   * The controller calls show message on view to show an appropriate message of
   * what went wrong.
   * invalid rows/ cols.
   * Test on actual model.
   */
  @Test
  public void testStartNewGameInValidInputs2() {
    int rows = -4;
    int cols = 20;
    int percent = 30;
    int difficulty = 1;
    boolean enableWrap = true;
    int interconnectivity = 2;
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(mockView);
    controller.startNewGame(rows, cols, percent, difficulty, enableWrap, interconnectivity);
    assertEquals(
            uniqueCode
                    + "Game features set.\n"
                    + uniqueCode
                    + "display message: \n"
                    + "Invalid size of grid.",
            viewLog.toString()
    );
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   * The controller calls show message on view to show an appropriate message of
   * what went wrong.
   * invalid percentage.
   * Test on actual model.
   */
  @Test
  public void testStartNewGameInValidInputs3() {
    int rows = 5;
    int cols = 10;
    int percent = 130;
    int difficulty = 1;
    boolean enableWrap = true;
    int interconnectivity = 2;
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(mockView);
    controller.startNewGame(rows, cols, percent, difficulty, enableWrap, interconnectivity);
    assertEquals(
            uniqueCode
                    + "Game features set.\n"
                    + uniqueCode
                    + "display message: \n"
                    + "Invalid percentage",
            viewLog.toString()
    );
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   * The controller calls show message on view to show an appropriate message of
   * what went wrong.
   * invalid percentage.
   * Test on actual model.
   */
  @Test
  public void testStartNewGameInValidInputs4() {
    int rows = 5;
    int cols = 10;
    int percent = -50;
    int difficulty = 1;
    boolean enableWrap = true;
    int interconnectivity = 2;
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(mockView);
    controller.startNewGame(rows, cols, percent, difficulty, enableWrap, interconnectivity);
    assertEquals(
            uniqueCode
                    + "Game features set.\n"
                    + uniqueCode
                    + "display message: \n"
                    + "Invalid percentage",
            viewLog.toString()
    );
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   * The controller calls show message on view to show an appropriate message of
   * what went wrong.
   * invalid difficulty.
   * Test on actual model.
   */
  @Test
  public void testStartNewGameInValidInputs5() {
    int rows = 4;
    int cols = 4;
    int percent = 50;
    int difficulty = 20;
    boolean enableWrap = true;
    int interconnectivity = 2;
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(mockView);
    controller.startNewGame(rows, cols, percent, difficulty, enableWrap, interconnectivity);
    assertEquals(
            uniqueCode
                    + "Game features set.\n"
                    + uniqueCode
                    + "display message: \n"
                    + "Number of monsters is too high!",
            viewLog.toString()
    );
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   * The controller calls show message on view to show an appropriate message of
   * what went wrong.
   * invalid difficulty.
   * Test on actual model.
   */
  @Test
  public void testStartNewGameInValidInputs6() {
    int rows = 4;
    int cols = 4;
    int percent = 50;
    int difficulty = -20;
    boolean enableWrap = true;
    int interconnectivity = 2;
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(mockView);
    controller.startNewGame(rows, cols, percent, difficulty, enableWrap, interconnectivity);
    assertEquals(
            uniqueCode
                    + "Game features set.\n"
                    + uniqueCode
                    + "display message: \n"
                    + "Number of monsters should be at least one.",
            viewLog.toString()
    );
  }

  /**
   * When a random model is passed to the controller along with a mock view that
   * logs the read only models description, We see that the controller creates a
   * correct model.
   * Visit {@link MockView} for clarification of its functionality.
   * The controller calls show message on view to show an appropriate message of
   * what went wrong.
   * invalid interconnectivity.
   * Test on actual model.
   */
  @Test
  public void testStartNewGameInValidInputs7() {
    int rows = 4;
    int cols = 4;
    int percent = 50;
    int difficulty = 2;
    boolean enableWrap = true;
    int interconnectivity = 20;
    Appendable viewLog = new StringWriter();
    String uniqueCode = "190\n";
    GameView mockView = new MockView(viewLog, uniqueCode);
    GameFeatures controller = new DungeonControllerForGui(mockView);
    controller.startNewGame(rows, cols, percent, difficulty, enableWrap, interconnectivity);
    assertEquals(
            uniqueCode
                    + "Game features set.\n"
                    + uniqueCode
                    + "display message: \n"
                    + "interconnectivity too high",
            viewLog.toString()
    );
  }

  /**
   * On call to playGame the controller must just make the view visible and
   * make no changes to the model.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   *
   */
  @Test
  public void testPlayGame() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    assertEquals(
            "",
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n",
            viewLog.toString()
    );
  }

  /**
   * On call to restartGame the controller must use the methods on the model
   * to get data to create a new Model and set it to the view. It should not validate
   * what the model is returning and just create a new Model even if the current model
   * is returning incorrect data.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   */
  @Test
  public void testRestartGame() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.restartGame();
    assertEquals(
            modelCode
                    + "get row count called\n"
                    + modelCode
                    + "get column count called\n"
                    + modelCode
                    + "get percentage called\n"
                    + modelCode
                    + "get difficulty called\n"
                    + modelCode
                    + "get enable wrapped called\n"
                    + modelCode
                    + "get interconnectivity called\n",
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "New Game Started\n"
                    + "Rows: 4\n"
                    + "Columns: 4\n"
                    + "Percentage: 50\n"
                    + "Difficulty: 3\n"
                    + "Wrap: true\n"
                    + "Interconnectivity: 2\n"
                    + viewCode
                    + "Refreshed.\n",
            viewLog.toString()
    );
  }

  /**
   * On call to resetGame the controller must use the methods on the model to
   * get a new model object with the current model's start state and set it to the view.
   * It should not validate what the model is returning even if the returned model's start
   * state is not the same as the current model's start state.
   * is returning incorrect data.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * MockGameLogger returns data to generate a real dungeon in the controller.
   * When models returns their data, the controller uses it to generate new models.
   */
  @Test
  public void testResetGame() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.resetGame();
    assertEquals(
            modelCode
                    + "get row count called\n"
                    + modelCode
                    + "get column count called\n"
                    + modelCode
                    + "get percentage called\n"
                    + modelCode
                    + "get difficulty called\n"
                    + modelCode
                    + "get enable wrapped called\n"
                    + modelCode
                    + "get interconnectivity called\n"
                    + modelCode
                    + "get generation sequence called\n",
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "New Game Started\n"
                    + "Rows: 4\n"
                    + "Columns: 4\n"
                    + "Percentage: 50\n"
                    + "Difficulty: 3\n"
                    + "Wrap: true\n"
                    + "Interconnectivity: 2\n",
            viewLog.toString()
    );
  }

  /**
   * On call to quitGame the controller must just call dismiss method on the view.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * MockGameLogger returns a new MockGameLogger with unique code 520 on
   * call to its getInitialState method.
   */
  @Test
  public void testQuitGame() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.quitGame();
    assertEquals(
            "",
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View dismissed.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that logs inputs to its methods and a mock view that
   * logs the functions that were called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that calling the move method gives expected logs in the view and the controller.
   */
  @Test
  public void testValidMove() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 3);
    Direction direction = random == 0 ? NORTH : random == 1 ? SOUTH : random == 2 ? EAST : WEST;
    controller.move(direction);
    assertEquals(
            modelCode
                    + "move called: "
                    + direction.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "Refreshed.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that logs inputs to its methods and a mock view that
   * logs the functions that were called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that calling the move to location method gives expected logs in the view
   * and the controller.
   */
  @Test
  public void testValidMoveToLocation() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    String locationCode = "300";
    ReadOnlyLocation mockLocation = new MockReadOnlyLocation(locationCode);
    controller.moveToLocation(mockLocation);
    assertEquals(
            modelCode
                    + "move to location called: "
                    + mockLocation.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "Refreshed.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that logs inputs to its methods and a mock view that
   * logs the functions that were called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that calling the shoot method gives expected logs in the view and the controller.
   */
  @Test
  public void testValidShoot() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 3);
    Direction direction = random == 0 ? NORTH : random == 1 ? SOUTH : random == 2 ? EAST : WEST;
    int distance = randomizer.getIntBetween(0, 100);
    controller.shoot(direction, distance);
    assertEquals(
            modelCode
                    + "shoot called: "
                    + direction.toString()
                    + " "
                    + distance,
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "Refreshed.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that logs inputs to its methods and a mock view that
   * logs the functions that were called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that calling the pick treasure method gives expected logs in the view and
   * the controller.
   */
  @Test
  public void testValidPickTreasure() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 2);
    Treasure treasure = random == 0 ? DIAMOND : random == 1 ? RUBY : SAPPHIRE;
    controller.pickTreasure(treasure);
    assertEquals(
            modelCode
                    + "cede treasure called: "
                    + treasure.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "Refreshed.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that logs inputs to its methods and a mock view that
   * logs the functions that were called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that calling the pick item method gives expected logs in the view and the controller.
   */
  @Test
  public void testValidPickItem() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 1);
    Item item = random == 0 ? CROOKED_ARROW : POTION;
    controller.pickItem(item);
    assertEquals(
            modelCode
                    + "cede item called: "
                    + item.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "Refreshed.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that logs inputs to its methods and a mock view that
   * logs the functions that were called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameLogger} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that calling the attack method gives expected logs in the view and the controller.
   */
  @Test
  public void testAttack() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameLogger(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    controller.attack();
    assertEquals(
            modelCode
                    + "attack called",
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "Refreshed.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal argument error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the move call throws illegal argument error, hence the controller ignores it
   * and makes no changes to the view/ does not refresh the view [because the model is not updated].
   */
  @Test
  public void testMoveIllegalArgs() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalArgs(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 3);
    Direction direction = random == 0 ? NORTH : random == 1 ? SOUTH : random == 2 ? EAST : WEST;
    controller.move(direction);
    assertEquals(
            modelCode
                    + "move called: "
                    + direction.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal argument error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the move to location call throws illegal argument error, hence the controller
   * ignores it and makes no changes to the view/ does not refresh the view [because the model
   * is not updated].
   */
  @Test
  public void testMoveToLocationIllegalArgs() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalArgs(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    String locationCode = "300";
    ReadOnlyLocation mockLocation = new MockReadOnlyLocation(locationCode);
    controller.moveToLocation(mockLocation);
    assertEquals(
            modelCode
                    + "move to location called: "
                    + mockLocation.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal argument error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the shoot call throws illegal argument error, hence the controller ignores it
   * and makes no changes to the view/ does not refresh the view [because the model is not updated].
   */
  @Test
  public void testShootIllegalArgs() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalArgs(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 3);
    Direction direction = random == 0 ? NORTH : random == 1 ? SOUTH : random == 2 ? EAST : WEST;
    int distance = randomizer.getIntBetween(0, 100);
    controller.shoot(direction, distance);
    assertEquals(
            modelCode
                    + "shoot called: "
                    + direction.toString()
                    + " "
                    + distance,
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal argument error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the pick treasure call throws illegal argument error, hence the
   * controller ignores it and makes no changes to the view/ does not refresh the
   * view [because the model is not updated].
   */
  @Test
  public void testPickTreasureIllegalArgs() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalArgs(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 2);
    Treasure treasure = random == 0 ? DIAMOND : random == 1 ? RUBY : SAPPHIRE;
    controller.pickTreasure(treasure);
    assertEquals(
            modelCode
                    + "cede treasure called: "
                    + treasure.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal argument error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalArgs} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the pick item call throws illegal argument error, hence the controller ignores it
   * and makes no changes to the view/ does not refresh the view [because the model is not updated].
   */
  @Test
  public void testPickItemIllegalArgs() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalArgs(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 1);
    Item item = random == 0 ? CROOKED_ARROW : POTION;
    controller.pickItem(item);
    assertEquals(
            modelCode
                    + "cede item called: "
                    + item.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n",
            viewLog.toString()
    );
  }


  /**
   * We pass a mock model that that always throw illegal state error when the player
   * command methods are called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalState} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the move call throws illegal state error, hence the controller displays a message
   * in the view.
   */
  @Test
  public void testMoveIllegalState() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalState(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 3);
    Direction direction = random == 0 ? NORTH : random == 1 ? SOUTH : random == 2 ? EAST : WEST;
    controller.move(direction);
    assertEquals(
            modelCode
                    + "move called: "
                    + direction.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "display message: \n"
                    + "Because the model said so.",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal state error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalState} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the move to location call throws illegal state error, hence the controller
   * displays a message in the view.
   */
  @Test
  public void testMoveToLocationIllegalState() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalState(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    String locationCode = "300";
    ReadOnlyLocation mockLocation = new MockReadOnlyLocation(locationCode);
    controller.moveToLocation(mockLocation);
    assertEquals(
            modelCode
                    + "move to location called: "
                    + mockLocation.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "display message: \n"
                    + "Because the model said so.",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal state error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalState} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the shoot call throws illegal state error, hence the controller displays a
   * message in the view.
   */
  @Test
  public void testShootIllegalState() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalState(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 3);
    Direction direction = random == 0 ? NORTH : random == 1 ? SOUTH : random == 2 ? EAST : WEST;
    int distance = randomizer.getIntBetween(0, 100);
    controller.shoot(direction, distance);
    assertEquals(
            modelCode
                    + "shoot called: "
                    + direction.toString()
                    + " "
                    + distance,
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "display message: \n"
                    + "Because the model said so.",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal state error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalState} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the pick treasure call throws illegal state error, hence the controller
   * displays a message in the view.
   */
  @Test
  public void testPickTreasureIllegalState() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalState(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 2);
    Treasure treasure = random == 0 ? DIAMOND : random == 1 ? RUBY : SAPPHIRE;
    controller.pickTreasure(treasure);
    assertEquals(
            modelCode
                    + "cede treasure called: "
                    + treasure.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "display message: \n"
                    + "Because the model said so.",
            viewLog.toString()
    );
  }

  /**
   * We pass a mock model that that always throw illegal state error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalState} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the pick item call throws illegal state error, hence the controller
   * displays a message in the view.
   */
  @Test
  public void testPickItemIllegalState() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalState(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    int random = randomizer.getIntBetween(0, 1);
    Item item = random == 0 ? CROOKED_ARROW : POTION;
    controller.pickItem(item);
    assertEquals(
            modelCode
                    + "cede item called: "
                    + item.toString(),
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n"
                    + viewCode
                    + "display message: \n"
                    + "Because the model said so.",
            viewLog.toString()
    );
  }


  /**
   * We pass a mock model that that always throw illegal state error when the player
   * command methods are  called, and a mock view that logs the functions that were
   * called by controller and the readonly model's
   * toString when it is set.
   * Visit {@link MockView} for clarification of its functionality.
   * Visit {@link MockGameIllegalState} for clarification of its functionality.
   * The controller functions are called it makes changes in the model and then makes
   * the view reflect these changes.
   * We see that the attack call throws illegal state error, the controller should just
   * ignore this.
   */
  @Test
  public void testAttackIllegalState() {
    Appendable modelLog = new StringWriter();
    String modelCode = "220\n";
    GameWithObstacles mockModel = new MockGameIllegalState(modelLog, modelCode);
    Appendable viewLog = new StringWriter();
    String viewCode = "190\n";
    GameView mockView = new MockView(viewLog, viewCode);
    GameFeatures controller = new DungeonControllerForGui(mockModel, mockView);
    controller.playGame();
    controller.attack();
    assertEquals(
            modelCode
                    + "attack called",
            modelLog.toString()
    );
    assertEquals(
            viewCode
                    + "Game features set.\n"
                    + viewCode
                    + "New Game Started\n"
                    + modelCode
                    + "\n"
                    + viewCode
                    + "Refreshed.\n"
                    + viewCode
                    + "View made visible.\n",
            viewLog.toString()
    );
  }
}
