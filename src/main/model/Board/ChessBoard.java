package main.model.Board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import main.modelOldRpg.map.TileNotFoundException;

public class ChessBoard implements Iterable<ChessHex> {
    public static final int[] VECTOR_ADJ_N  = { 0, 1, -1 };
    public static final int[] VECTOR_ADJ_NE = { 1, 0, -1 };
    public static final int[] VECTOR_ADJ_SE = { 1, -1, 0 };
    public static final int[] VECTOR_ADJ_S  = { 0, -1, 1 };
    public static final int[] VECTOR_ADJ_SW = { -1, 1, 0 };
    public static final int[] VECTOR_ADJ_NW = { -1, 1, 0 };
    public static final int[][] VECTORS_ADJ = { 
        VECTOR_ADJ_N, VECTOR_ADJ_NE, VECTOR_ADJ_SE,
        VECTOR_ADJ_S, VECTOR_ADJ_SW, VECTOR_ADJ_NW 
    };

    public static final int[] VECTOR_DIA_NE = {  1, -1,  1 };
    public static final int[] VECTOR_DIA_E  = {  2, -1, -1 };
    public static final int[] VECTOR_DIA_SE = {  1, -2,  1 };
    public static final int[] VECTOR_DIA_SW = { -1, -1,  2 };
    public static final int[] VECTOR_DIA_W =  { -2,  1,  1 };
    public static final int[] VECTOR_DIA_NW = { -1,  2, -1 };
    public static final int[][] VECTORS_DIA = {
        VECTOR_DIA_NE, VECTOR_DIA_E, VECTOR_DIA_SE,
        VECTOR_DIA_SW, VECTOR_DIA_W, VECTOR_DIA_NW
    };
    
    private static ChessBoard chessBoard = new ChessBoard(5, 5, 5);
    private Map<ChessHex, ChessHex> map;

    private ChessBoard(int maxX, int maxY, int maxZ) {
        map = new HashMap<>();
        for (int x = -maxX; x <= maxX; x++) {
            for (int y = -maxY; y <= maxY; y++) {
                for (int z = -maxZ; z <= maxZ; z++) {
                    try {
                        ChessHex newTile = new ChessHex(x, y, z, this);
                        map.put(newTile, newTile);
                    } catch (InvalidCoordinateException e) {
                        continue;
                    }
                }
            }
        }
    }

    public static ChessBoard getInstance() {
        return chessBoard;
    }

    public void printMap() {
        for (ChessHex t : map.keySet()) {
            System.out.println(t);
        }
    }

    // REQUIRES: v1, v2 have same size
    // EFFECTS: returns a vector consisted of the two added element-wise
    private int[] add(int[] v1, int[] v2) {
        int[] v3 = new int[v1.length];

        for (int i = 0; i < v1.length; i++) {
            v3[i] = v1[i] + v2[i];
        }

        return v3;
    }

    // EFFECTS: returns a vector consisted of a vector multiplied by a scalar
    private int[] mult(int[] v, int scalar) {
        int[] v2 = new int[v.length];

        for (int i = 0; i < v.length; i++) {
            v2[i] = v[i] * scalar;
        }

        return v2;
    }
    

    // EFFECTS: returns the set of tiles that are adjacent to a given tile
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getAdjacentTiles(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            ret.add(getTile(add(tile.getCoords(), VECTORS_ADJ[i])));
        }

        ret.remove(null);

        return ret;
    }
    // EFFECTS: returns the set of tiles that are diagonal to a given tile
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getDiagonalTiles(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            ret.add(getTile(add(tile.getCoords(), VECTORS_DIA[i])));
        }

        ret.remove(null);

        return ret;
    }

    // EFFECTS: returns a set of tiles in all adjacent directions
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getAdjLines(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            int j = 1;

            while (true) {
                ChessHex tileToAdd = getTile((add(tile.getCoords(), mult(VECTORS_ADJ[i], j))));
                if (tileToAdd == null || tileToAdd.containsPiece()) {
                    break;
                } else {
                    ret.add(tileToAdd);
                    j++;
                }
            }
        }

        return ret;
    }

    // EFFECTS: returns a set of tiles in all diagonal directions
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getDiaLines(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            int j = 1;

            while (true) {
                ChessHex tileToAdd = getTile((add(tile.getCoords(), mult(VECTORS_DIA[i], j))));
                if (tileToAdd == null || tileToAdd.containsPiece()) {
                    break;
                } else {
                    ret.add(tileToAdd);
                    j++;
                }
            }
        }

        return ret;
    }

    public ChessHex getTile(int x, int y, int z) {
        return map.get(new ChessHex(x, y, z));
    }

    public ChessHex getTile(int[] coords) {
        return map.get(new ChessHex(coords[0], coords[1], coords[2]));
    }

    public ChessHex getTile(ChessHex tileCoords) {
        return map.get(tileCoords);
    }

    @Override
    public Iterator<ChessHex> iterator() {
        return map.keySet().iterator();
    }
}
