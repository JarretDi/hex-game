package model.Character;

import model.Character.CharacterActions.GameAction;

public abstract class ClassRole {
    private String className;
    private GameAction classAction;
    
    public String getClassName() {
        return className;
    }

    public GameAction getClassAction() {
        return classAction;
    }
}
