package dungeoncontroller;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Treasure;
import dungeonmodel.DungeonGameWithObstacles;
import dungeonmodel.Game;
import dungeonmodel.GameWithObstacles;
import dungeonview.DungeonGameView;
import dungeonview.GameView;

public class DungeonControllerForGUI implements GameFeatures {

  private final GameView view;
  private GameWithObstacles model;

  public DungeonControllerForGUI() {
    view = new DungeonGameView();
    view.setFeatures(this);
  }

  @Override
  public void generateDungeon(int rows, int columns, int percentage,
                              int difficulty, boolean enableWrap,
                              int interconnectivity) throws IllegalArgumentException {
    try {
      this.model = new DungeonGameWithObstacles(
              rows, columns, percentage,
              difficulty, enableWrap,
              interconnectivity);
      view.setModel(this, model);
      view.showGame();
    }
    catch (IllegalArgumentException iae) {
      view.showMessage(iae.getMessage());
    }
  }

  @Override
  public void move(Direction direction) {
    try {
      model.move(direction);
      view.refresh();
    }
    catch(IllegalArgumentException | IllegalStateException ignored) {}
  }

  @Override
  public void shoot(Direction direction, int distance) {
    try {
      model.shoot(direction, distance);
      view.refresh();
    }
    catch(IllegalArgumentException | IllegalStateException ignored) {
    }
  }

  @Override
  public void pickTreasure(Treasure treasure) {
    try {
      model.cedeTreasure(treasure);
      view.refresh();
    }
    catch(IllegalArgumentException | IllegalStateException ignored) {
    }
  }

  @Override
  public void pickItem(Item item) {
    try {
      model.cedeItem(item);
      view.refresh();
    }
    catch(IllegalArgumentException | IllegalStateException ignored) {
    }
  }

  @Override
  public void attack() {

  }

  @Override
  public void playGame() {
    view.makeVisible();
  }
}
