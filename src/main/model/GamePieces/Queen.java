package main.model.GamePieces;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Queen extends GamePiece {
    public Queen(boolean colour, ChessHex position) {
        super(colour, position);
        try {
            if (getColour() == GamePiece.WHITE) {
                image = ImageIO.read(new File("./src/data/pieces/white-queen.png"));
            } else {
                image = ImageIO.read(new File("./src/data/pieces/black-queen.png"));
            }
        } catch (Exception e) {
            // Really should not have got here
        }
    }

    public Queen(Queen other, ChessHex position) {
        super(other, position);
        image = other.image;
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        ChessBoard cb = getBoard();
        ChessHex pos = getPosition();

        Set<ChessHex> adj = cb.getAdjacentLines(pos, false);
        Set<ChessHex> dia = cb.getDiagonalLines(pos, false);

        adj.addAll(dia);

        filterCriticals(adj);
        managePins(adj);

        return adj;
    }

    @Override
    public Set<ChessHex> getThreatenedHexes() {
        if (!isOnMap()) {
            return new HashSet<>();
        }
        ChessBoard cb = getBoard();
        ChessHex pos = getPosition();

        Set<ChessHex> adj = cb.getAdjacentLines(pos, true);
        Set<ChessHex> dia = cb.getDiagonalLines(pos, true);

        adj.addAll(dia);

        return adj;
    }
    
    @Override
    public Set<ChessHex> getCriticalHexes() {
        Set<ChessHex> criticalHexes = new HashSet<>();

        int[] start = getPosition().getCoords();
        int[] end = getPosition().getBoard().getKing(!getColour()).getPosition().getCoords();
        int[] direction = ChessBoard.getDirection(start, end);
        

        while (!ChessBoard.sameVector(start, end)) {
            criticalHexes.add(getBoard().getTile(start));
            start = ChessBoard.addV(start, direction);
        }

        return criticalHexes;
    }

    @Override
    public String getType() {
        return "Q";
    }
}
