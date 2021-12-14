package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.ReadOnlyGameWithObstacles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * View of the dungeon game.
 */
public class DungeonGameView extends JFrame implements GameView {

  private ReadOnlyGameWithObstacles readOnlyGame;
  private GamePanel gamepanel;
  private final Settings settings;
  private final Home home;
  private final JMenuItem homePageMenuItem;
  private final JMenuItem newGameMenuItem;
  private final JMenuItem resetGameMenuItem;
  private final JMenuItem restartGameMenuItem;
  private final JMenuItem quitGameMenuItem;
  private final JMenuItem aboutGameMenuItem;
  private final JMenuItem gameDescriptionMenuItem;
  private static final String ERROR_MESSAGE_TITLE = "really bruh?";
  private static final String ERROR_MESSAGE = "You need to start a game to access this option.";

  /**
   * Constructs a view.
   * Has the following pages: home, settings, and game panel.
   * Also has a JMenu with various options for settings and to
   * show descriptions.
   */
  public DungeonGameView() {
    homePageMenuItem = new JMenuItem("Home");
    newGameMenuItem = new JMenuItem("New Game");
    resetGameMenuItem = new JMenuItem("Reset Game");
    restartGameMenuItem = new JMenuItem("Randomized Reset");
    quitGameMenuItem = new JMenuItem("Quit");
    JMenu menu = new JMenu("Menu");
    menu.add(homePageMenuItem);
    menu.add(newGameMenuItem);
    menu.add(resetGameMenuItem);
    menu.add(restartGameMenuItem);
    menu.add(quitGameMenuItem);
    JMenu info = new JMenu("Info");
    aboutGameMenuItem = new JMenuItem("Commands");
    gameDescriptionMenuItem = new JMenuItem("Game Description");
    info.add(aboutGameMenuItem);
    info.add(gameDescriptionMenuItem);
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(menu);
    menuBar.add(info);
    setJMenuBar(menuBar);
    setTitle("Dungeon");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.settings = new Settings();
    this.home = new Home();
    showHome();
    setMinimumSize(new Dimension(850, 900));
    ImageIcon image = new ImageIcon(new ImageFetcher().getPlayer());
    setIconImage(image.getImage());
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
      showMessage(ERROR_MESSAGE, ERROR_MESSAGE_TITLE);
    }
    else {
      removeAllPanels();
      add(gamepanel);
      refresh();
    }
  }

  @Override
  public void startNewGame(
          GameFeatures controller, ReadOnlyGameWithObstacles readOnlyGame
  ) {
    removeAllPanels();
    this.readOnlyGame = readOnlyGame;
    gamepanel = new GamePanel(readOnlyGame);
    gamepanel.setFeatures(this, controller);
    showGame();
  }

  @Override
  public void refresh() {
    revalidate();
    repaint();
  }

  @Override
  public void setFeatures(GameFeatures controller) {
    settings.setFeatures(controller, this);
    home.setFeatures(this, controller);
    newGameMenuItem.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        showSettings();
      }
    });
    restartGameMenuItem.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        if (readOnlyGame != null) {
          controller.restartGame();
        }
        else {
          showMessage(ERROR_MESSAGE, ERROR_MESSAGE_TITLE);
        }
      }
    });
    resetGameMenuItem.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        if (readOnlyGame != null) {
          controller.resetGame();
        }
        else {
          showMessage(ERROR_MESSAGE, ERROR_MESSAGE_TITLE);
        }
      }
    });
    quitGameMenuItem.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        controller.quitGame();
      }
    });
    homePageMenuItem.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        showHome();
      }
    });
    aboutGameMenuItem.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        showMessage(
                "\nKeys:\nR: pick ruby\nD: pick diamond\nS: pick sapphire\n"
                + "C: pick arrow\nA: attack moving monster\nS: shoot\n"
                + "Arrow keys: shoot direction/ move\n\nMouse Clicks:\nmove",
                "Game Commands"
        );
      }
    });
    gameDescriptionMenuItem.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        if (readOnlyGame == null) {
          showMessage(ERROR_MESSAGE, ERROR_MESSAGE_TITLE);
        }
        else {
          showMessage(
                  readOnlyGame.toString(),
                  "Game Description"
          );
        }
      }
    });

  }

  @Override
  public void showMessage(String message, String title) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void dismiss() {
    this.dispose();
  }

  boolean hasModel() {
    return readOnlyGame != null;
  }
}
