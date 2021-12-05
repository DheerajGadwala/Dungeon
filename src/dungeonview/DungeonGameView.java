package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.ReadOnlyGameWithObstacles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static dungeongeneral.Direction.*;
import static dungeongeneral.Direction.SOUTH;

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
    setMinimumSize(new Dimension(750, 650));
    //setResizable(false);
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
  public void setModel(GameFeatures controller, ReadOnlyGameWithObstacles readOnlyGame) {
    this.readOnlyGame = readOnlyGame;
    this.gamepanel = new GamePanel(readOnlyGame);
    this.gamepanel.setFeatures(this, controller);
    this.gamepanel.refresh();
    refresh();
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
}
