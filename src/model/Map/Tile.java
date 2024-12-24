package model.map;

import java.util.Random;

public class Tile {
    public enum TileType {
        Plains,
        Forest,
        Desert,
        Water,
    }

    private TileType type;

    public Tile() {
        type = TileType.values()[new Random().nextInt(TileType.values().length)];
    }

    public TileType getType() {
        return type;
    }
}
