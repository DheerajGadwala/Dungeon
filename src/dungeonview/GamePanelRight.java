package dungeonview;

import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Treasure.DIAMOND;
import static dungeongeneral.Treasure.RUBY;
import static dungeongeneral.Treasure.SAPPHIRE;

import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ReadOnlyPlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

class GamePanelRight extends JPanel {

  private final MutableInteger cellSize;
  private final ZoomSettingsPanel zoomSettings;

  GamePanelRight(ReadOnlyGameWithObstacles readOnlyGame, MutableInteger cellSize) {
    this.cellSize = cellSize;
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
    private final ImageFetcher imageFetcher;

    PlayerDescriptionPanel(ReadOnlyPlayer player) {
      this.player = player;
      setLayout(new BorderLayout());
      setPreferredSize(new Dimension(180, 250));
      setAlignmentY(0.5f);
      setAlignmentX(0.5f);
      this.imageFetcher = new ImageFetcher();
    }

    @Override
    public void paintComponent(Graphics gOld) {
      Graphics2D g = (Graphics2D) gOld;
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      g.setFont(new Font("Rockwell", Font.BOLD, 16));
      g.setColor(Color.WHITE);
      g.drawImage(imageFetcher.getHealth(), 35, 20, 30, 30, null);
      g.drawString(String.valueOf(player.getHealth()), 120, 40);
      g.drawImage(imageFetcher.getItem(CROOKED_ARROW), 35, 60, 30, 30, null);
      g.drawString(String.valueOf(player.getItems().get(CROOKED_ARROW)), 120, 80);
      g.drawImage(imageFetcher.getTreasure(RUBY), 35, 110, 30, 30, null);
      g.drawString(String.valueOf(player.getTreasure().get(RUBY)), 120, 130);
      g.drawImage(imageFetcher.getTreasure(SAPPHIRE), 35, 160, 30, 30, null);
      g.drawString(String.valueOf(player.getTreasure().get(SAPPHIRE)), 120, 180);
      g.drawImage(imageFetcher.getTreasure(DIAMOND), 35, 210, 30, 30, null);
      g.drawString(String.valueOf(player.getTreasure().get(DIAMOND)), 120, 230);
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
        if (cellSize.getValue() < 120) {
          cellSize.add(5);
          gameView.refresh();
        }
      });

      zoomOut.addActionListener(e -> {
        if (cellSize.getValue() > 60) {
          cellSize.add(-5);
          gameView.refresh();
        }
      });
    }
  }
}
