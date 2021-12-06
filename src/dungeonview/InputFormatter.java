package dungeonview;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

class InputFormatter extends NumberFormatter {

  InputFormatter() {
    super(getIntegerFormat());
    setValueClass(Integer.class);
    setMinimum(-Integer.MAX_VALUE);
    setMaximum(Integer.MAX_VALUE);
  }

  private static NumberFormat getIntegerFormat() {
    NumberFormat format = NumberFormat.getIntegerInstance();
    format.setGroupingUsed(false);
    return format;
  }

}
