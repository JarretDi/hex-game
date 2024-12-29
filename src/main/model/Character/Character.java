package main.model.Character;

import java.util.HashSet;
import java.util.Set;

import main.model.Character.CharacterActions.GameAction;
import main.model.GameComponents.Damagable;
import main.model.Inventory.Inventory;
import main.model.Inventory.items.Item;
import main.model.map.GameMap;
import main.model.map.Tile;

public class Character implements Damagable {
    private String name;
    private Statboard stats;
    private ClassRole classRole;
    private Set<GameAction> characterActions;

    private double maxHp;
    private double health;

    private Inventory inventory;
    private Tile position;

    private Item mainhandItem;
    private Item offhandItem;

    // EFFECTS: Constructs a character with a given name and class, with a statboard and full hp
    public Character(String name, ClassRole classRole, Tile position) {
        this.name = name;
        this.stats = new Statboard(this);
        this.classRole = classRole;

        this.inventory = new Inventory(this);

        this.position = position;

        this.maxHp = Statboard.getMaxHpFromLevel();
        this.health = maxHp;

        this.characterActions = new HashSet<>();
        addActions();
    }

    public Character(String name, ClassRole classRole) {
        this(name, classRole, null);
    }

    private void addActions() {
        
    }

    // EFFECTS: returns the damage a character should do given their statboards and weapons
    public Damage getDamage() {
        return new Damage(stats.getStat(Statboard.STRENGTH), null);
    }

    // MODIFIES: this
    // EFFECTS: updates health to the given damage
    public void takeDamage(Damage dmg) {
        
    }

    public String getName() {
        return name;
    }

    public double getPercentHp() {
        return health / maxHp;
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
        return position != null && position.getMap() == map;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onDestroy'");
    }
}
