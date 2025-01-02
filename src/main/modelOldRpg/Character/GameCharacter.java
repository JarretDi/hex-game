package main.modelOldRpg.Character;

import java.util.HashSet;
import java.util.Set;

import main.modelOldRpg.map.GameMap;
import main.modelOldRpg.map.Tile;
import main.modelOldRpg.Character.CharacterActions.Doable;
import main.modelOldRpg.GameComponents.AttackComponent;
import main.modelOldRpg.GameComponents.Damagable;
import main.modelOldRpg.Inventory.Inventory;
import main.modelOldRpg.Inventory.items.Weapon;

public class GameCharacter implements Damagable {
    private String name;
    private Statboard stats;
    private ClassRole classRole;
    private Set<Doable> characterActions;

    private double maxHp;
    private double health;

    private Inventory inventory;
    private Equipment equipment;

    private Tile position;

    // EFFECTS: Constructs a character with a given name and class, with a statboard and full hp
    public GameCharacter(String name, ClassRole classRole, Tile position) {
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

    public GameCharacter(String name, ClassRole classRole) {
        this(name, classRole, null);
    }

    private void addActions() {
        
    }

    // EFFECTS: returns the damage a character should do given their statboards and weapons
    public AttackComponent getEffectiveDamage() {
        AttackComponent weaponAC = equipment.getMainhandItem().getAttackComponent();
        int strength = stats.getStat(Statboard.STRENGTH);

        return new AttackComponent(strength + weaponAC.getDamage(), weaponAC.getDamageType(), weaponAC.getAreaType());
    }
    
    public Weapon getMainhand() {
        return equipment.getMainhandItem();
    }

    // MODIFIES: this
    // EFFECTS: updates health to the given damage
    public void takeDamage(AttackComponent ac) {
        int damage = ac.getDamage();
        health -= damage;
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
