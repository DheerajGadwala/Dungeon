package dungeonview;

import dungeongeneral.Coordinate;
import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ReadOnlyLocation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

class GamePanel extends CenteredPanel {

  private final ReadOnlyGameWithObstacles readOnlyGame;
  private JButton home;

  public GamePanel(ReadOnlyGameWithObstacles readOnlyGame) {
    JLabel title = new JLabel("Game", SwingConstants.CENTER);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setFont(new Font("Rockwell", Font.BOLD, 16));
    title.setBorder(new EmptyBorder(15, 0, 15, 0));
    home = new StyledButton("Home");
    home.setAlignmentX(Component.CENTER_ALIGNMENT);
    addTop(title);
    addBottom(home);
    this.readOnlyGame = readOnlyGame;
    buildGrid();
  }

  void setFeatures(GameView gameView) {
    home.addActionListener((ignored) -> gameView.showHome());
  }

  private void buildGrid() {
    center.setLayout(new BorderLayout());
    JPanel gridCenter = new JPanel();
    gridCenter.setLayout(new BoxLayout(gridCenter, BoxLayout.PAGE_AXIS));
    gridCenter.setBackground(Color.BLACK);
    JPanel gridContainer = new JPanel(new BorderLayout());
    gridContainer.setBackground(Color.GREEN);
    JPanel grid = new JPanel();
    grid.setLayout(
            new GridLayout(
            readOnlyGame.getRowCount(),
            readOnlyGame.getColumnCount()
    ));
    int size = 60;
    gridContainer.setPreferredSize(
            new Dimension(
                    readOnlyGame.getColumnCount() * size,
                    readOnlyGame.getRowCount() * size
            )
    );
    for (int i = 0; i < readOnlyGame.getRowCount(); i++) {
      for (int j = 0; j < readOnlyGame.getColumnCount(); j++) {
        JPanel cell = new Cell(readOnlyGame.getLocation(new Coordinate(i, j)));
        grid.add(cell);
      }
    }
    JScrollPane scroller = new JScrollPane(gridCenter);
    scroller.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
    );
    scroller.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
    );
    scroller.getVerticalScrollBar().setUnitIncrement(16);
    gridContainer.add(grid, BorderLayout.CENTER);
    gridCenter.add(gridContainer, BorderLayout.CENTER);
    center.add(scroller, BorderLayout.CENTER);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
