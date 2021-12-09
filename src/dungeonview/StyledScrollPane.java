package dungeonview;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

class StyledScrollPane extends JScrollPane {

  StyledScrollPane(Component component) {
    super(
            component,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    getVerticalScrollBar().setUI(new StyledScrollBarUI());
    getHorizontalScrollBar().setUI(new StyledScrollBarUI());
    setBorder(BorderFactory.createEmptyBorder());
    getVerticalScrollBar().setUnitIncrement(16);
    setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JPanel(new GridLayout(0, 1)));
    getCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER).setBackground(Color.BLACK);
  }

  static class StyledScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
      thumbColor = Color.DARK_GRAY;
      thumbHighlightColor = Color.LIGHT_GRAY;
      trackColor = Color.BLACK;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
      return getButton(orientation);
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
      return getButton(orientation);
    }

    private JButton getButton(int orientation) {
      JButton button = new BasicArrowButton(orientation);
      button.setBackground( Color.BLACK);
      button.setForeground( Color.DARK_GRAY);
      return button;
    }
  }

}
