package dungeonview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This is the home panel.
 */
class Home extends CenteredPanel {

  private final JButton newGame;
  private final JButton resumeGame;

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
  }

  void setFeatures(GameView gameView) {
    newGame.addActionListener((l) -> gameView.showSettings());
    resumeGame.addActionListener((l) -> gameView.showGame());
  }
}
