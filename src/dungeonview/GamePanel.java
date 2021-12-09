package dungeonview;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Treasure.DIAMOND;
import static dungeongeneral.Treasure.RUBY;
import static dungeongeneral.Treasure.SAPPHIRE;

import dungeoncontroller.GameFeatures;
import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.Treasure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class GamePanel extends CenteredPanel {

  private final ReadOnlyGameWithObstacles game;
  private final JButton homeButton;
  private JScrollPane scroller;
  private JPanel grid;
  private JPanel gridContainer;
  private final MutableInteger cellSize;
  private final GamePanelRight rightPanel;
  private final Cell[][] cells;
  private boolean shootNext;
  private Component leftPadding;
  private Component rightPadding;
  private JLabel finalMessage;

  GamePanel(ReadOnlyGameWithObstacles game) {
    JLabel title = new JLabel("Game", SwingConstants.CENTER);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setFont(new Font("Rockwell", Font.BOLD, 16));
    title.setBorder(new EmptyBorder(15, 0, 15, 0));
    title.setForeground(Color.WHITE);
    this.cellSize = new MutableInteger(80);
    homeButton = new StyledButton("Home");
    homeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
    addTop(title);
    addLeft(homeButton);
    left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
    left.setPreferredSize(new Dimension(100, 200));
    this.game = game;
    right.setPreferredSize(new Dimension(250, 50));
    this.rightPanel = new GamePanelRight(game, cellSize);
    right.add(this.rightPanel);
    this.cells = new Cell[game.getRowCount()][game.getColumnCount()];
    buildGrid();
    center.setBackground(Color.BLACK);
    left.setBackground(Color.BLACK);
    right.setBackground(Color.BLACK);
    bottom.setBackground(Color.BLACK);
    top.setBackground(Color.BLACK);
    setFocusable(true);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        requestFocus();
      }
    });
    displayGameOver();
  }

  private void displayGameOver() {
    finalMessage = new JLabel("");
    finalMessage.setFont(new Font("Rockwell", Font.BOLD, 20));
    bottom.setPreferredSize(new Dimension(50, 100));
    bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
    finalMessage.setForeground(Color.WHITE);
    finalMessage.setAlignmentX(0.5f);
    leftPadding =  Box.createRigidArea(new Dimension(30, 0));
    bottom.add(leftPadding);
    bottom.add(finalMessage);
    bottom.add(
            Box.createRigidArea(
                    new Dimension(30, 0)
            )
    );
    rightPadding = Box.createRigidArea(new Dimension(30, 0));
    bottom.add(rightPadding);
  }

  void setFeatures(GameView gameView, GameFeatures controller) {
    requestFocus();
    homeButton.addActionListener((ignored) -> gameView.showHome());
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), WEST);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), EAST);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), NORTH);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), SOUTH);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), RUBY);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), SAPPHIRE);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), DIAMOND);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), CROOKED_ARROW);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "attack");
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "shoot");
    for (Direction direction: Direction.values()) {
      getActionMap().put(direction, new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (shootNext) {
            try {
              int distance = Integer.parseInt(JOptionPane.showInputDialog("Enter Distance"));
              controller.shoot(direction, distance);
            }
            catch (NumberFormatException nfe) {
              gameView.showMessage("Invalid Distance", "Error");
            }
            //controller.shoot(direction, shootDistance.getValue());
          }
          else {
            controller.move(direction);
          }
          requestFocus();
          shootNext = false;
        }
      });
    }
    for (Treasure treasure: Treasure.values()) {
      getActionMap().put(treasure, new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          shootNext = false;
          controller.pickTreasure(treasure);
          requestFocus();
        }
      });
    }
    getActionMap().put(CROOKED_ARROW, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shootNext = false;
        controller.pickItem(CROOKED_ARROW);
        requestFocus();
      }
    });
    getActionMap().put("attack", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shootNext = false;
        controller.attack();
        requestFocus();
      }
    });
    getActionMap().put("shoot", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shootNext = true;
        requestFocus();
      }
    });
    for (int i = 0; i < game.getRowCount(); i++) {
      for (int j = 0; j < game.getColumnCount(); j++) {
        cells[i][j].setFeatures(controller, this);
      }
    }
    rightPanel.setFeatures(gameView);
  }

  private void buildGrid() {
    center.setLayout(new BorderLayout());
    JPanel gridCenter = new JPanel();
    gridCenter.setLayout(new FlowLayout());
    gridCenter.setBackground(Color.BLACK);
    gridContainer = new JPanel();
    gridContainer.setLayout(new BorderLayout());
    gridContainer.setPreferredSize(new Dimension(
            game.getColumnCount() * cellSize.getValue(),
            game.getRowCount() * cellSize.getValue()
    ));
    gridContainer.setBackground(Color.BLACK);
    JPanel grid = new JPanel();
    grid.setLayout(
            new GridLayout(
            game.getRowCount(),
            game.getColumnCount()
    ));
    for (int i = 0; i < game.getRowCount(); i++) {
      for (int j = 0; j < game.getColumnCount(); j++) {
        Cell cell = new Cell(new Coordinate(i, j), game, cellSize);
        grid.add(cell);
        cells[i][j] = cell;
      }
    }
    gridContainer.add(grid);
    JScrollPane scroller = new StyledScrollPane(gridContainer);
    gridCenter.add(scroller);
    center.add(gridCenter);
    this.scroller = scroller;
    this.grid = gridCenter;
  }

  private void dynamicAdjust() {
    int offset_1 = 0;
    int offset_2 = -10;
    if (game.getRowCount() * cellSize.getValue() + offset_1
        > grid.getHeight() + offset_2 && game.getColumnCount()
        * cellSize.getValue() + offset_1 > grid.getWidth() + offset_2) {
      scroller.setPreferredSize(
              new Dimension(
                      grid.getWidth() + offset_2,
                      grid.getHeight() + offset_2
              )
      );
    }
    else if (game.getRowCount() * cellSize.getValue()
            + offset_1 > grid.getHeight() + offset_2) {
      scroller.setPreferredSize(
              new Dimension(
                      game.getColumnCount() * cellSize.getValue() + offset_1,
                      grid.getHeight() + offset_2
              )
      );
    }
    else if (game.getColumnCount() * cellSize.getValue()
            + offset_1 > grid.getWidth() + offset_2) {
      scroller.setPreferredSize(
              new Dimension(
                      grid.getWidth() + offset_2,
                      game.getRowCount() * cellSize.getValue() + offset_1
              )
      );
    }
    else {
      scroller.setPreferredSize(
              new Dimension(
                      game.getColumnCount() * cellSize.getValue() + offset_1,
                      game.getRowCount() * cellSize.getValue() + offset_1
              )
      );
    }
    gridContainer.revalidate();
    gridContainer.repaint();
    grid.revalidate();
    grid.repaint();
    scroller.revalidate();
    scroller.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    gridContainer.setPreferredSize(new Dimension(
            game.getColumnCount() * cellSize.getValue(),
            game.getRowCount() * cellSize.getValue()
    ));
    dynamicAdjust();
    leftPadding.setPreferredSize(new Dimension((int) (bottom.getWidth() * 0.45), 0));
    leftPadding.revalidate();
    leftPadding.repaint();
    rightPadding.setPreferredSize(new Dimension((int) (bottom.getWidth() * 0.55), 0));
    rightPadding.revalidate();
    rightPadding.repaint();
    if (game.isGameOver()) {
      if (game.hasPlayerWon()) {
        finalMessage.setText("Game Over! You have escaped the dungeon!");
      }
      else {
        finalMessage.setText("Game Over! You are dead!");
      }
    }
  }
}
