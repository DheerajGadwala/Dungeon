package dungeonview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class RadioInput extends JPanel {

  private final JLabel label;
  private final List<JRadioButton> radioButtons;

  public RadioInput(String labelText, String... options) {
    this(400, 30, labelText, options);
  }

  public RadioInput(int width, int height, String labelText, String... options) {
    JPanel child = new JPanel();
    child.setLayout(new BoxLayout(child, BoxLayout.PAGE_AXIS));
    setMaximumSize(new Dimension(width, height));
    setAlignmentX(0.5f);
    this.label = new JLabel(labelText);
    label.setPreferredSize(new Dimension((int) (width * 0.375), (int) (height * 0.66)));
    label.setFont(new Font("Rockwell", Font.BOLD, 14));
    add(label);
    radioButtons = new ArrayList<>();
    ButtonGroup bg = new ButtonGroup();
    for(String k: options) {
      JRadioButton t = new JRadioButton(k);
      t.setPreferredSize(new Dimension((int) (width * 0.5), (int) (height * 0.66)));
      t.setHorizontalAlignment(SwingConstants.CENTER);
      t.setFocusPainted(false);
      radioButtons.add(t);
      bg.add(t);
      child.add(t);
    }
    add(child);
  }

  String getText() {
    return label.getText();
  }

  /**
   * Returns null if nothing is selected else returns selected button's text.
   * @return null if nothing is selected else returns selected button's text.
   */
  String getSelection() {
    for(JRadioButton r: radioButtons) {
      if (r.isSelected()) {
        return r.getText();
      }
    }
    return null;
  }

}
