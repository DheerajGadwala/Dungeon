package dungeoncontroller;

import dungeongeneral.*;
import dungeonmodel.DungeonGameWithObstacles;
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
      view.startNewGame(this, model);
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
    catch(IllegalArgumentException | IllegalStateException ignored) {
    }
  }

  @Override
  public void moveToLocation(ReadOnlyLocation location) {
    try {
      model.moveToLocation(location);
      view.refresh();
    }
    catch(IllegalArgumentException | IllegalStateException ignored) {
    }
  }

  @Override
  public void shoot(Direction direction, int distance) {
    try {
      ShotResult res = model.shoot(direction, distance);
      view.showShotResult(res);
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
    try {
      model.attack();
      view.refresh();
    }
    catch(IllegalArgumentException | IllegalStateException ignored) {
    }
  }

  @Override
  public void playGame() {
    view.makeVisible();
  }
}
