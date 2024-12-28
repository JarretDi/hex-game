package main.model.map;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import main.model.Character.Character;
import main.model.GameComponents.GameComponent;

public class Tile {
    public enum TileType {
        Plains,
        Forest,
        Desert,
        Water,
        Floor
    }

    private int x;
    private int y;
    private int z;

    private TileType type;
    private GameMap map;

    private Character character;
    private Set<GameComponent> components;
    private Set<TileEffect> tileEffects;

    // EFFECTS: construct a hexagonal tile with given x, y, z coordinates
    // Throws an invalid coordinate exception if given coordinates does not satisfy
    // cubical coordinate restraints (i.e. that x+y+z=0)
    // then, determines tile type by
    // sets character to null with no components or tile effects
    public Tile(int x, int y, int z, GameMap map) throws InvalidCoordinateException {
        if (x + y + z != 0) {
            throw new InvalidCoordinateException();
        }
        this.x = x;
        this.y = y;
        this.z = z;

        this.map = map;

        type = TileType.values()[new Random().nextInt(TileType.values().length)];

        this.character = null;
        this.components = new HashSet<>();
        this.tileEffects = new HashSet<>();
    }

    public Tile(int x, int y, int z) throws InvalidCoordinateException {
        this(x, y, z, null);
    }

    // EFFECTS: returns true if this tile is passable
    public Boolean isPassable() {
        // stub
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public TileType getType() {
        return type;
    }

    public Character getCharacter() {
        return character;
    }

    // MODIFIES: this
    // EFFECTS: sets this character to given character
    //          additionally sets newCharacter's position to this
    public void setCharacter(Character newCharacter) {
        if (this.character != newCharacter) {
            this.character = newCharacter;
            newCharacter.setPosition(this);
        }
    }

    public void removeCharacter() {
        this.character = null;
    }

    public Set<GameComponent> getComponents() {
        return components;
    }

    public Set<TileEffect> getTileEffects() {
        return tileEffects;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z + "\t" + type + "\t| " + character;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    /*
     * |1 0 0|    |\sqrt3/2   1/2         0|
     * |0 1 0| -> |1/2        -\sqrt3/2   0|
     * |0 0 1|    |0          0           1|
     */
}
