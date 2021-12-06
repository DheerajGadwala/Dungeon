package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ShotResult;

public interface GameView {

  void showHome();

  void showSettings();

  void showGame();

  void startNewGame(GameFeatures controller, ReadOnlyGameWithObstacles readOnlyGame);

  void refresh();

  void setFeatures(GameFeatures controller);

  void showMessage(String message);

  void makeVisible();

  void showShotResult(ShotResult res);
}
