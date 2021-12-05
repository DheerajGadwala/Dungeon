package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.ReadOnlyGame;
import dungeongeneral.ReadOnlyGameWithObstacles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static dungeongeneral.Direction.*;
import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Treasure.*;

class GamePanel extends CenteredPanel {

  private final ReadOnlyGameWithObstacles readOnlyGame;
  private JButton home;
  private JScrollPane scroller;
  private JPanel grid;
  private JPanel gridContainer;
  private final MutableInteger CELL_SIZE;
  private final GamePanelRight rightPanel;
  private final List<Cell> cells;

  public GamePanel(ReadOnlyGameWithObstacles readOnlyGame) {
    JLabel title = new JLabel("Game", SwingConstants.CENTER);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setFont(new Font("Rockwell", Font.BOLD, 16));
    title.setBorder(new EmptyBorder(15, 0, 15, 0));
    this.CELL_SIZE = new MutableInteger(80);
    home = new StyledButton("Home");
    home.setAlignmentX(Component.CENTER_ALIGNMENT);
    addTop(title);
    addBottom(home);
    this.readOnlyGame = readOnlyGame;
    right.setPreferredSize(new Dimension(250, 50));
    this.rightPanel = new GamePanelRight(readOnlyGame, CELL_SIZE);
    right.add(this.rightPanel);
    this.cells = new ArrayList<>();
    buildGrid();
  }

  void setFeatures(GameView gameView, GameFeatures controller) {
    home.addActionListener((ignored) -> gameView.showHome());
    setFocusable(true);
    requestFocus();
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
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), CROOKED_ARROW);
    getActionMap().put(WEST, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.move(WEST);
      }
    });
    getActionMap().put(EAST, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.move(EAST);
      }
    });
    getActionMap().put(NORTH, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.move(NORTH);
      }
    });
    getActionMap().put(SOUTH, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.move(SOUTH);
      }
    });
    getActionMap().put(RUBY, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.pickTreasure(RUBY);
      }
    });
    getActionMap().put(SAPPHIRE, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.pickTreasure(SAPPHIRE);
      }
    });
    getActionMap().put(DIAMOND, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.pickTreasure(DIAMOND);
      }
    });
    getActionMap().put(CROOKED_ARROW, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.pickItem(CROOKED_ARROW);
      }
    });
    rightPanel.setFeatures(gameView);
  }

  private void buildGrid() {
    center.setLayout(new BorderLayout());
    JPanel gridCenter = new JPanel();
    gridCenter.setLayout(new FlowLayout());
    gridContainer = new JPanel();
    gridContainer.setLayout(new BorderLayout());
    gridContainer.setPreferredSize(new Dimension(
            readOnlyGame.getColumnCount() * CELL_SIZE.getValue(),
            readOnlyGame.getRowCount() * CELL_SIZE.getValue()
    ));
    JPanel grid = new JPanel();
    grid.setLayout(
            new GridLayout(
            readOnlyGame.getRowCount(),
            readOnlyGame.getColumnCount()
    ));
    for (int i = 0; i < readOnlyGame.getRowCount(); i++) {
      for (int j = 0; j < readOnlyGame.getColumnCount(); j++) {
        Cell cell = new Cell(new Coordinate(i, j), readOnlyGame, CELL_SIZE);
        grid.add(cell);
        cells.add(cell);
      }
    }
    gridContainer.add(grid);
    JScrollPane scroller = new JScrollPane(
            gridContainer,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
    );
    scroller.getVerticalScrollBar().setUnitIncrement(16);
    gridCenter.add(scroller);
    center.add(gridCenter);
    this.scroller = scroller;
    this.grid = gridCenter;
  }

  private void dynamicAdjust() {
    int OFFSET_2 = -10;
    int OFFSET_1 = 20;
    if (readOnlyGame.getRowCount() * CELL_SIZE.getValue() + OFFSET_1
        > grid.getHeight() + OFFSET_2 && readOnlyGame.getColumnCount()
        * CELL_SIZE.getValue() + OFFSET_1 > grid.getWidth() + OFFSET_2) {
      scroller.setPreferredSize(
              new Dimension(
                      grid.getWidth() + OFFSET_2,
                      grid.getHeight() + OFFSET_2
              )
      );
    }
    else if (readOnlyGame.getRowCount() * CELL_SIZE.getValue()
            + OFFSET_1 > grid.getHeight() + OFFSET_2) {
      scroller.setPreferredSize(
              new Dimension(
                      readOnlyGame.getColumnCount() * CELL_SIZE.getValue() + OFFSET_1,
                      grid.getHeight() + OFFSET_2
              )
      );
    }
    else if (readOnlyGame.getColumnCount() * CELL_SIZE.getValue()
            + OFFSET_1 > grid.getWidth() + OFFSET_2) {
      scroller.setPreferredSize(
              new Dimension(
                      grid.getWidth() + OFFSET_2,
                      readOnlyGame.getRowCount() * CELL_SIZE.getValue() + OFFSET_1
              )
      );
    }
    else {
      scroller.setPreferredSize(
              new Dimension(
                      readOnlyGame.getColumnCount() * CELL_SIZE.getValue() + OFFSET_1,
                      readOnlyGame.getRowCount() * CELL_SIZE.getValue() + OFFSET_1
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
            readOnlyGame.getColumnCount() * CELL_SIZE.getValue(),
            readOnlyGame.getRowCount() * CELL_SIZE.getValue()
    ));
    dynamicAdjust();
  }

  void refresh() {
    revalidate();
    repaint();
  }


}
