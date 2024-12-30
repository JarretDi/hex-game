package main.model.Character.CharacterActions;

import java.util.Set;

import main.model.Character.GameCharacter;
import main.model.map.Tile;

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
