package main.model.GamePieces;

import java.util.Set;

import main.model.Board.ChessHex;

public class Rook extends GamePiece {
    public Rook(Boolean colour, ChessHex position) {
        super(colour, position);
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMovableHexes'");
    }

}
