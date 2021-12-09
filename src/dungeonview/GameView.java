package dungeonview;

import dungeoncontroller.GameFeatures;
import dungeongeneral.ReadOnlyGameWithObstacles;

/**
 * This is the view of the game.
 * A game can be created through this.
 * A game can be played through this.
 * This has a home page, a settings page and a game page.
 */
public interface GameView {

  /**
   * Shows home page of the view.
   */
  void showHome();

  /**
   * Shows settings page of the view.
   */
  void showSettings();

  /**
   * Shows game page of the view.
   */
  void showGame();

  /**
   * Starts a new game in this view.
   * @param controller controller to add listeners to the new game board.
   * @param readOnlyGame new read only game model for the view.
   */
  void startNewGame(GameFeatures controller, ReadOnlyGameWithObstacles readOnlyGame);

  /**
   * refreshes the view.
   */
  void refresh();

  /**
   * Set features for various actions performed in this view.
   * @param controller features to be set.
   */
  void setFeatures(GameFeatures controller);

  /**
   * Shows a message in the view.
   * @param message message to be displayed.
   * @param title title of the message.
   */
  void showMessage(String message, String title);

  /**
   * Make the view visible.
   */
  void makeVisible();

  /**
   * Closes the view window.
   */
  void dismiss();
}
