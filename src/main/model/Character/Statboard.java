package main.model.Character;

public class Statboard {
    private int level;

    private int vitality;
    private int focus;

    private int strength;
    private int agility;
    private int wits;

    private int charm;

    public Statboard(int level, int vitality, int focus, int strength, int agility, int wits, int charm) {
        this.level = level;

        this.vitality = vitality;
        this.focus = focus;
        this.strength = strength;
        this.agility = agility;
        this.wits = wits;
        this.charm = charm;
    }

    public static double getMaxHpFromLevel() {
        return 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        level++;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int newVitality) {
        if (newVitality < 0) throw new IllegalArgumentException();
        this.vitality = newVitality;
    } 

    public int getFocus() {
        return focus;
    }

    public void setFocus(int newFocus) {
        if (newFocus < 0) throw new IllegalArgumentException();
        this.focus = newFocus;
    } 

    public int getStrength() {
        return strength;
    }

    public void setStrength(int newStrength) {
        if (newStrength < 0) throw new IllegalArgumentException();
        this.strength = newStrength;
    } 

    public int getAgility() {
        return agility;
    }

    public void setAgility(int newAgility) {
        if (newAgility < 0) throw new IllegalArgumentException();
        this.agility = newAgility;
    } 

    public int getWits() {
        return wits;
    }

    public void setWits(int newWits) {
        if (newWits < 0) throw new IllegalArgumentException();
        this.wits = newWits;
    } 

    public int getCharm() {
        return charm;
    }

    public void setCharm(int newCharm) {
        if (newCharm < 0) throw new IllegalArgumentException();
        this.charm = newCharm;
    } 

}
