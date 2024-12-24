package model.Character.CharacterActions;

/*
 * Class that represents the actions a character can take during the game
 */
public interface GameAction {
    public String getActionName();

    public void doAction(Character character);
}
