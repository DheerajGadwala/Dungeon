package dungeonview;

import javax.swing.*;
import java.awt.*;

public class CenteredPanel extends JPanel {

  protected final JPanel top;
  protected final JPanel left;
  protected final JPanel bottom;
  protected final JPanel right;
  protected final JPanel center;

  CenteredPanel() {
    setLayout(new BorderLayout());
    top = new JPanel();
    top.setPreferredSize(new Dimension(50, 50));
    left = new JPanel();
    left.setPreferredSize(new Dimension(50, 50));
    bottom = new JPanel();
    bottom.setPreferredSize(new Dimension(50, 50));
    right = new JPanel();
    right.setPreferredSize(new Dimension(50, 50));
    center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    add(top, BorderLayout.NORTH);
    add(left, BorderLayout.WEST);
    add(bottom, BorderLayout.SOUTH);
    add(right, BorderLayout.EAST);
    add(center, BorderLayout.CENTER);
  }

  protected void addCenter(Component component) {
    center.add(component);
  }

  protected void addTop(Component component) {
    top.add(component);
  }

  protected void addLeft(Component component) {
    left.add(component);
  }

  protected void addBottom(Component component) {
    bottom.add(component);
  }

  protected void addRight(Component component) {
    right.add(component);
  }

  void refresh() {
    revalidate();
    repaint();
  }
}
