package main.model.Character;

import main.model.Character.CharacterActions.Doable;

public abstract class ClassRole {
    private String className;
    private Doable classAction;
    
    public String getClassName() {
        return className;
    }

    public Doable getClassAction() {
        return classAction;
    }
}
