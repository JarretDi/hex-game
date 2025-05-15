package main.model.GamePieces;

import java.io.File;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Queen extends GamePiece {
    public Queen(Boolean colour, ChessHex position) {
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

        Set<ChessHex> adj = cb.getAdjacentLines(pos);
        Set<ChessHex> dia = cb.getDiagonalLines(pos);

        adj.addAll(dia);

        return adj;
    }

    @Override
    public String getType() {
        return "Q";
    }
}
