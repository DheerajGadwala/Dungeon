package dungeonview;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Sound.DYING_HOWL;
import static dungeongeneral.Sound.HOWL;

import dungeoncontroller.GameFeatures;
import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeongeneral.Treasure;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

class Cell extends JPanel {

  private final ReadOnlyGameWithObstacles game;
  private final ReadOnlyLocation location;
  private final ReadOnlyPlayer player;
  private final MutableInteger cellSize;
  private boolean discoveryEstablished;
  private double yOffset;
  private double xOffset;
  private BufferedImage currentImage;
  private final ImageFetcher imageFetcher;

  Cell(Coordinate coordinate, ReadOnlyGameWithObstacles game, MutableInteger cellSize) {
    this.game = game;
    this.location = game.getLocation(coordinate);
    this.player = game.getPlayerDesc();
    this.cellSize = cellSize;
    this.imageFetcher = new ImageFetcher();
  }

  @Override
  public void paintComponent(Graphics gOld) {
    Graphics2D g = (Graphics2D) gOld;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    BufferedImage finalImage;
    if (location.isDiscovered()) {
      if (!discoveryEstablished) {
        discoveryEstablished = true;
        setOffset();
      }
      finalImage = imageFetcher.getLocation(location.getPossibleRoutes());
      finalImage = overlay(
              finalImage, imageFetcher.getOdour(location.getOdour()),
              (int) (cellSize.getValue() * (0.25 + xOffset)),
              (int) (cellSize.getValue() * (0.25 + yOffset)),
              (int) (cellSize.getValue() * 0.5),
              (int) (cellSize.getValue() * 0.5)
      );
      if (location.hasPit()) {
        finalImage = overlay(
                finalImage, imageFetcher.getPit(),
                (int) (cellSize.getValue() * (0.325 + xOffset)),
                (int) (cellSize.getValue() * (0.375 + yOffset)),
                (int) (cellSize.getValue() * 0.35),
                (int) (cellSize.getValue() * 0.35)
        );
      }
      if (location.hasSignsOfNearbyPit()) {
        finalImage = overlay(
                finalImage, imageFetcher.getSignsOfPit(),
                (int) (cellSize.getValue() * (0.375 + xOffset)),
                (int) (cellSize.getValue() * (0.4 + yOffset)),
                (int) (cellSize.getValue() * 0.15),
                (int) (cellSize.getValue() * 0.15)
        );
      }
      if (player.isAt(location)) {

        finalImage = overlay(
                finalImage, imageFetcher.getPlayer(),
                (int) (cellSize.getValue() * (0.35 + xOffset)),
                (int) (cellSize.getValue() * (0.35 + yOffset)),
                (int) (cellSize.getValue() * 0.4),
                (int) (cellSize.getValue() * 0.4)
        );
        if (game.thiefAtPlayerLocation()) {
          finalImage = overlay(
                  finalImage, imageFetcher.getThief(),
                  (int) (cellSize.getValue() * (0.25 + xOffset)),
                  (int) (cellSize.getValue() * (0.15 + yOffset)),
                  (int) (cellSize.getValue() * 0.3),
                  (int) (cellSize.getValue() * 0.3)
          );
        }
        if (game.movingMonsterAliveAtPlayerLocation()) {
          finalImage = overlay(
                  finalImage, imageFetcher.getMovingMonster(),
                  (int) (cellSize.getValue() * (0.4 + xOffset)),
                  (int) (cellSize.getValue() * (0.65 + yOffset)),
                  (int) (cellSize.getValue() * 0.3),
                  (int) (cellSize.getValue() * 0.3)
          );
        }
        if (player.getPreviousShotResult() == DYING_HOWL) {
          finalImage = overlay(
                  finalImage, imageFetcher.getKillSound(),
                  (int) (cellSize.getValue() * (0.6 + xOffset)),
                  (int) (cellSize.getValue() * (0.7 + yOffset)),
                  (int) (cellSize.getValue() * 0.2),
                  (int) (cellSize.getValue() * 0.2)
          );
        }
        else if (player.getPreviousShotResult() == HOWL) {
          finalImage = overlay(
                  finalImage, imageFetcher.getHitSound(),
                  (int) (cellSize.getValue() * (0.6 + xOffset)),
                  (int) (cellSize.getValue() * (0.7 + yOffset)),
                  (int) (cellSize.getValue() * 0.2),
                  (int) (cellSize.getValue() * 0.2)
          );
        }
      }
      if (location.hasMonster()) {
        finalImage = overlay(
                finalImage, imageFetcher.getOtyugh(),
                (int) (cellSize.getValue() * 0.2),
                (int) (cellSize.getValue() * 0.5),
                (int) (cellSize.getValue() * 0.27),
                (int) (cellSize.getValue() * 0.27)
        );
        if (location.hasInjuredMonster()) {
          finalImage = overlay(
                  finalImage, imageFetcher.getItem(CROOKED_ARROW),
                  (int) (cellSize.getValue() * 0.2),
                  (int) (cellSize.getValue() * 0.6),
                  (int) (cellSize.getValue() * 0.3),
                  (int) (cellSize.getValue() * 0.3)
          );
        }
        if (location.hasDeadMonster()) {
          finalImage = overlay(
                  finalImage, imageFetcher.getItem(CROOKED_ARROW),
                  (int) (cellSize.getValue() * 0.2),
                  (int) (cellSize.getValue() * 0.6),
                  (int) (cellSize.getValue() * 0.3),
                  (int) (cellSize.getValue() * 0.3)
          );
          finalImage = overlay(
                  finalImage, imageFetcher.getItem(CROOKED_ARROW),
                  (int) (cellSize.getValue() * 0.1),
                  (int) (cellSize.getValue() * 0.6),
                  (int) (cellSize.getValue() * 0.3),
                  (int) (cellSize.getValue() * 0.3)
          );
        }
      }
      if (location.hasTreasure()) {
        Map<Treasure, Integer> treasures = location.getTreasure();
        double increment = 0.1;
        for (Treasure treasure: Treasure.values()) {
          if (treasures.get(treasure) != 0) {
            finalImage = overlay(
                    finalImage, imageFetcher.getTreasure(treasure),
                    (int) (cellSize.getValue() * (0.5 - increment)),
                    (int) (cellSize.getValue() * (0.1 + increment)),
                    (int) (cellSize.getValue() * 0.11),
                    (int) (cellSize.getValue() * 0.11)
            );
            increment += 0.1;
          }
        }
      }
      if (location.hasItems()) {
        Map<Item, Integer> items = location.getItems();
        for (Item item: Item.values()) {
          if (items.get(item) != 0) {
            finalImage = overlay(
                    finalImage, imageFetcher.getItem(item),
                    (int) (cellSize.getValue() * (0.3 + xOffset)),
                    (int) (cellSize.getValue() * (0.3 + yOffset)),
                    (int) (cellSize.getValue() * 0.2),
                    (int) (cellSize.getValue() * 0.2)
            );
          }
        }
      }
    }
    else {
      finalImage = imageFetcher.getBlack();
    }
    currentImage = finalImage;
    g.drawImage(
            finalImage, 0, 0,
            cellSize.getValue(),
            cellSize.getValue(),
            null
    );
  }

  private BufferedImage overlay(
          BufferedImage starting, BufferedImage overlay,
          int offsetX, int offsetY, int height, int width
  ) {
    BufferedImage combined = new BufferedImage(
            cellSize.getValue() * 2,
            cellSize.getValue() * 2,
            BufferedImage.TYPE_INT_ARGB
    );
    Graphics2D g = (Graphics2D) combined.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g.scale(2, 2);
    g.drawImage(starting, 0, 0, cellSize.getValue(), cellSize.getValue(), null);
    g.drawImage(overlay, offsetX, offsetY, width, height, null);
    return combined;
  }

  private void setOffset() {
    List<Direction> directions = location.getPossibleRoutes();
    if (location.isTunnel()) {
      this.yOffset =  - (
              directions.contains(NORTH) && directions.contains(SOUTH) ? 0
                      : directions.contains(NORTH) ? 0.15
                      : directions.contains(SOUTH) ? -0.15
                      : 0);
      this.xOffset =  - (
              directions.contains(EAST) && directions.contains(WEST) ? 0
                      : directions.contains(EAST) ? -0.15
                      : directions.contains(WEST) ? 0.15
                      : 0);
    }
    else {
      yOffset = 0;
      xOffset = 0;
    }
  }

  void setFeatures(GameFeatures controller, GamePanel board) {
    MouseAdapter modelAction = new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        controller.moveToLocation(location);
      }
    };
    MouseAdapter noModelAction = new MouseAdapter() {

      private boolean mouseInside = false;

      @Override
      public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.mouseInside = true;
        if (!location.isDiscovered()) {
          getGraphics().drawImage(
                  imageFetcher.getGrey(), 0, 0,
                  cellSize.getValue(),
                  cellSize.getValue(),
                  null
          );
        }
        else if (!player.getCoordinates().equals(location.getCoordinates())) {
          getGraphics().drawImage(
                  overlay(
                          currentImage,
                          imageFetcher.getPointer(),
                          (int) (cellSize.getValue() * (0.4 + xOffset)),
                          (int) (cellSize.getValue() * (0.4 + yOffset)),
                          (int) (cellSize.getValue() * 0.2),
                          (int) (cellSize.getValue() * 0.2)
                  ),
                  0, 0,
                  cellSize.getValue(),
                  cellSize.getValue(),
                  null
          );
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!location.isDiscovered()) {
          getGraphics().drawImage(
                  imageFetcher.getBrown(), 0, 0,
                  cellSize.getValue(),
                  cellSize.getValue(),
                  null);
        }
      }

      @Override
      public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.mouseInside = false;
        revalidate();
        repaint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        setCursor(
                mouseInside
                        ? new Cursor(Cursor.HAND_CURSOR)
                        : new Cursor(Cursor.DEFAULT_CURSOR)
        );
        if (!location.isDiscovered()) {
          getGraphics().drawImage(
                  mouseInside
                          ? imageFetcher.getGrey()
                          : imageFetcher.getBlack(),
                  0, 0,
                  cellSize.getValue(),
                  cellSize.getValue(),
                  null
          );
        }
        board.requestFocus();
      }
    };
    addMouseListener(modelAction);
    addMouseListener(noModelAction);
  }
}
