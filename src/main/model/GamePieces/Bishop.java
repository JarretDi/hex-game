package main.model.GamePieces;

import java.io.File;
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

    @Override
    public Set<ChessHex> getMovableHexes() {
        return ChessBoard.getInstance().getDiagonalLines(getPosition());
    }

    @Override
    public String getType() {
        return "Bishop";
    }
}
