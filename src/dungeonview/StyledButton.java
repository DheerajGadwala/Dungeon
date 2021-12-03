package dungeonview;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class StyledButton extends JButton {

  public StyledButton(String text) {
    super(text);
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setBackground(Color.BLACK);
    setForeground(Color.LIGHT_GRAY);
    setAlignmentX(0.5f);
    setFont(new Font("Rockwell", Font.BOLD, 15));
    setFocusPainted(false);
    addMouseListener(new MouseAdapter() {

      @Override
      public void mouseEntered(MouseEvent evt) {
        setBackground(new Color(163, 23, 23));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
      }

      @Override
      public void mouseExited(MouseEvent evt) {
        setBackground(Color.BLACK);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }

      @Override
      public void mouseReleased(MouseEvent evt) {
        setContentAreaFilled(true);
        setBackground(Color.BLACK);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }

      @Override
      public void mousePressed(MouseEvent evt) {
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(new Color(120, 2, 2));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
      }
    });
  }
}
