package main.modelOldRpg.Character.CharacterActions;

import java.util.Set;

import main.modelOldRpg.Character.GameCharacter;
import main.modelOldRpg.map.Tile;

public class MoveAction implements Doable {
    @Override
    public String getActionName() {
        return "Move";
    }

    @Override
    public void doAction(GameCharacter giver, Set<Tile> receivers) {
        // TODO wait until map is determined
    }
}
