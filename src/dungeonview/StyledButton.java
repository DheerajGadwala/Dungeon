package dungeonview;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;

class StyledButton extends JButton {

  StyledButton(String text) {
    super(text);
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setBackground(Color.DARK_GRAY);
    setForeground(Color.LIGHT_GRAY);
    setAlignmentX(0.5f);
    setFont(new Font("Rockwell", Font.BOLD, 15));
    setFocusPainted(false);
    addMouseListener(new MouseAdapter() {

      @Override
      public void mouseEntered(MouseEvent evt) {
        setBackground(new Color(77, 145, 109));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
      }

      @Override
      public void mouseExited(MouseEvent evt) {
        setBackground(Color.DARK_GRAY);

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }

      @Override
      public void mouseReleased(MouseEvent evt) {
        setContentAreaFilled(true);
        setBackground(Color.DARK_GRAY);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }

      @Override
      public void mousePressed(MouseEvent evt) {
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(new Color(7, 148, 73));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
      }
    });
  }
}
