package model.Character;

import java.util.HashSet;
import java.util.Set;

import model.Character.CharacterActions.GameAction;

public class Character {
    private String name;
    private ClassRole classRole;
    private Set<GameAction> characterActions;

    private int level;
    private double maxHp;
    private double hp;

    // EFFECTS: Constructs a character with a given name and class, with zero levels and full hp
    public Character(String name, ClassRole classRole) {
        this.name = name;
        this.classRole = classRole;

        this.level = 0;

        this.maxHp = Level.getMaxHpFromLevel();
        this.hp = maxHp;

        this.characterActions = new HashSet<>();
        addActions();
    }

    private void addActions() {
        
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getPercentHp() {
        return hp / maxHp;
    }
}
