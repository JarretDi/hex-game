package main.model.GamePieces;

import java.io.File;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class King extends GamePiece {
    public King(Boolean colour, ChessHex position) {
        super(colour, position);
        try {
            if (getColour() == GamePiece.WHITE) {
                image = ImageIO.read(new File("./src/data/pieces/white-king.png"));
            } else {
                image = ImageIO.read(new File("./src/data/pieces/black-king.png"));
            }
        } catch (Exception e) {
            // Really should not have got here
        }
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        Set <ChessHex> threatendHexs = getThreatenedHexes();

        threatendHexs.removeAll(ChessBoard.getInstance().getThreatenedTiles(!getColour()));

        return threatendHexs;
    }

    @Override
    public Set<ChessHex> getThreatenedHexes() {
        ChessBoard cb = ChessBoard.getInstance();
        ChessHex pos = getPosition();

        Set<ChessHex> adj = cb.getAdjacentTiles(pos);
        Set<ChessHex> dia = cb.getDiagonalTiles(pos);

        adj.addAll(dia);

        return adj;
    }

    @Override
    public String getType() {
        return "King";
    }
}
