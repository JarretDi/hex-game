package main.model.GamePieces;

import java.util.Set;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Queen extends GamePiece {
    public Queen(Boolean colour, ChessHex position) {
        super(colour, position);
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        ChessBoard cb = ChessBoard.getInstance();
        ChessHex pos = getPosition();

        Set<ChessHex> adj = cb.getAdjacentLines(pos);
        Set<ChessHex> dia = cb.getDiagonalLines(pos);

        adj.addAll(dia);

        return adj;
    }

    @Override
    public String getType() {
        return "Queen";
    }
}
