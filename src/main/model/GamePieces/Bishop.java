package main.model.GamePieces;

import java.util.Set;

import main.model.Board.ChessHex;

public class Bishop extends GamePiece {
    public Bishop(Boolean colour, ChessHex position) {
        super(colour, position);
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMovableHexes'");
    }

}
