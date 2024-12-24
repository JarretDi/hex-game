package model.Character.CharacterActions;

import java.util.Set;

public class AttackAction extends GameAction {

    public AttackAction(String actionName) {
        super("Attack");
    }

    // EFFECTS: attacks the reciever(s) based on the equipped weapon of the character
    @Override
    public void doAction(Character giver, Set<Character> receivers) {
        
    }

}
