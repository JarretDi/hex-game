package main.model.Board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import main.model.GamePieces.Bishop;
import main.model.GamePieces.King;
import main.model.GamePieces.Knight;
import main.model.GamePieces.Pawn;
import main.model.GamePieces.Queen;
import main.model.GamePieces.Rook;
import main.modelOldRpg.map.TileNotFoundException;

public class ChessBoard implements Iterable<ChessHex> {
    public static final int[] VECTOR_ADJ_N = { 0, 1, -1 };
    public static final int[] VECTOR_ADJ_NE = { 1, 0, -1 };
    public static final int[] VECTOR_ADJ_SE = { 1, -1, 0 };
    public static final int[] VECTOR_ADJ_S = { 0, -1, 1 };
    public static final int[] VECTOR_ADJ_SW = { -1, 0, 1 };
    public static final int[] VECTOR_ADJ_NW = { -1, 1, 0 };
    public static final int[][] VECTORS_ADJ = {
            VECTOR_ADJ_N, VECTOR_ADJ_NE, VECTOR_ADJ_SE,
            VECTOR_ADJ_S, VECTOR_ADJ_SW, VECTOR_ADJ_NW
    };

    public static final int[] VECTOR_DIA_NE = { 1, 1, -2 };
    public static final int[] VECTOR_DIA_E = { 2, -1, -1 };
    public static final int[] VECTOR_DIA_SE = { 1, -2, 1 };
    public static final int[] VECTOR_DIA_SW = { -1, -1, 2 };
    public static final int[] VECTOR_DIA_W = { -2, 1, 1 };
    public static final int[] VECTOR_DIA_NW = { -1, 2, -1 };
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
        resetBoard();
    }

    public static ChessBoard getInstance() {
        return chessBoard;
    }

    public void resetBoard() {
        clearBoard();
        placePawns();
        placePieces();
    }

    private void clearBoard() {
        for (ChessHex hex : map.keySet()) {
            hex.removePiece();
        }
    }

    private void placePawns() {
        new Pawn(true, getTile(-4, -1, 5));
        new Pawn(true, getTile(-3, -1, 4));
        new Pawn(true, getTile(-2, -1, 3));
        new Pawn(true, getTile(-1, -1, 2));
        new Pawn(true, getTile(0, -1, 1));
        new Pawn(true, getTile(1, -2, 1));
        new Pawn(true, getTile(2, -3, 1));
        new Pawn(true, getTile(3, -4, 1));
        new Pawn(true, getTile(4, -5, 1));

        new Pawn(false, getTile(4, 1, -5));
        new Pawn(false, getTile(3, 1, -4));
        new Pawn(false, getTile(2, 1, -3));
        new Pawn(false, getTile(1, 1, -2));
        new Pawn(false, getTile(0, 1, -1));
        new Pawn(false, getTile(-1, 2, -1));
        new Pawn(false, getTile(-2, 3, -1));
        new Pawn(false, getTile(-3, 4, -1));
        new Pawn(false, getTile(-4, 5, -1));
    }

    private void placePieces() {
        placeWhitePieces();
        placeBlackPieces();
    }

    private void placeWhitePieces() {
        new Bishop(true, getTile(0, -3, 3));
        new Bishop(true, getTile(0, -4, 4));
        new Bishop(true, getTile(0, -5, 5));

        new Knight(true, getTile(2, -5, 3));
        new Knight(true, getTile(-2, -3, 5));

        new Rook(true, getTile(3, -5, 2));
        new Rook(true, getTile(-3, -2, 5));

        new Queen(true, getTile(-1, -4, 5));
        new King(true, getTile(1, -5, 4));
    }

    private void placeBlackPieces() {
        new Bishop(false, getTile(0, 3, -3));
        new Bishop(false, getTile(0, 4, -4));
        new Bishop(false, getTile(0, 5, -5));

        new Knight(false, getTile(2, 3, -5));
        new Knight(false, getTile(-2, 5, -3));

        new Rook(false, getTile(3, 2, -5));
        new Rook(false, getTile(-3, 5, -2));

        new Queen(false, getTile(-1, 5, -4));
        new King(false, getTile(1, 4, -5));
    }

    public void printMap() {
        for (ChessHex t : map.keySet()) {
            System.out.println(t);
        }
    }

    // REQUIRES: v1, v2 have same size
    // EFFECTS: returns a vector consisted of the two added element-wise
    public static int[] add(int[] v1, int[] v2) {
        int[] v3 = new int[v1.length];

        for (int i = 0; i < v1.length; i++) {
            v3[i] = v1[i] + v2[i];
        }

        return v3;
    }

    // EFFECTS: returns a vector consisted of a vector multiplied by a scalar
    public static int[] mult(int[] v, int scalar) {
        int[] v2 = new int[v.length];

        for (int i = 0; i < v.length; i++) {
            v2[i] = v[i] * scalar;
        }

        return v2;
    }

    // EFFECTS: returns the set of empty tiles that are adjacent to a given tile
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getAdjacentTiles(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        tile = getTile(tile);

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            ChessHex tileToAdd = getTile(add(tile.getCoords(), VECTORS_ADJ[i]));
            if (tileToAdd == null || tileToAdd.containsAlliedPiece(tile.getPiece())) {
                continue;
            } else {
                ret.add(tileToAdd);
            }
        }

        return ret;
    }

    // EFFECTS: returns the set of empty tiles that are diagonal to a given tile
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getDiagonalTiles(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        tile = getTile(tile);

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            ChessHex tileToAdd = getTile(add(tile.getCoords(), VECTORS_DIA[i]));
            if (tileToAdd == null || tileToAdd.containsAlliedPiece(tile.getPiece())) {
                continue;
            } else {
                ret.add(tileToAdd);
            }
        }

        return ret;
    }

    // EFFECTS: returns a set of tiles in all adjacent directions
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getAdjacentLines(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        tile = getTile(tile);

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            getAdjacentLine(tile, ret, i);
        }

        return ret;
    }

    private void getAdjacentLine(ChessHex tile, Set<ChessHex> ret, int i) {
        int j = 1;

        while (true) {
            ChessHex tileToAdd = getTile((add(tile.getCoords(), mult(VECTORS_ADJ[i], j))));
            if (tileToAdd == null || tileToAdd.containsAlliedPiece(tile.getPiece())) {
                break;
            } else if (tileToAdd.containsEnemyPiece(tile.getPiece())) {
                ret.add(tileToAdd);
                break;
            } else {
                ret.add(tileToAdd);
                j++;
            }
        }
    }

    // EFFECTS: returns a set of tiles in all diagonal directions
    // if tile is not in set, throws TileNotFoundException
    public Set<ChessHex> getDiagonalLines(ChessHex tile) {
        if (!map.containsKey(tile)) {
            throw new TileNotFoundException();
        }

        tile = getTile(tile);

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            getDiagonalLines(tile, ret, i);
        }

        return ret;
    }

    private void getDiagonalLines(ChessHex tile, Set<ChessHex> ret, int i) {
        int j = 1;

        while (true) {
            ChessHex tileToAdd = getTile((add(tile.getCoords(), mult(VECTORS_DIA[i], j))));
            if (tileToAdd == null || tileToAdd.containsAlliedPiece(tile.getPiece())) {
                break;
            } else if (tileToAdd.containsEnemyPiece(tile.getPiece())) {
                ret.add(tileToAdd);
                break;
            } else {
                ret.add(tileToAdd);
                j++;
            }
        }
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
