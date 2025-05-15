package main.model.Board;

import java.awt.Color;
import java.util.Arrays;

import main.model.Observer;
import main.model.GamePieces.GamePiece;
import main.model.GamePieces.Pawn;

public class ChessHex implements Observer {
    private int[] coords = new int[3];
    private ChessBoard chessBoard;
    private GamePiece piece;
    private Color colour;

    private Boolean isHighlighted;

    // EFFECTS: construct a hexagonal tile with given x, y, z coordinates
    // Throws an invalid coordinate exception if given coordinates does not satisfy
    // cubical coordinate restraints (i.e. that x+y+z=0)
    // then, determines tile type by
    // sets character to null with no components or tile effects
    public ChessHex(int x, int y, int z, ChessBoard map) throws InvalidCoordinateException {
        if (x + y + z != 0) {
            throw new InvalidCoordinateException();
        }
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
        
        this.chessBoard = map;
        this.piece = null;
        this.isHighlighted = false;
    }

    // Copy Constructor for ChessHex
    // sets map for null
    public ChessHex(ChessHex other) {
        this.coords[0] = other.coords[0];
        this.coords[1] = other.coords[1];
        this.coords[2] = other.coords[2];

        this.chessBoard = null;
        this.piece = null;
        this.isHighlighted = false;
    }

    public ChessHex(int x, int y, int z) throws InvalidCoordinateException {
        this(x, y, z, null);
    }

    public int[] getCoords() {
        return coords;
    }

    public GamePiece getPiece() {
        return piece;
    }

    // MODIFIES: this
    // EFFECTS: if piece is null
    // sets this character to given character
    // additionally sets newCharacter's position to this
    public void setPiece(GamePiece newPiece) {
        if (this.piece != newPiece) {
            this.piece = newPiece;
            newPiece.setPosition(this);
        }
    }

    public GamePiece removePiece() {
        GamePiece temp = this.piece;
        this.piece = null;
        return temp;

    }

    public Boolean containsPiece() {
        return this.piece != null;
    }

    public Boolean containsAlliedPiece(GamePiece piece) {
        return (containsPiece() && getPiece().getColour() == piece.getColour());
    }

    public Boolean containsEnemyPiece(GamePiece piece) {
        return (containsPiece() && getPiece().getColour() != piece.getColour());
    }

    public ChessBoard getBoard() {
        return chessBoard;
    }

    public void setBoard(ChessBoard cb) {
        this.chessBoard = cb;
    }
    
    public Color getColour() {
        return isHighlighted ? colour.brighter() : colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "(" + coords[0] + " " + coords[1] + " " + coords[2] + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(coords);
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
        ChessHex other = (ChessHex) obj;
        if (!Arrays.equals(coords, other.coords))
            return false;
        return true;
    }

    @Override
    public void update(Object obj, String msg) {
        if (msg.equals("All unselected") || obj == null) {
            this.isHighlighted = false;
        } else if (msg == "Piece selected") {
            if (((GamePiece)obj).getMovableHexes().contains(this)) {
                this.isHighlighted = true;
            } else {
                this.isHighlighted = false;
            }
        }
    }


    /*
     * |1 0 0| |\sqrt3/2 1/2 0|
     * |0 1 0| -> |1/2 -\sqrt3/2 0|
     * |0 0 1| |0 0 1|
     */
}
