package dungeonview;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

class RadioInput extends JPanel {

  private final JLabel label;
  private final List<JRadioButton> radioButtons;

  RadioInput(String labelText, String... options) {
    this(400, 30, labelText, options);
  }

  RadioInput(int width, int height, String labelText, String... options) {
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
    for (String k: options) {
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
    for (JRadioButton r: radioButtons) {
      if (r.isSelected()) {
        return r.getText();
      }
    }
    return null;
  }

}
