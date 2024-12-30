package main.model.Character.CharacterActions;

import java.util.Set;

import main.model.Character.GameCharacter;
import main.model.GameComponents.AttackComponent;
import main.model.map.Tile;

public class AttackAction implements Doable {
    // EFFECTS: attacks the reciever(s) based on the equipped weapon of the character
    @Override
    public void doAction(GameCharacter giver, Set<Tile> receivers) {
        AttackComponent ac = giver.getEffectiveDamage();

        for (Tile tile:receivers) {
            tile.getCharacter().takeDamage(ac);
        }
    }

    @Override
    public String getActionName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActionName'");
    }

}
