package main.model.Character;

public class Statboard {
    private Character character;

    private int level;
    private int[] stats;

    public static final int VITALITY = 0;
    public static final int WILLPOWER = 1;
    public static final int AGILITY = 2;

    public static final int STRENGTH = 3;
    public static final int MAGIC = 4;
    public static final int TECHNIQUE = 5;

    public static final int CHARM = 6;
    public static final int CUNNING = 7;
    public static final int LUCK = 8;

    public Statboard(Character character, int level, int vitality, int willpower, int agility, int strength, int magic, int technique, int charm, int cunning, int luck) {
        this.character = character;
        this.level = level;

        stats = new int[]{vitality, willpower, agility, strength, magic, technique, charm, cunning, luck};
    }

    public Statboard(Character character) {
        this(character, 0,0,0,0,0,0,0,0,0,0);
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

    // REQUIRES: stat is an integer from 0-8
    // EFFECTS: returns the stat associated with the given stats
   public int getStat(int stat) {
        return stats[stat];
   }

   // REQUIRES: stat is an integer from 0-8, newValue > 0
   // MODIFIES: this
   // EFFECTS: sets the given stat to a new value
   public void setStat(int stat, int newValue) {
        stats[stat] = newValue;
   }

}
