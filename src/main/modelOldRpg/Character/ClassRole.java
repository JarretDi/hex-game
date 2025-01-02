package main.modelOldRpg.Character;

import main.modelOldRpg.Character.CharacterActions.Doable;

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
