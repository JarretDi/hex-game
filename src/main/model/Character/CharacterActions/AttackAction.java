package main.model.Character.CharacterActions;

import java.util.Set;

import main.model.map.Tile;

public class AttackAction extends GameAction {

    public AttackAction(String actionName) {
        super("Attack");
    }

    // EFFECTS: attacks the reciever(s) based on the equipped weapon of the character
    @Override
    public void doAction(Character giver, Set<Tile> receivers) {
        
    }

}
