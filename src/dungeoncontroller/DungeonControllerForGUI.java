package dungeoncontroller;

import dungeonmodel.DungeonGameWithObstacles;
import dungeonmodel.GameWithObstacles;
import dungeonview.DungeonGameView;
import dungeonview.GameView;

import static dungeongeneral.Direction.*;

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
      DungeonGameWithObstacles a = new DungeonGameWithObstacles(
              rows, columns, percentage,
              difficulty, enableWrap,
              interconnectivity);
      try {
        a.move(NORTH);
      }
      catch (Exception e) {}
            try {
        a.move(NORTH);
      }
      catch (Exception e) {}
            try {
        a.move(EAST);
      }
      catch (Exception e) {}
            try {
        a.move(WEST);
      }
      catch (Exception e) {}
            try {
        a.move(SOUTH);
      }
      catch (Exception e) {}
            try {
        a.move(NORTH);
      }
      catch (Exception e) {}
            try {
        a.move(EAST);
      }
      catch (Exception e) {}
            try {
        a.move(WEST);
      }
      catch (Exception e) {}
            try {
        a.move(SOUTH);
      }
      catch (Exception e) {}
            try {
        a.move(NORTH);
      }
      catch (Exception e) {}
            try {
        a.move(NORTH);
      }
      catch (Exception e) {}
            try {
        a.move(EAST);
      }
      catch (Exception e) {}
            try {
        a.move(NORTH);
      }
      catch (Exception e) {}
            try {
        a.move(EAST);
      }
      catch (Exception e) {}
            try {
        a.move(WEST);
      }
      catch (Exception e) {}
            try {
        a.move(SOUTH);
      }
      catch (Exception e) {}
      this.model = a;
      view.setModel(model);
      view.showGame();
    }
    catch (IllegalArgumentException iae) {
      view.showMessage(iae.getMessage());
    }
  }
}
