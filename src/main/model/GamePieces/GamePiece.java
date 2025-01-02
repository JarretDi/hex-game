package main.model.GamePieces;

import java.util.Set;

import main.model.Board.ChessHex;

public abstract class GamePiece {
    public static final Boolean WHITE = true;
    public static final Boolean BLACK = false;

    private ChessHex position;
    private Boolean colour;

    public GamePiece(Boolean colour, ChessHex position) {
        this.colour = colour;
        this.position = position;
    }

    // Based on the game piece, returns the movable tiles
    public abstract Set<ChessHex> getMovableHexes();

    public ChessHex getPosition() {
        return position;
    }

    // MODIFIES: this, newPosition
    // EFFECTS: if newPosition is empty:
    //          moves character to newPosition
    //          sets the character of newPosition to this
    //          removes this character from the old posiion
    public void setPosition(ChessHex newPosition) {
        ChessHex oldPosition = this.position;

        if (this.position != newPosition) {
            this.position = newPosition;

            newPosition.setPiece(this);
            if (oldPosition != null) oldPosition.removePiece();
        }
    }

    public void removePosition() {
        this.position = null;
    }

    public Boolean getColour() {
        return colour;
    }

    public Boolean isOnMap() {
        return position != null;
    }
}
