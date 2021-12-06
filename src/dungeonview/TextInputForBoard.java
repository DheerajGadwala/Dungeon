package dungeonview;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class TextInputForBoard extends JPanel {
  private final JLabel label;
  private final JTextField input;
  private static final Color BACKGROUND = Color.BLACK;

  TextInputForBoard(String labelText, String defaultValue, GamePanel board) {
    setBackground(BACKGROUND);
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.label = new JLabel(labelText);
    label.setFont(new Font("Rockwell", Font.BOLD, 16));
    label.setBorder(new EmptyBorder(15, 0, 15, 0));
    label.setForeground(Color.WHITE);
    this.input = new JFormattedTextField(new InputFormatter());
    input.setPreferredSize(new Dimension(100, 30));
    input.setMaximumSize(input.getPreferredSize());
    add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
    add(label);
    add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
    add(input);
    add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
    input.setText(defaultValue);
    FocusListener focusListener = new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        input.setBorder(new LineBorder(Color.DARK_GRAY, 2));
      }

      @Override
      public void focusLost(FocusEvent e) {
        input.setBorder(new LineBorder(Color.DARK_GRAY, 1));
      }
    };
    input.addFocusListener(focusListener);
  }

  String getInput() {
    return input.getText();
  }

  String getText() {
    return label.getText();
  }

  public int getValue() {
    return Integer.parseInt(getInput());
  }
}