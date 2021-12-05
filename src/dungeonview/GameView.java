package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.ReadOnlyGameWithObstacles;

public interface GameView {

  void showHome();

  void showSettings();

  void showGame();

  void setModel(GameFeatures controller, ReadOnlyGameWithObstacles readOnlyGame);

  void refresh();

  void setFeatures(GameFeatures controller);

  void showMessage(String message);

  void makeVisible();
}
