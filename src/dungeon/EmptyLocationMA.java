package dungeon;

public class EmptyLocationMA extends EmptyLocation implements LocationNodeMA{

  @Override
  public void setMonster(Monster monster) throws IllegalArgumentException {
    throw new IllegalStateException("Can not add monster to sentinel.");
  }

  @Override
  public boolean hasMonster() {
    return false;
  }
}
