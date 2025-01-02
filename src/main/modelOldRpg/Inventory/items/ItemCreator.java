package main.modelOldRpg.Inventory.items;

import main.modelOldRpg.Inventory.exceptions.InvalidNumberException;
import main.modelOldRpg.Inventory.exceptions.InvalidTypeException;

// Represents a class used to create a subtype of item when given a type as a String
public class ItemCreator {

    // EFFECT: helper that creates and returns an, item based on parameters
    // throws: InvalidTypeException if given invalid type
    // InvalidNumberException if given values for value/weight are non-negative
    public static Item createItemFromInput(
            String name, String type, int itemValue, int itemWeight, String desc, Boolean favourite)
            throws InvalidNumberException, InvalidTypeException {
                
        if (itemValue < 0 || itemWeight < 0) {
            throw new InvalidNumberException();
        }
        switch (type) {
            case "Weapon":
            case "w":
                return new Weapon(name, itemValue, itemWeight, desc, favourite);
            case "Armour":
            case "a":
                return new Armour(name, itemValue, itemWeight, desc, favourite);
            case "Consumable":
                return new Consumable(name, itemValue, itemWeight, desc, favourite);
            case "Currency":
                return new Currency(name, itemValue, itemWeight, desc, favourite);
            case "Misc":
            case "m":
            case "":
                return new Misc(name, itemValue, itemWeight, desc, favourite);
            default:
                throw new InvalidTypeException();
        }
    }

    // EFFECT: helper that creates and returns an item based on only name and type
    public static Item createItemFromInput(String name, String type) throws InvalidNumberException, InvalidTypeException {
        return createItemFromInput(name, type, 0, 0, "", false);
    }
}
