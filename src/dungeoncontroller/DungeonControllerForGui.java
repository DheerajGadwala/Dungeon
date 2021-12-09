package dungeoncontroller;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.Treasure;
import dungeonmodel.DungeonGameWithObstacles;
import dungeonmodel.GameWithObstacles;
import dungeonview.DungeonGameView;
import dungeonview.GameView;

/**
 * This is the controller of the view.
 * This can be used to create new game models.
 * This can be used to make changes to the game, like move, shoot, etc.
 * This can be used to reset, restart and quit the game.
 */
public class DungeonControllerForGui implements GameFeatures {

  private final GameView view;
  private GameWithObstacles model;

  /**
   * Constructs a controller object (this).
   * Creates a view and makes it visible.
   * Dependent on the view to create a model.
   */
  public DungeonControllerForGui() {
    this(new DungeonGameView());
  }

  /**
   * Constructs a controller object (this).
   * Works with the given model and view.
   * @param model model to be used
   * @param view view to be used.
   */
  public DungeonControllerForGui(GameWithObstacles model, GameView view) {
    this.model = model;
    this.view = view;
    view.setFeatures(this);
    view.startNewGame(this, model);
    view.refresh();
  }

  /**
   *  Constructs a controller object (this).
   *  Works with the given view.
   * @param view view to be used.
   */
  public DungeonControllerForGui(GameView view) {
    this.view = view;
    this.view.setFeatures(this);
  }

  @Override
  public void startNewGame(int rows, int columns, int percentage,
                           int difficulty, boolean enableWrap,
                           int interconnectivity) throws IllegalArgumentException {
    try {
      this.model = new DungeonGameWithObstacles(
              rows, columns, percentage,
              difficulty, enableWrap,
              interconnectivity
      );
      view.startNewGame(this, model);
      view.refresh();
    }
    catch (IllegalArgumentException iae) {
      view.showMessage(iae.getMessage(), "Error");
    }
  }

  @Override
  public void move(Direction direction) {
    try {
      model.move(direction);
      view.refresh();
    }
    catch (IllegalArgumentException ignored) {
    }
    catch (IllegalStateException ise) {
      view.showMessage(ise.getMessage(), "Message");
    }
  }

  @Override
  public void moveToLocation(ReadOnlyLocation location) {
    try {
      model.moveToLocation(location);
      view.refresh();
    }
    catch (IllegalArgumentException ignored) {
    }
    catch (IllegalStateException ise) {
      view.showMessage(ise.getMessage(), "Message");
    }
  }

  @Override
  public void shoot(Direction direction, int distance) {
    try {
      model.shoot(direction, distance);
      view.refresh();
    }
    catch (IllegalArgumentException ignored) {
    }
    catch (IllegalStateException ise) {
      view.showMessage(ise.getMessage(), "Message");
    }
  }

  @Override
  public void pickTreasure(Treasure treasure) {
    try {
      model.cedeTreasure(treasure);
      view.refresh();
    }
    catch (IllegalArgumentException ignored) {
    }
    catch (IllegalStateException ise) {
      view.showMessage(ise.getMessage(), "Message");
    }
  }

  @Override
  public void pickItem(Item item) {
    try {
      model.cedeItem(item);
      view.refresh();
    }
    catch (IllegalArgumentException ignored) {
    }
    catch (IllegalStateException ise) {
      view.showMessage(ise.getMessage(), "Message");
    }
  }

  @Override
  public void attack() {
    try {
      model.attack();
      view.refresh();
    }
    catch (IllegalStateException ignored) {
    }
  }

  @Override
  public void playGame() {
    view.makeVisible();
  }

  @Override
  public void restartGame() {
    startNewGame(
            model.getRowCount(), model.getColumnCount(),
            model.getPercentage(), model.getDifficulty(),
            model.getEnableWrap(), model.getInterconnectivity()
    );
  }

  @Override
  public void resetGame() {
    try {
      model = new DungeonGameWithObstacles(
              model.getRowCount(), model.getColumnCount(),
              model.getPercentage(), model.getDifficulty(),
              model.getEnableWrap(), model.getInterconnectivity(),
              model.getGenerationSequence()
      );
      view.startNewGame(this, model);
    }
    catch (IllegalArgumentException iae) {
      view.showMessage(iae.getMessage(), "Error");
    }
  }

  @Override
  public void quitGame() {
    view.dismiss();
  }
}
