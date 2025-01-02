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
        Set<ChessHex> ret = new HashSet<>();
        int[] baseDirection = getColour() ? ChessBoard.VECTOR_Z_NEG : ChessBoard.VECTOR_Z_POS;
        if (firstMove) {
            ret.addAll(getPosition().getMap().getLine(getPosition(), baseDirection, 2));
        } else {
            ret.addAll(getPosition().getMap().getLine(getPosition(), baseDirection, 1));
        }
        return ret;
    }
}
