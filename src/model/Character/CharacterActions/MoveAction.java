package model.Character.CharacterActions;

import java.util.Set;

public class MoveAction extends GameAction {

    public MoveAction(String actionName) {
        super("Move");
    }

    @Override
    public void doAction(Character giver, Set<Character> receivers) {
        // TODO wait until map is determined
    }

}
