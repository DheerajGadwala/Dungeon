package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ShotResult;

import javax.swing.*;
import java.awt.*;

/**
 * View of the dungeon game.
 */
public class DungeonGameView extends JFrame implements GameView {

  private ReadOnlyGameWithObstacles readOnlyGame;
  private final Settings settings;
  private final Home home;
  private GamePanel gamepanel;

  public DungeonGameView() {
    setTitle("Dungeon");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.settings = new Settings();
    this.home = new Home();
    showHome();
    setMinimumSize(new Dimension(850, 900));
  }

  private void removeAllPanels() {
    if (settings.isShowing()) {
      remove(settings);
    }
    if (gamepanel != null && gamepanel.isShowing()) {
      remove(gamepanel);
    }
    if (home.isShowing()) {
      remove(home);
    }
    refresh();
  }

  @Override
  public void showHome() {
    removeAllPanels();
    add(home);
    refresh();
  }

  @Override
  public void showSettings() {
    removeAllPanels();
    add(settings);
    refresh();
  }

  @Override
  public void showGame() {
    if (readOnlyGame == null) {
      showMessage("You have not started a game yet!");
    }
    else {
      removeAllPanels();
      add(gamepanel);
      refresh();
    }
  }

  @Override
  public void startNewGame(GameFeatures controller, ReadOnlyGameWithObstacles readOnlyGame) {
    this.readOnlyGame = readOnlyGame;
    this.gamepanel = new GamePanel(readOnlyGame);
    this.gamepanel.refresh();
    this.showGame();
    this.gamepanel.setFeatures(this, controller);
  }

  @Override
  public void refresh() {
    revalidate();
    repaint();
  }

  @Override
  public void setFeatures(GameFeatures controller) {
    settings.setFeatures(controller, this);
    home.setFeatures(this);
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void showShotResult(ShotResult shotResult) {
    gamepanel.showShotResult(shotResult);
  }
}
