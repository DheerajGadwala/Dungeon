package dungeon;

import general.Direction;
import general.MatrixPosition;

import java.util.HashMap;

class LocationMA extends Location implements LocationNodeMA {

  private Monster monster;

  public LocationMA(MatrixPosition position, HashMap<Direction, LocationNode> neighbours) {
    super(position, neighbours);
  }

  @Override
  public void setMonster(Monster monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("Monster can not be null");
    }
    else if (!this.isCave()) {
      throw new IllegalArgumentException("Can add monsters to caves only.");
    }
    else if (this.hasMonster()) {
      throw new IllegalArgumentException("This cave already has a monster.");
    }
    this.monster = monster;
  }

  @Override
  public boolean hasMonster() {
    return monster != null;
  }

}
