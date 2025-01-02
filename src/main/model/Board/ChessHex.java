package main.model.Board;

import main.model.GamePieces.GamePiece;

public class ChessHex {
    private int x;
    private int y;
    private int z;

    private ChessBoard map;

    private GamePiece piece;

    // EFFECTS: construct a hexagonal tile with given x, y, z coordinates
    // Throws an invalid coordinate exception if given coordinates does not satisfy
    // cubical coordinate restraints (i.e. that x+y+z=0)
    // then, determines tile type by
    // sets character to null with no components or tile effects
    public ChessHex(int x, int y, int z, ChessBoard map) throws InvalidCoordinateException {
        if (x + y + z != 0) {
            throw new InvalidCoordinateException();
        }
        this.x = x;
        this.y = y;
        this.z = z;

        this.map = map;
        
        this.piece = null;
    }

    public ChessHex(int x, int y, int z) throws InvalidCoordinateException {
        this(x, y, z, null);
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

    public ChessBoard getMap() {
        return map;
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

    public void removePiece() {
        this.piece = null;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z + "\t| " + piece;
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
        ChessHex other = (ChessHex) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    /*
     * |1 0 0| |\sqrt3/2 1/2 0|
     * |0 1 0| -> |1/2 -\sqrt3/2 0|
     * |0 0 1| |0 0 1|
     */
}
