package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import static dungeongeneral.Direction.*;
import static dungeongeneral.Item.CROOKED_ARROW;

class Cell extends JPanel {

  private final ReadOnlyGameWithObstacles game;
  private final ReadOnlyLocation location;
  private final ReadOnlyPlayer player;
  private final MutableInteger cellSize;
  private boolean discoveryEstablished;
  private double yOffset;
  private double xOffset;
  private BufferedImage currentImage;

  public Cell(Coordinate coordinate, ReadOnlyGameWithObstacles game, MutableInteger cellSize) {
    this.game = game;
    this.location = game.getLocation(coordinate);
    this.player = game.getPlayerDesc();
    this.cellSize = cellSize;
  }

  @Override
  public void paintComponent(Graphics gOld) {
    Graphics2D g = (Graphics2D) gOld;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    try {
      BufferedImage finalImage;
      if (location.isDiscovered()) {
        if (!discoveryEstablished) {
          discoveryEstablished = true;
          setOffset();
        }
        finalImage = ImageFetcher.getLocation(location.getPossibleRoutes());
        finalImage = overlay(
                finalImage, ImageFetcher.getOdour(location.getOdour()),
                (int) (cellSize.getValue() * (0.25 + xOffset)),
                (int) (cellSize.getValue() * (0.25 + yOffset)),
                (int) (cellSize.getValue() * 0.5),
                (int) (cellSize.getValue() * 0.5)
        );
        if (location.hasPit()) {
          finalImage = overlay(
                  finalImage, ImageFetcher.getPit(),
                  (int) (cellSize.getValue() * (0.325 + xOffset)),
                  (int) (cellSize.getValue() * (0.375 + yOffset)),
                  (int) (cellSize.getValue() * 0.35),
                  (int) (cellSize.getValue() * 0.35)
          );
        }
        if (player.getCoordinates().equals(location.getCoordinates())) {
          finalImage = overlay(
                  finalImage, ImageFetcher.getPlayer(),
                  (int) (cellSize.getValue() * (0.35 + xOffset)),
                  (int) (cellSize.getValue() * (0.35 + yOffset)),
                  (int) (cellSize.getValue() * 0.4),
                  (int) (cellSize.getValue() * 0.4)
          );
          if (game.thiefAtPlayerLocation()) {
            finalImage = overlay(
                    finalImage, ImageFetcher.getThief(),
                    (int) (cellSize.getValue() * (0.3 + xOffset)),
                    (int) (cellSize.getValue() * (0.3 + yOffset)),
                    (int) (cellSize.getValue() * 0.3),
                    (int) (cellSize.getValue() * 0.3)
            );
          }
          if (game.movingMonsterAliveAtPlayerLocation()) {
            finalImage = overlay(
                    finalImage, ImageFetcher.getMovingMonster(),
                    (int) (cellSize.getValue() * (0.4 + xOffset)),
                    (int) (cellSize.getValue() * (0.5 + yOffset)),
                    (int) (cellSize.getValue() * 0.3),
                    (int) (cellSize.getValue() * 0.3)
            );
          }
        }
        if (location.hasMonster()) {
          finalImage = overlay(
                  finalImage, ImageFetcher.getOtyugh(),
                  (int) (cellSize.getValue() * 0.2),
                  (int) (cellSize.getValue() * 0.5),
                  (int) (cellSize.getValue() * 0.27),
                  (int) (cellSize.getValue() * 0.27)
          );
          if (location.hasInjuredMonster()) {
            finalImage = overlay(
                    finalImage, ImageFetcher.getItem(CROOKED_ARROW),
                    (int) (cellSize.getValue() * 0.2),
                    (int) (cellSize.getValue() * 0.6),
                    (int) (cellSize.getValue() * 0.3),
                    (int) (cellSize.getValue() * 0.3)
            );
          }
          if (location.hasDeadMonster()) {
            finalImage = overlay(
                    finalImage, ImageFetcher.getItem(CROOKED_ARROW),
                    (int) (cellSize.getValue() * 0.2),
                    (int) (cellSize.getValue() * 0.6),
                    (int) (cellSize.getValue() * 0.3),
                    (int) (cellSize.getValue() * 0.3)
            );
            finalImage = overlay(
                    finalImage, ImageFetcher.getItem(CROOKED_ARROW),
                    (int) (cellSize.getValue() * 0.2),
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
                      finalImage, ImageFetcher.getTreasure(treasure),
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
                      finalImage, ImageFetcher.getItem(item),
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
        finalImage = ImageFetcher.getBlack();
      }
      this.currentImage = finalImage;
      g.drawImage(
              finalImage, 0, 0,
              cellSize.getValue(),
              cellSize.getValue(),
              null
      );
    } catch (IOException ignored) {
    }
  }

  private BufferedImage overlay(
          BufferedImage starting, BufferedImage overlay,
          int offsetX, int offsetY, int height, int width
  ) {
    BufferedImage combined = new BufferedImage(
            cellSize.getValue()*2,
            cellSize.getValue()*2,
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
                      : directions.contains(NORTH) ? 0.15 : directions.contains(SOUTH) ? -0.15
                      : 0
      );
      this.xOffset =  - (
              directions.contains(EAST) && directions.contains(WEST) ? 0
                      : directions.contains(EAST) ? -0.15 : directions.contains(WEST) ? 0.15
                      : 0
      );
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
        try {
          if (!location.isDiscovered()) {
            getGraphics().drawImage(
                    ImageFetcher.getGrey(), 0, 0,
                    cellSize.getValue(),
                    cellSize.getValue(),
                    null
            );
          }
          else if (!player.getCoordinates().equals(location.getCoordinates())){
            getGraphics().drawImage(
                    overlay(
                            currentImage,
                            ImageFetcher.getPointer(),
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
        } catch (IOException ignored) {
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!location.isDiscovered()) {
          try {
            getGraphics().drawImage(
                    ImageFetcher.getBrown(), 0, 0,
                    cellSize.getValue(),
                    cellSize.getValue(),
                    null);
          } catch (IOException ignored) {
          }
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
          try {
            getGraphics().drawImage(
                    mouseInside
                            ? ImageFetcher.getGrey()
                            : ImageFetcher.getBlack()
                    , 0, 0,
                    cellSize.getValue(),
                    cellSize.getValue(),
                    null);
          } catch (IOException ignored) {
          }
        }
        board.requestFocus();
      }
    };
    addMouseListener(modelAction);
    addMouseListener(noModelAction);
  }

  void showSound(ShotResult shotResult) {

  }
}
