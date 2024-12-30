package main.model.Character;

import main.model.Inventory.items.Armour;
import main.model.Inventory.items.Item;
import main.model.Inventory.items.Weapon;

/* 
 * Represents a class representing what a character has equipped
 */
public class Equipment {
    private GameCharacter character;
    
    private Weapon mainhandItem;
    private Item offhandItem;

    private Armour headgear;
    private Armour bodygear;
    private Armour legwear;
    private Armour accessory;

    public Equipment(GameCharacter character) {
        this.character = character;
    }

    public Weapon getMainhandItem() {
        return mainhandItem;
    }

    public void setMainhandItem(Weapon mainhandItem) {
        this.mainhandItem = mainhandItem;
        if (mainhandItem.isTwoHanded()) {
            offhandItem = null;
        }
    }

    public Item getOffhandItem() {
        return offhandItem;
    }

    public void setOffhandItem(Item offhandItem) {
        this.offhandItem = offhandItem;
    }

    public Armour getHeadgear() {
        return headgear;
    }

    public void setHeadgear(Armour headgear) {
        this.headgear = headgear;
    }

    public Armour getBodygear() {
        return bodygear;
    }

    public void setBodygear(Armour bodygear) {
        this.bodygear = bodygear;
    }

    public Armour getLegwear() {
        return legwear;
    }

    public void setLegwear(Armour legwear) {
        this.legwear = legwear;
    }

    public Armour getAccessory() {
        return accessory;
    }

    public void setAccessory(Armour accessory) {
        this.accessory = accessory;
    }
    
}
