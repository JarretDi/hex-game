package main.model.GamePieces;

import java.awt.Image;
import java.util.Set;

import main.model.Observer;
import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public abstract class GamePiece implements Observer {
    public static final Boolean WHITE = true;
    public static final Boolean BLACK = false;

    private ChessHex position;
    private Boolean hasMoved;
    private Boolean colour;
    private Boolean isSelected;

    protected Image image;

    public GamePiece(Boolean colour, ChessHex position) {
        this.hasMoved = false;
        this.colour = colour;
        this.position = position;
        this.isSelected = false;
        position.setPiece(this);
    }

    public GamePiece(GamePiece other, ChessHex position) {
        this.hasMoved = other.hasMoved;
        this.colour = other.colour;
        this.position = position;
        this.isSelected = false;
    }

    // Based on the game piece, returns the movable tiles
    public abstract Set<ChessHex> getMovableHexes();

    public Set<ChessHex> getThreatenedHexes() {
        return getMovableHexes();
    }

    public abstract String getType();

    public ChessBoard getBoard() {
        return position.getBoard();
    }

    public Image getImage() {
        return image;
    }

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

    public void update(Object obj, String msg) {
        if (msg.equals("All unselected") || obj == null) {
            this.isSelected = false;
        } else if (msg == "Piece selected") {
            if (((GamePiece)obj).equals(this)) {
                this.isSelected = true;
            } else {
                this.isSelected = false;
            }
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

    public Boolean isSelected() {
        return isSelected;
    }
    
    public Boolean hasMoved() {
        return hasMoved;
    }

    public void move() {
        hasMoved = true;
    }
}
