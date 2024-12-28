package main.model.Character;

import java.util.HashSet;
import java.util.Set;

import main.model.Character.CharacterActions.GameAction;
import main.model.Inventory.Inventory;
import main.model.Inventory.items.Item;
import main.model.map.GameMap;
import main.model.map.Tile;

public class Character {
    private String name;
    private ClassRole classRole;
    private Set<GameAction> characterActions;

    private int level;
    private double maxHp;
    private double hp;

    private Inventory inventory;
    private Tile position;

    private Item mainhandItem;
    private Item offhandItem;

    // EFFECTS: Constructs a character with a given name and class, with zero levels and full hp
    public Character(String name, ClassRole classRole, Tile position) {
        this.name = name;
        this.classRole = classRole;

        this.inventory = new Inventory(this);

        this.level = 0;
        this.position = position;

        this.maxHp = Statboard.getMaxHpFromLevel();
        this.hp = maxHp;

        this.characterActions = new HashSet<>();
        addActions();
    }

    public Character(String name, ClassRole classRole) {
        this(name, classRole, null);
    }

    private void addActions() {
        
    }

    // MODIFIES: this
    // EFFECTS: updates health to the given damage
    public void takeDamage(Damage dmg) {

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

    public Tile getPosition() {
        return position;
    }

    // MODIFIES: this, newPosition
    // EFFECTS: if newPosition is empty:
    //          moves character to newPosition
    //          sets the character of newPosition to this
    //          removes this character from the old posiion
    public void setPosition(Tile newPosition) {
        Tile oldPosition = this.position;

        if (this.position != newPosition) {
            this.position = newPosition;

            newPosition.setCharacter(this);
            if (oldPosition != null) oldPosition.removeCharacter();
        }
    }

    public void removePosition() {
        this.position = null;
    }

    public Boolean isOnMap(GameMap map) {
        // TODO: when tiles are linked to maps
        return position != null;
    }
}
