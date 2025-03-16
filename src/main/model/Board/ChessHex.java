package main.model.Board;

import java.awt.Color;
import java.util.Arrays;

import main.model.GamePieces.GamePiece;

public class ChessHex {
    private int[] coords = new int[3];
    private GamePiece piece;
    private Color colour;

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
        
        this.piece = null;
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
        if (containsPiece() && getPiece().getColour() == piece.getColour()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean containsEnemyPiece(GamePiece piece) {
        if (containsPiece() && getPiece().getColour() != piece.getColour()) {
            return true;
        } else {
            return false;
        }
    }

    
    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return coords[0] + " " + coords[1] + " " + coords[2] + "\t| " + ((piece != null) ? (piece.getColour()? "White " : "Black ") + piece.getType() : "");
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

    /*
     * |1 0 0| |\sqrt3/2 1/2 0|
     * |0 1 0| -> |1/2 -\sqrt3/2 0|
     * |0 0 1| |0 0 1|
     */
}
