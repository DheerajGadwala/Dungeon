package guicontroller;

import dungeoncontroller.GameFeatures;
import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeonview.GameView;

import java.io.IOException;

/**
 * This is a mock of the view.
 * When a functionality of this is called by a controller,
 * we simply log the unique code and what functionality was called.
 * If a new model is set, we log the details of the new model using the
 * read only model received.
 */
class MockView implements GameView {

  private final Appendable viewLog;
  private final String uniqueCode;

  /**
   * Constructor of this mock view.
   * @param viewLog view log.
   * @param uniqueCode unique code assigned.
   */
  public MockView(Appendable viewLog, String uniqueCode) {
    if (viewLog == null) {
      throw new IllegalArgumentException("Game log can not be null.");
    }
    if (uniqueCode == null || uniqueCode.equals("")) {
      throw new IllegalArgumentException("Unique code can not be null.");
    }
    this.viewLog = viewLog;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void showHome() {
    // not used
  }

  @Override
  public void showSettings() {
    // not used
  }

  @Override
  public void showGame() {
    // not used
  }

  @Override
  public void startNewGame(GameFeatures controller, ReadOnlyGameWithObstacles readOnlyGame) {
    try {
      viewLog.append(uniqueCode);
      viewLog.append("New Game Started\n");
      viewLog.append(readOnlyGame.toString()).append("\n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void refresh() {
    try {
      viewLog.append(uniqueCode);
      viewLog.append("Refreshed.\n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void setFeatures(GameFeatures controller) {
    try {
      viewLog.append(uniqueCode);
      viewLog.append("Game features set.\n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void showMessage(String message, String title) {
    try {
      viewLog.append(uniqueCode);
      viewLog.append("display message: \n");
      viewLog.append(message);
    } catch (IOException ignored) {
    }
  }

  @Override
  public void makeVisible() {
    try {
      viewLog.append(uniqueCode);
      viewLog.append("View made visible.\n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void dismiss() {
    try {
      viewLog.append(uniqueCode);
      viewLog.append("View dismissed.\n");
    } catch (IOException ignored) {
    }
  }
}
