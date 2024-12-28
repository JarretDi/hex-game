package main.model.Character.CharacterActions;

import java.util.Set;

import main.model.map.Tile;

public class MoveAction extends GameAction {

    public MoveAction(String actionName) {
        super("Move");
    }

    @Override
    public void doAction(Character giver, Set<Tile> receivers) {
        // TODO wait until map is determined
    }

}
