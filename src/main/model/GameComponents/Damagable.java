package main.model.GameComponents;

import main.model.Character.Damage;

/*
 * Represent something that has health and can take damage
 */
public interface Damagable {
    // EFFECTS: Returns the current health of a damagable
    public double getHealth();

    // MODIFIES: this
    // EFFECTS: Takes the given damage
    public void takeDamage(Damage dmg);

    // MODIFIES: this
    // EFFECTS: Action to take when hp reaches 0
    public void onDestroy();
}
