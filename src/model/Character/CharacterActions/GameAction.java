package model.Character.CharacterActions;

import java.util.Set;

/*
 * Class that represents the actions a character can take during the game
 */
public abstract class GameAction {
    private String actionName;

    // EFFECTS: Constructs an action with given name
    public GameAction(String actionName) {
        this.actionName = actionName;
    }

    // EFFECTS: Does the action
    public abstract void doAction(Character giver, Set<Character> receivers);


    public String getActionName() {
        return actionName;
    }
}
