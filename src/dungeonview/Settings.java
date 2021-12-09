package dungeonview;

import dungeoncontroller.GameFeatures;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This is the Settings panel.
 */
class Settings extends CenteredPanel {

  private final List<TextInput> textInputs;
  private final RadioInput wrap;
  private final JButton start;
  private final JButton back;

  /**
   * Constructor for the settings panel.
   */
  public Settings() {
    JLabel title = new JLabel("GAME SETTINGS", SwingConstants.CENTER);
    title.setAlignmentX(0.5f);
    title.setFont(new Font("Rockwell", Font.BOLD, 16));
    title.setBorder(new EmptyBorder(15, 0, 15, 0));
    Predicate<String> matcher = (input) -> input.matches("[-]?[1-9][0-9]*") || input.matches("[0]");
    textInputs = new ArrayList<>();
    textInputs.add(
            new TextInput(
                    "Rows",
                    "5",
                    matcher
            )
    );
    textInputs.add(
            new TextInput(
                    "Columns",
                    "5",
                    matcher
            )
    );
    textInputs.add(
            new TextInput(
                    "Percentage",
                    "50",
                    matcher
            )
    );
    textInputs.add(
            new TextInput(
                    "Difficulty",
                    "3",
                    matcher
            )
    );
    textInputs.add(
            new TextInput(
                    "Interconnectivity",
                    "2",
                    matcher
            )
    );
    wrap = new RadioInput(
            "Enable Wrap",
            "Yes", "No"
    );
    addCenter(title);
    addCenter(textInputs.get(0));
    addCenter(textInputs.get(1));
    addCenter(textInputs.get(2));
    addCenter(textInputs.get(3));
    addCenter(wrap);
    addCenter(textInputs.get(4));
    addCenter(
            Box.createRigidArea(
                    new Dimension(0, 15)
            )
    );
    JPanel buttons = new JPanel();
    back = new StyledButton("Home");
    buttons.add(back);
    buttons.add(
            Box.createRigidArea(
                    new Dimension(40, 0)
            )
    );
    start = new StyledButton("Start");
    buttons.add(start);
    addCenter(buttons);
  }

  /**
   * Set features.
   * We add listener to the submit button here.
   * @param controller Game features object.
   */
  void setFeatures(GameFeatures controller, GameView gameView) {
    start.addActionListener((ignored) -> {
      boolean hasErrors = false;
      for (TextInput t: textInputs) {
        if (!t.validateInput()) {
          JOptionPane.showMessageDialog(
                  this,
                  "Please enter a valid integer for " + t.getText()
          );
          hasErrors = true;
        }
      }
      if (wrap.getSelection() == null) {
        JOptionPane.showMessageDialog(
                this,
                "Please select an option for " + wrap.getText()
        );
        hasErrors = true;
      }
      if (!hasErrors) {
        int rows = Integer.parseInt(textInputs.get(0).getInput());
        int columns = Integer.parseInt(textInputs.get(1).getInput());
        int percentage = Integer.parseInt(textInputs.get(2).getInput());
        int difficulty = Integer.parseInt(textInputs.get(3).getInput());
        boolean enableWrap = wrap.getSelection().equals("Yes");
        int interconnectivity = Integer.parseInt(textInputs.get(4).getInput());
        controller.startNewGame(
                rows, columns, percentage,
                difficulty, enableWrap,
                interconnectivity);
      }
    });
    back.addActionListener((ignored) -> {
      gameView.showHome();
    });
  }
}
