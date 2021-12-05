package dungeonview;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Odour;
import dungeongeneral.Treasure;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

class ImageFetcher {

  static BufferedImage getPlayer() throws IOException {
    return ImageIO.read(new File("img\\player.png"));
  }

  static BufferedImage getTreasure(Treasure treasure) {
    if (treasure != null) {
      try {
        return ImageIO.read(new File("img\\" + treasure.toString() +".png"));
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
    else {
      return null;
    }
  }

  static BufferedImage getItem(Item item) throws IOException {
    if (item != null) {
      return ImageIO.read(new File("img\\" + item.toString() +".png"));
    }
    else {
      return null;
    }
  }

  static BufferedImage getOtyugh() throws IOException {
    return ImageIO.read(new File("img\\otyugh.png"));
  }

  static BufferedImage getOdour(Odour odour) throws IOException {
    if (odour != null) {
      return ImageIO.read(new File("img\\" + odour.toString() +".png"));
    }
    else {
      return null;
    }
  }

  static BufferedImage getLocation(List<Direction> directions) throws IOException {
    String str = "";
    Collections.sort(directions);
    for (Direction k: directions) {
      str += (k.toString());
    }
    return ImageIO.read(new File("img\\" + str + ".png"));
  }

  static BufferedImage getBlack() throws IOException {
    return ImageIO.read(new File("img\\black.png"));
  }

  static BufferedImage getPit() throws IOException {
    return ImageIO.read(new File("img\\pit.png"));
  }

  public static BufferedImage getHealth() throws IOException {
    return ImageIO.read(new File("img\\health.png"));
  }

  public static BufferedImage getThief() throws IOException {
    return ImageIO.read(new File("img\\thief.png"));
  }

  public static BufferedImage getMovingMonster() throws IOException {
    return ImageIO.read(new File("img\\movingMonster.png"));
  }

}
