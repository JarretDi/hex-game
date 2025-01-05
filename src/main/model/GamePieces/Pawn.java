package main.model.GamePieces;

import java.util.HashSet;
import java.util.Set;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Pawn extends GamePiece {
    private Boolean firstMove;

    // Constructs an unmoved pawn at the given position with the given colour
    public Pawn(Boolean colour, ChessHex position) {
        super(colour, position);
        this.firstMove = true;
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        ChessBoard cb = ChessBoard.getInstance();
        int[] forwardVector = getColour() ? ChessBoard.VECTOR_ADJ_N : ChessBoard.VECTOR_ADJ_S;
        int[] rightCapture = getColour() ? ChessBoard.VECTOR_ADJ_NE : ChessBoard.VECTOR_ADJ_SE;
        int[] leftCapture  = getColour() ? ChessBoard.VECTOR_ADJ_NW : ChessBoard.VECTOR_ADJ_SW;

        Set<ChessHex> ret = new HashSet<>();

        ChessHex tileAhead = cb.getTile(ChessBoard.add(getPosition().getCoords(), forwardVector));
        if (!tileAhead.containsPiece()) {
            ret.add(tileAhead);
        }

        if (firstMove) {
            ChessHex tile2Ahead = cb.getTile(ChessBoard.add(tileAhead.getCoords(), forwardVector));
            if (!tile2Ahead.containsPiece()) {
                ret.add(tile2Ahead);
            }
        }

        ChessHex tileEast = cb.getTile(ChessBoard.add(getPosition().getCoords(), rightCapture));
        if (tileEast.containsEnemyPiece(this)) {
            ret.add(tileEast);
        }

        ChessHex tileWest = cb.getTile(ChessBoard.add(getPosition().getCoords(), leftCapture));
        if (tileWest.containsEnemyPiece(this)) {
            ret.add(tileWest);
        }

        return ret;
    }

    @Override
    public String getType() {
        return "Pawn";
    }

    public void move() {
        this.firstMove = false;
    }
}
