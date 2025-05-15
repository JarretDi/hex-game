package main.model.Board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.model.GamePieces.GamePiece;
import main.model.GamePieces.King;
import main.model.GamePieces.PieceFactory;
import main.model.Logger.BoardLogger;
import main.modelOldRpg.map.TileNotFoundException;

// Represents the minimum amount of information needed to reconstruct a Chessboard
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

    public static final Color COLOUR1 = Color.decode("#F4A460");
    public static final Color COLOUR2 = Color.decode("#CD853F");
    public static final Color COLOUR3 = Color.decode("#FFDAB9");

    private Map<ChessHex, ChessHex> map;

    private Set<GamePiece> pieces;
    private List<GamePiece> capturedPieces;
    private boolean turn;
    private int turnCount;

    public ChessBoard(int maxX, int maxY, int maxZ) {
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

        pieces = new HashSet<>();
        capturedPieces = new ArrayList<>();
        turn = GamePiece.WHITE;
        turnCount = 1;
        
        colourBoard();
    }

    // Copy constructor for a chess board
    public ChessBoard(ChessBoard other) {
        this.map = other.duplicateBoard();
        pieces = new HashSet<>();
        capturedPieces = new ArrayList<>();
        turn = other.getTurn();
        turnCount = other.turnCount;

        for (ChessHex hex : map.keySet()) {
            hex.setBoard(this);
            if (hex.containsPiece()) {
                pieces.add(hex.getPiece());
            }
        }
        for (GamePiece gp : other.getCapturedPieces()) {
            capturedPieces.add(PieceFactory.makePiece(gp, null));
        }
        colourBoard();
    }

    // Helper for copy constructor
    private Map<ChessHex, ChessHex> duplicateBoard() {
        Map<ChessHex, ChessHex> ret = new HashMap<>();

        for (ChessHex hex : map.keySet()) {
            ChessHex newHex = new ChessHex(hex);
            if (hex.containsPiece()) {
                GamePiece oldPiece = hex.getPiece();
                newHex.setPiece(PieceFactory.makePiece(oldPiece, newHex));
            }
            ret.put(newHex, newHex);
        }

        return ret;
    }

    public Set<ChessHex> getMap() {
        return map.keySet();
    }

    public void printMap() {
        for (ChessHex t : map.keySet()) {
            System.out.println(t);
        }
    }

    private void colourBoard() {
        ChessHex start1 = getTile(0, 0, 0);
        ChessHex start2 = getTile(0, 1, -1);
        ChessHex start3 = getTile(1, 0, -1);

        Set<ChessHex> colour1 = colourBoard(start1, new HashSet<>());
        Set<ChessHex> colour2 = colourBoard(start2, new HashSet<>());
        Set<ChessHex> colour3 = colourBoard(start3, new HashSet<>());

        for (ChessHex hex : colour1) {
            hex.setColour(COLOUR2);
        }
        for (ChessHex hex : colour2) {
            hex.setColour(COLOUR1);
        }
        for (ChessHex hex : colour3) {
            hex.setColour(COLOUR3);
        }
    }

    private Set<ChessHex> colourBoard(ChessHex current, Set<ChessHex> visited) {
        if (current == null) return null;
        if (!visited.contains(current)) {
            visited.add(current);
    
            for (int i = 0; i < 6; i++) {
                ChessHex h = getTile(addV(current.getCoords(), VECTORS_DIA[i]));
                colourBoard(h, visited);
            }
        }
        return visited;
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

    public boolean getTurn() {
        return turn;
    }

    // Swaps whose turn and returns the value of the new turn
    // increments turn counter by one
    public boolean swapTurn() {
        turn = !turn;
        turnCount++;
        return turn;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public Set<GamePiece> getPieces() {
        return pieces;
    }

    public Set<GamePiece> getWhitePieces() {
        Set<GamePiece> ret = new HashSet<>();
        for (GamePiece p : pieces) {
            if (p.getColour()) {
                ret.add(p);
            }
        }
        return ret;
    }

    public Set<GamePiece> getBlackPieces() {
        Set<GamePiece> ret = new HashSet<>();
        for (GamePiece p : pieces) {
            if (!p.getColour()) {
                ret.add(p);
            }
        }
        return ret;
    }

    public List<GamePiece> getCapturedPieces() {
        return capturedPieces;
    }

    public Set<GamePiece> getEnemyPieces(GamePiece piece) {
        return piece.getColour() ? getBlackPieces() : getWhitePieces();
    }

    public Set<GamePiece> getEnemyPieces(boolean colour) {
        return colour ?  getBlackPieces() : getWhitePieces();
    }

    public King getKing(boolean colour) {
        for (GamePiece piece : pieces) {
            if (piece.getType() == "K" && piece.getColour() == colour) {
                return (King) piece;
            }
        }
        return null;
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
            ChessHex tileToAdd = getTile(addV(tile.getCoords(), VECTORS_ADJ[i]));
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
            ChessHex tileToAdd = getTile(addV(tile.getCoords(), VECTORS_DIA[i]));
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

    // REQUIRES: 0 <= i <= 6
    // EFFECTS: returns a line in the direction of the given number of direction vector i
    //          if the next tile being added is an allied piece, stop
    //          if the next tile is an enemy piece, add that hex and then stop
    //          else continue adding tiles along that line
    private void getAdjacentLine(ChessHex tile, Set<ChessHex> ret, int i) {
        int j = 1;

        while (true) {
            ChessHex tileToAdd = getTile((addV(tile.getCoords(), multV(VECTORS_ADJ[i], j))));
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
            ChessHex tileToAdd = getTile((addV(tile.getCoords(), multV(VECTORS_DIA[i], j))));
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
    
    public Set<ChessHex> getThreatenedTiles(boolean colour) {
        Set<ChessHex> ret = new HashSet<>();
        for (GamePiece piece : getEnemyPieces(!colour)) {
            ret.addAll(piece.getThreatenedHexes());
        }

        return ret;
    }

    // REQUIRES: v1, v2 have same size
    // EFFECTS: returns a vector consisted of the two added element-wise
    public static int[] addV(int[] v1, int[] v2) {
        int[] v3 = new int[v1.length];

        for (int i = 0; i < v1.length; i++) {
            v3[i] = v1[i] + v2[i];
        }

        return v3;
    }

    // EFFECTS: returns a vector consisted of a vector multiplied by a scalar
    public static int[] multV(int[] v, int scalar) {
        int[] v2 = new int[v.length];

        for (int i = 0; i < v.length; i++) {
            v2[i] = v[i] * scalar;
        }

        return v2;
    }

    @Override
    public Iterator<ChessHex> iterator() {
        return map.keySet().iterator();
    }
}
