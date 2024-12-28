package main.model.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameMap {
    public static final int[] VECTOR_X_POS = { 1, -1, 0 };
    public static final int[] VECTOR_Y_POS = { 1, 0, -1 };
    public static final int[] VECTOR_Z_POS = { 0, 1, -1 };
    public static final int[] VECTOR_X_NEG = { -1, 1, 0 };
    public static final int[] VECTOR_Y_NEG = { -1, 0, 1 };
    public static final int[] VECTOR_Z_NEG = { 0, -1, 1 };
    public static final int[][] VECTORS = { VECTOR_X_POS, VECTOR_Y_POS, VECTOR_Z_POS,
            VECTOR_X_NEG, VECTOR_Y_NEG, VECTOR_Z_NEG };

    private Map<Tile, Tile> map;

    public GameMap(int maxX, int maxY, int maxZ, TileGenerator tileGenerator) {
        map = new HashMap<>();
        for (int x = -maxX; x <= maxX; x++) {
            for (int y = -maxY; y <= maxY; y++) {
                for (int z = -maxZ; z <= maxZ; z++) {
                    try {
                        Tile newTile = new Tile(x, y, z, this);
                        map.put(newTile, newTile);
                    } catch (InvalidCoordinateException e) {
                        continue;
                    }
                }
            }
        }
    }

    public void printMap() {
        for (Tile t : map.keySet()) {
            System.out.println(t);
        }
    }

    // EFFECTS: returns the set of tiles that neighbour the one given within a
    // certain distance
    // if minDistance < 0 or maxDistance is < 1 or minDistance > maxDistance throws
    // IllegalArgumentException
    // if tile is not in set, then throws TileNotFoundException
    public Set<Tile> getNeighbours(Tile tile, int minDistance, int maxDistance) {
        if (minDistance < 0 || maxDistance < 1 || minDistance > maxDistance) {
            throw new IllegalArgumentException();
        }
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        Set<Tile> ret = new HashSet<>();

        addNeibouringTiles(tile, ret, maxDistance);

        ret.remove(tile);
        ret.remove(null);

        removeNeibouringTiles(tile, ret, minDistance);

        return ret;
    }

    public Set<Tile> getNeighbours(Tile tile, int distance) {
        return getNeighbours(tile, 0, distance);
    }

    public Set<Tile> getNeighbours(Tile tile) {
        return getNeighbours(tile, 0, 1);
    }

    // Helper for getNeighbours
    private void addNeibouringTiles(Tile tile, Set<Tile> ret, int length) {
        if (length == 0) {
            return;
        } else {
            for (int i = 0; i < 6; i++) {
                Tile newPosTile = new Tile(
                        tile.getX() + VECTORS[i][0],
                        tile.getY() + VECTORS[i][1],
                        tile.getZ() + VECTORS[i][2]);
                ret.add(getTile(newPosTile));
                addNeibouringTiles(newPosTile, ret, length - 1);
            }
        }
    }

    // Helper for getNeighbours
    private void removeNeibouringTiles(Tile tile, Set<Tile> ret, int length) {
        if (length == 0) {
            return;
        } else {
            for (int i = 0; i < 6; i++) {
                Tile newPosTile = new Tile(
                        tile.getX() + VECTORS[i][0],
                        tile.getY() + VECTORS[i][1],
                        tile.getZ() + VECTORS[i][2]);
                ret.remove(getTile(newPosTile));
                removeNeibouringTiles(newPosTile, ret, length - 1);
            }
        }
    }

    // REQUIRES: directionVector is in VECTORS, distance > 0
    // EFFECTS: returns a set of tiles in the given direction within a certain
    // distance
    public Set<Tile> getLine(Tile tile, int[] directionVector, int distance) {
        Set<Tile> ret = new HashSet<>();

        for (int i = 1; i <= distance; i++) {
            ret.add(getTile(new Tile(
                    tile.getX() + directionVector[0] * i,
                    tile.getY() + directionVector[1] * i,
                    tile.getZ() + directionVector[2] * i)));
        }

        ret.remove(null);

        return ret;
    }

    public Tile getTile(int x, int y, int z) {
        return map.get(new Tile(x, y, z));
    }

    public Tile getTile(Tile tileCoords) {
        return map.get(tileCoords);
    }
}
