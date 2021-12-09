package dungeonview;

import dungeoncontroller.GameFeatures;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This is the home panel.
 */
class Home extends CenteredPanel {

  private final JButton newGame;
  private final JButton resumeGame;
  private final JButton resetGame;
  private final JButton restartGame;
  private final JButton quitGame;
  private static final String ERROR_MESSAGE = "You need to start a game to access this option.";
  private static final String ERROR_MESSAGE_TITLE = "really bruh?";

  /**
   * Home page constructor.
   */
  public Home() {
    JLabel title = new JLabel("Home", SwingConstants.CENTER);
    title.setAlignmentX(0.5f);
    title.setFont(new Font("Rockwell", Font.BOLD, 16));
    title.setBorder(new EmptyBorder(15, 0, 15, 0));
    addCenter(title);
    addCenter(
            Box.createRigidArea(
                    new Dimension(0, 45)
            )
    );
    newGame = new StyledButton("New Game");
    addCenter(newGame);
    addCenter(
            Box.createRigidArea(
                    new Dimension(0, 30)
            )
    );
    resumeGame = new StyledButton("Resume Game");
    addCenter(resumeGame);
    addCenter(
            Box.createRigidArea(
                    new Dimension(0, 30)
            )
    );
    resetGame = new StyledButton("Reset Game");
    addCenter(resetGame);
    addCenter(
            Box.createRigidArea(
                    new Dimension(0, 30)
            )
    );
    restartGame = new StyledButton("Randomized Reset");
    addCenter(restartGame);
    addCenter(
            Box.createRigidArea(
                    new Dimension(0, 30)
            )
    );
    quitGame = new StyledButton("Quit");
    addCenter(quitGame);

  }

  void setFeatures(GameView gameView, GameFeatures controller) {
    newGame.addActionListener((l) -> gameView.showSettings());
    resumeGame.addActionListener((l) -> gameView.showGame());
    resetGame.addActionListener((l) -> {
      if (((DungeonGameView) gameView).hasModel()) {
        controller.resetGame();
      }
      else {
        gameView.showMessage(ERROR_MESSAGE, ERROR_MESSAGE_TITLE);
      }
    });
    restartGame.addActionListener((l) -> {
      if (((DungeonGameView) gameView).hasModel()) {
        controller.restartGame();
      }
      else {
        gameView.showMessage(ERROR_MESSAGE, ERROR_MESSAGE_TITLE);
      }
    });
    quitGame.addActionListener((l) -> controller.quitGame());
  }
}
