package main.model.Character;

public class Damage {
    public enum DamageType {
        Sharp,
        Blunt
    }
    
    private int damage;
    private DamageType damageType;

    public Damage(int damage, DamageType damageType) {
        this.damage = damage;
        this.damageType = damageType;
    }

    public int getDamage() {
        return damage;
    }

    public DamageType getDamageType() {
        return damageType;
    }
}
