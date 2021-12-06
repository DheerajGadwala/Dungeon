package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static dungeongeneral.Direction.*;
import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Treasure.*;

class GamePanel extends CenteredPanel {

  private final ReadOnlyGameWithObstacles game;
  private final JButton homeButton;
  private JScrollPane scroller;
  private JPanel grid;
  private JPanel gridContainer;
  private final MutableInteger CELL_SIZE;
  private final GamePanelRight rightPanel;
  private final TextInputForBoard shootDistance;
  private final DirectionRadios shootDirection;
  private final Cell[][] cells;
  private boolean shootNext;

  GamePanel(ReadOnlyGameWithObstacles game) {
    JLabel title = new JLabel("Game", SwingConstants.CENTER);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setFont(new Font("Rockwell", Font.BOLD, 16));
    title.setBorder(new EmptyBorder(15, 0, 15, 0));
    title.setForeground(Color.WHITE);
    this.CELL_SIZE = new MutableInteger(80);
    homeButton = new StyledButton("Home");
    homeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
    addTop(title);
    addLeft(homeButton);
    left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
    left.setPreferredSize(new Dimension(100, 200));
    this.game = game;
    right.setPreferredSize(new Dimension(250, 50));
    this.rightPanel = new GamePanelRight(game, CELL_SIZE);
    right.add(this.rightPanel);
    bottom.setPreferredSize(new Dimension(50, 150));
    bottom.setLayout(new BorderLayout());
    GamePanelBottom bottomPanel = new GamePanelBottom(game, this);
    bottom.add(bottomPanel, BorderLayout.CENTER);
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
    shootDistance = new TextInputForBoard("Direction", "1", this);
    shootDirection = new DirectionRadios(this);
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
            controller.shoot(direction, shootDistance.getValue());
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
            game.getColumnCount() * CELL_SIZE.getValue(),
            game.getRowCount() * CELL_SIZE.getValue()
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
        Cell cell = new Cell(new Coordinate(i, j), game, CELL_SIZE);
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
    int OFFSET_2 = -10;
    int OFFSET_1 = 0;
    if (game.getRowCount() * CELL_SIZE.getValue() + OFFSET_1
        > grid.getHeight() + OFFSET_2 && game.getColumnCount()
        * CELL_SIZE.getValue() + OFFSET_1 > grid.getWidth() + OFFSET_2) {
      scroller.setPreferredSize(
              new Dimension(
                      grid.getWidth() + OFFSET_2,
                      grid.getHeight() + OFFSET_2
              )
      );
    }
    else if (game.getRowCount() * CELL_SIZE.getValue()
            + OFFSET_1 > grid.getHeight() + OFFSET_2) {
      scroller.setPreferredSize(
              new Dimension(
                      game.getColumnCount() * CELL_SIZE.getValue() + OFFSET_1,
                      grid.getHeight() + OFFSET_2
              )
      );
    }
    else if (game.getColumnCount() * CELL_SIZE.getValue()
            + OFFSET_1 > grid.getWidth() + OFFSET_2) {
      scroller.setPreferredSize(
              new Dimension(
                      grid.getWidth() + OFFSET_2,
                      game.getRowCount() * CELL_SIZE.getValue() + OFFSET_1
              )
      );
    }
    else {
      scroller.setPreferredSize(
              new Dimension(
                      game.getColumnCount() * CELL_SIZE.getValue() + OFFSET_1,
                      game.getRowCount() * CELL_SIZE.getValue() + OFFSET_1
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
            game.getColumnCount() * CELL_SIZE.getValue(),
            game.getRowCount() * CELL_SIZE.getValue()
    ));
    dynamicAdjust();
  }

  void showShotResult(ShotResult shotResult) {
    ReadOnlyPlayer player = game.getPlayerDesc();
    Coordinate coordinate = player.getCoordinates();
    Cell cell = cells[coordinate.getRow()][coordinate.getColumn()];
    cell.showSound(shotResult);
  }
}
