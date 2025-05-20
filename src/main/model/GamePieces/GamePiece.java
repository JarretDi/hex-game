package main.model.GamePieces;

import java.awt.Image;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import main.model.Observer;
import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public abstract class GamePiece implements Observer {
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;

    private ChessHex position;
    private boolean hasMoved;
    private boolean colour;

    private boolean isSelected;

    protected Image image;

    public GamePiece(boolean colour, ChessHex position) {
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

    public abstract Set<ChessHex> getThreatenedHexes();

    // REQUIRES: Other side is currently in check
    // EFFECTS: Based on the game piece, if this piece is giving check to the opponent,
    // represents the hexes that can be moved to in order to stop this piece from giving it
    // i.e. some pieces can be blocked, most can be captured, King cannot have any
    public abstract Set<ChessHex> getCriticalHexes();

    // EFFECTS: removes non-critical tiles from a given set of moveable tiles
    public void filterCriticals(Set<ChessHex> moves) {
        if (getBoard().getKing(getColour()).isInCheck()) {
            Set<ChessHex> criticals = getBoard().findCriticalHexes();
            Iterator<ChessHex> it = moves.iterator();
            
            while (it.hasNext()) {
                if (!criticals.contains(it.next())) {
                    it.remove();
                }
            }
        }
    }

    // EFFECTS: If moving this piece to each possible move results in a check, remove it from given list
    public void managePins(Set<ChessHex> moves) {
        ChessBoard cb = getBoard();

        ChessHex orig = getPosition();
        Iterator<ChessHex> movesIterator = moves.iterator();

        while (movesIterator.hasNext()) {
            ChessHex newhex = movesIterator.next();

            GamePiece newpc = newhex.removePiece();
            if (newpc != null) newpc.removePosition();
            setPosition(newhex);

            boolean shouldRemove = false;
            if (cb.getKing(colour).isInCheck()) {
                shouldRemove = true;
            }

            setPosition(orig);
            if (newpc != null) {
                newhex.setPiece(newpc);
                newpc.setPosition(newhex);
            }
            if (shouldRemove) movesIterator.remove();
        }
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

    public boolean getColour() {
        return colour;
    }

    public boolean isOnMap() {
        return position != null;
    }

    public boolean isSelected() {
        return isSelected;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }

    public void move() {
        hasMoved = true;
    }
}
