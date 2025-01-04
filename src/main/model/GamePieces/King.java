package main.model.GamePieces;

import java.util.Set;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class King extends GamePiece {
    public King(Boolean colour) {
        super(colour, colour? new ChessHex(0, 0, 0) : new ChessHex(0, 0, 0));
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        ChessBoard cb = ChessBoard.getInstance();
        ChessHex pos = getPosition();

        Set<ChessHex> adj = cb.getAdjacentTiles(pos);
        Set<ChessHex> dia = cb.getDiagonalTiles(pos);

        adj.addAll(dia);

        return adj;
    }
}
