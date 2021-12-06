package dungeonview;

import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ReadOnlyPlayer;

import javax.swing.*;
import java.awt.*;

class GamePanelBottom extends JPanel {

  private final ReadOnlyPlayer player;
  private static final Color BACKGROUND = Color.BLACK;
  private final Component leftPadding;
  private final Component rightPadding;

  GamePanelBottom(ReadOnlyGameWithObstacles game, GamePanel board) {
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.player = game.getPlayerDesc();
    setBackground(BACKGROUND);
    leftPadding =  Box.createRigidArea(new Dimension(30, 0));
    add(leftPadding);
    add(new DirectionRadios(board));
    add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
    add(new TextInputForBoard(
            "Distance", "1",
            board
    ));
    rightPadding = Box.createRigidArea(new Dimension(30, 0));
    add(rightPadding);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    leftPadding.setPreferredSize(new Dimension((int) (getWidth() * 0.4), 0));
    leftPadding.revalidate();
    leftPadding.repaint();
    rightPadding.setPreferredSize(new Dimension((int) (getWidth() * 0.6), 0));
    rightPadding.revalidate();
    rightPadding.repaint();
  }
}
