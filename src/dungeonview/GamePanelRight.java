package dungeonview;

import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ReadOnlyPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Treasure.*;

class GamePanelRight extends JPanel {

  private final MutableInteger CELL_SIZE;
  private final ZoomSettingsPanel zoomSettings;

  GamePanelRight(ReadOnlyGameWithObstacles readOnlyGame, MutableInteger cellSize) {
    CELL_SIZE = cellSize;
    setLayout(new BorderLayout());
    zoomSettings = new ZoomSettingsPanel();
    JPanel holder = new JPanel();
    holder.add(new PlayerDescriptionPanel(readOnlyGame.getPlayerDesc()));
    holder.setBackground(Color.BLACK);
    add(holder, BorderLayout.CENTER);
    add(zoomSettings, BorderLayout.NORTH);

  }

  void setFeatures(GameView gameView) {
    zoomSettings.setFeatures(gameView);
  }

  private static class PlayerDescriptionPanel extends JPanel {

    private final ReadOnlyPlayer player;

    PlayerDescriptionPanel(ReadOnlyPlayer player) {
      this.player = player;
      setLayout(new BorderLayout());
      setPreferredSize(new Dimension(180, 250));
      setAlignmentY(0.5f);
      setAlignmentX(0.5f);
    }

    @Override
    public void paintComponent(Graphics gOld) {
      Graphics2D g = (Graphics2D) gOld;
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      g.setFont(new Font("Rockwell", Font.BOLD, 16));
      try {
        g.setColor(Color.WHITE);
        g.drawImage(ImageFetcher.getHealth(), 35, 20, 30, 30, null);
        g.drawString(String.valueOf(player.getHealth()), 120, 40);
        g.drawImage(ImageFetcher.getItem(CROOKED_ARROW), 35, 60, 30, 30, null);
        g.drawString(String.valueOf(player.getItems().get(CROOKED_ARROW)), 120, 80);
        g.drawImage(ImageFetcher.getTreasure(RUBY), 35, 110, 30, 30, null);
        g.drawString(String.valueOf(player.getTreasure().get(RUBY)), 120, 130);
        g.drawImage(ImageFetcher.getTreasure(SAPPHIRE), 35, 160, 30, 30, null);
        g.drawString(String.valueOf(player.getTreasure().get(SAPPHIRE)), 120, 180);
        g.drawImage(ImageFetcher.getTreasure(DIAMOND), 35, 210, 30, 30, null);
        g.drawString(String.valueOf(player.getTreasure().get(DIAMOND)), 120, 230);
      } catch (IOException ignored) {
      }
    }

  }

  private class ZoomSettingsPanel extends JPanel {

    private final JButton zoomIn;
    private final JButton zoomOut;

    ZoomSettingsPanel() {
      setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
      this.zoomIn = new StyledButton("Zoom In");
      this.zoomOut = new StyledButton("Zoom out");
      add(zoomIn);
      add(
              Box.createRigidArea(
                      new Dimension(20, 100)
              )
      );
      setBackground(Color.BLACK);
      add(zoomOut);
    }

    void setFeatures(GameView gameView) {
      zoomIn.addActionListener(e -> {
        if (CELL_SIZE.getValue() < 120) {
          CELL_SIZE.add(5);
          gameView.refresh();
        }
      });

      zoomOut.addActionListener(e -> {
        if (CELL_SIZE.getValue() > 60) {
          CELL_SIZE.add(-5);
          gameView.refresh();
        }
      });
    }
  }
}
