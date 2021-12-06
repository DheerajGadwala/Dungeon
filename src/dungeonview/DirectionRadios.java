package dungeonview;

import dungeongeneral.Direction;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dungeongeneral.Direction.*;

class DirectionRadios extends JPanel {

  private static final Color BACKGROUND = Color.BLACK;
  private final List<JRadioButton> radioButtons;

  DirectionRadios(GamePanel board) {
    setBackground(BACKGROUND);
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
    JLabel directionLabel = new JLabel("Direction");
    directionLabel.setFont(new Font("Rockwell", Font.BOLD, 16));
    directionLabel.setBorder(new EmptyBorder(15, 0, 15, 0));
    directionLabel.setForeground(Color.WHITE);
    add(directionLabel);
    directionLabel.setAlignmentY(0.5f);
    add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
    radioButtons = new ArrayList<>();
    JPanel options = new JPanel();
    options.setBackground(BACKGROUND);
    options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
    ButtonGroup rButtons = new ButtonGroup();
    for (Direction direction: Direction.values()) {
      JRadioButton temp = new JRadioButton(direction.getFullForm());
      temp.setBackground(BACKGROUND);
      temp.setFocusPainted(false);
      temp.setFont(new Font("Rockwell", Font.BOLD, 12));
      temp.setForeground(Color.WHITE);
      temp.addChangeListener((l) -> board.requestFocus());
      rButtons.add(temp);
      options.add(temp);
      radioButtons.add(temp);
    }
    add(options);
    add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
  }

  Direction getInput() {
    for(JRadioButton r: radioButtons) {
      if (r.isSelected()) {
        String t = r.getText();
        return "North".equals(t) ? NORTH : "South".equals(t)
                ? SOUTH : "East".equals(t) ? EAST : WEST;
      }
    }
    return null;
  }
}