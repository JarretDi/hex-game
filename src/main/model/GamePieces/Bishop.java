package main.model.GamePieces;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Bishop extends GamePiece {
    public Bishop(Boolean colour, ChessHex position) {
        super(colour, position);
        try {
            if (getColour() == GamePiece.WHITE) {
                image = ImageIO.read(new File("./src/data/pieces/white-bishop.png"));
            } else {
                image = ImageIO.read(new File("./src/data/pieces/black-bishop.png"));
            }
        } catch (Exception e) {
            // Really should not have got here
        }
    }

    public Bishop(Bishop other, ChessHex position) {
        super(other, position);
        image = other.image;
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        Set<ChessHex> ret = getBoard().getDiagonalLines(getPosition());
        filterCriticals(ret);
        return ret;
    }

    @Override
    public Set<ChessHex> getThreatenedHexes() {
        return getBoard().getDiagonalLines(getPosition());
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
        return "B";
    }
}
