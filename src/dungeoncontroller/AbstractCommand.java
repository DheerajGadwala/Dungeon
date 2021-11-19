package dungeoncontroller;

abstract class AbstractCommand implements Command {
  protected final Appendable out;

  protected AbstractCommand(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("appendable can not be null");
    }
    this.out = out;
  }
}
