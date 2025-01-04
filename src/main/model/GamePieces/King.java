package main.model.GamePieces;

import java.util.Set;

import main.model.Board.ChessHex;

public class King extends GamePiece {
    public King(Boolean colour) {
        super(colour, colour? new ChessHex(0, 0, 0) : new ChessHex(0, 0, 0));
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        return getPosition().getMap().getAdjacentTiles(getPosition());
    }
}
