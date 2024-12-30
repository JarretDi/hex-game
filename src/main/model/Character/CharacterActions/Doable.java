package main.model.Character.CharacterActions;

import java.util.Set;

import main.model.Character.GameCharacter;
import main.model.map.Tile;

/*
 * Class that represents the actions a character can take during the game
 */
public interface Doable {
    public String getActionName();

    // EFFECTS: Does the action
    public void doAction(GameCharacter giver, Set<Tile> receivers);
}
