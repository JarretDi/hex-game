package main.modelOldRpg.GameComponents;

public class AttackComponent {
    public enum DamageType {
        Sharp,
        Blunt
    }

    public enum AreaType {
        Single,
        Line,
        Cleave,
        Cone,
        Radius,
    }

    private int damage;
    private DamageType damageType;
    private AreaType areaType;

    public AttackComponent(int damage, DamageType damageType, AreaType areaType) {
        this.damage = damage;
        this.damageType = damageType;
        this.areaType = areaType;
    }

    public int getDamage() {
        return damage;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public AreaType getAreaType() {
        return areaType;
    }
}
