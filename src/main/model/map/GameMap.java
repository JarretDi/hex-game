package main.model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameMap {
    private Map<Tile, Tile> map;

    private static final int[][] basisVectors = {{1,-1,0},{0,1,-1},{1,0,-1}};
    private static final int[][] vectors = {{1,-1,0},{0,1,-1},{-1,0,1},{-1,1,0},{0,-1,1},{1,0,-1}};

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

    // EFFECTS: returns the set of tiles that neighbour the one given within a certain distance
    // if minDistance < 0 or maxDistance is < 1 or minDistance > maxDistance throws IllegalArgumentException
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

    private void addNeibouringTiles(Tile tile, Set<Tile> ret, int length) {
        if (length == 0) {
            return;
        } else {
            for (int i = 0; i < 6; i++) {
                Tile newPosTile = new Tile(tile.getX() + vectors[i][0], tile.getY() + vectors[i][1], tile.getZ() + vectors[i][2]);
                ret.add(getTile(newPosTile));
                addNeibouringTiles(newPosTile, ret, length - 1);
            }
        }
    }

    private void removeNeibouringTiles(Tile tile, Set<Tile> ret, int length) {
        if (length == 0) {
            return;
        } else {
            for (int i = 0; i < 6; i++) {
                Tile newPosTile = new Tile(tile.getX() + vectors[i][0], tile.getY() + vectors[i][1], tile.getZ() + vectors[i][2]);
                ret.remove(getTile(newPosTile));
                removeNeibouringTiles(newPosTile, ret, length - 1);
            }
        }
    }

    public Tile getTile(int x, int y, int z) {
        return map.get(new Tile(x, y, z));
    }

    public Tile getTile(Tile tileCoords) {
        return map.get(tileCoords);
    }
}
