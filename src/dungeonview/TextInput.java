package dungeonview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Predicate;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

class TextInput extends JPanel {

  private final JLabel label;
  private final JTextField input;
  private final Predicate<String> validation;

  TextInput(String labelText, String defaultValue, Predicate<String> validation) {
    this.label = new JLabel(labelText);
    this.input = new JTextField();
    this.validation = validation;
    setAlignmentX(0.5f);
    add(label);
    add(input);
    label.setPreferredSize(new Dimension(150, 20));
    label.setFont(new Font("Rockwell", Font.BOLD, 14));
    input.setPreferredSize(new Dimension(200, 20));
    setMaximumSize(new Dimension(400, 30));
    setLayout(new FlowLayout());
    input.setBorder(new LineBorder(Color.BLACK, 1));
    input.setBackground(Color.LIGHT_GRAY);
    input.setText(defaultValue);
    FocusListener focusListener = new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        input.setBorder(new LineBorder(Color.BLACK, 2));
      }

      @Override
      public void focusLost(FocusEvent e) {
        input.setBorder(new LineBorder(Color.BLACK, 1));
      }
    };
    input.addFocusListener(focusListener);
  }

  boolean validateInput() {
    return validation.test(input.getText());
  }

  String getInput() {
    return input.getText();
  }

  String getText() {
    return label.getText();
  }

}
