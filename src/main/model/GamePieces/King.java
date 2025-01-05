package main.model.GamePieces;

import java.util.Set;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class King extends GamePiece {
    public King(Boolean colour, ChessHex position) {
        super(colour, position);
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        ChessBoard cb = ChessBoard.getInstance();
        ChessHex pos = getPosition();

        Set<ChessHex> adj = cb.getAdjacentTiles(pos);
        Set<ChessHex> dia = cb.getDiagonalTiles(pos);

        adj.addAll(dia);

        adj.removeAll(cb.getThreatenedTiles(getColour()));

        return adj;
    }

    @Override
    public String getType() {
        return "King";
    }
}
