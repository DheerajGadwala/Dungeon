package dungeonview;

import static javax.imageio.ImageIO.read;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Odour;
import dungeongeneral.Treasure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

class ImageFetcher {

  BufferedImage getPlayer() {
    try {
      return read(getClass().getResource("/player.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  BufferedImage getTreasure(Treasure treasure) {
    if (treasure != null) {
      try {
        return read(getClass().getResource("/" + treasure.toString() + ".png"));
      } catch (IOException ignored) {
      }
    }
    return null;
  }

  BufferedImage getItem(Item item) {
    if (item != null) {
      try {
        return read(getClass().getResource("/" + item.toString() + ".png"));
      } catch (IOException ignored) {
      }
    }
    return null;
  }

  BufferedImage getOtyugh() {
    try {
      return read(getClass().getResource("/otyugh.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  BufferedImage getOdour(Odour odour) {
    if (odour != null) {
      try {
        return read(getClass().getResource("/" + odour.toString() + ".png"));
      } catch (IOException ignored) {
      }
    }
    return null;
  }

  BufferedImage getLocation(List<Direction> directions) {
    String str = "";
    Collections.sort(directions);
    for (Direction k: directions) {
      str += (k.toString());
    }
    try {
      return read(getClass().getResource("/" + str + ".png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  BufferedImage getBlack() {
    try {
      return read(getClass().getResource("/black.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  BufferedImage getPit() {
    try {
      return read(getClass().getResource("/pit.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getHealth() {
    try {
      return read(getClass().getResource("/health.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getThief() {
    try {
      return read(getClass().getResource("/thief.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getMovingMonster() {
    try {
      return read(getClass().getResource("/movingMonster.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getGrey() {
    try {
      return read(getClass().getResource("/grey.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getBrown() {
    try {
      return read(getClass().getResource("/brown.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getPointer() {
    try {
      return read(getClass().getResource("/pointer.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getKillSound() {
    try {
      return read(getClass().getResource("/kill.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getHitSound() {
    try {
      return read(getClass().getResource("/hit.png"));
    } catch (IOException ignored) {
    }
    return null;
  }

  public BufferedImage getSignsOfPit() {
    try {
      return read(getClass().getResource("/mud.png"));
    } catch (IOException ignored) {
    }
    return null;
  }
}
