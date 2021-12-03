package dungeonview;

import dungeongeneral.Direction;
import dungeongeneral.ReadOnlyLocation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

class Cell extends JPanel {

  private final ReadOnlyLocation location;

  public Cell(ReadOnlyLocation location) {
    this.location = location;
  }



  BufferedImage setBackground() {
    return null;
  }

  @Override
  public void paintComponent(Graphics g) {
    try {
      if (location.isDiscovered()) {
        String str = "";
        List<Direction> directions = location.getPossibleRoutes();
        Collections.sort(directions);
        for (Direction k: directions) {
          str += (k.toString());
        }
        BufferedImage bi = ImageIO.read(new File("img\\" + str + ".png"));
        g.drawImage(bi, 0, 0, 60, 60, null);
      }
      else {
        BufferedImage bi = ImageIO.read(new File("img\\black.png"));
        g.drawImage(bi, 0, 0, 60, 60, null);
      }
    } catch (IOException ignored) {
    }
  }

  private BufferedImage overlay(
          BufferedImage starting, String fpath,
          int offsetX, int offsetY)
          throws IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offsetX, offsetY, null);
    return combined;
  }

}
