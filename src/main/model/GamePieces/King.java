package main.model.GamePieces;

import java.awt.Image;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class King extends GamePiece {
    public King(boolean colour, ChessHex position) {
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

    public King(King other, ChessHex position) {
        super(other, position);
        image = other.image;
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        Set<ChessHex> moveable = getBoard().getAdjacentTiles(getPosition(), false);
        moveable.addAll(getBoard().getDiagonalTiles(getPosition(), false));

        for (ChessHex hex : getBoard().getThreatenedTiles(!getColour())) {
            moveable.remove(hex);
        }

        filterChecks(moveable);

        return moveable;
    }

    @Override
    public Set<ChessHex> getThreatenedHexes() {
        ChessBoard cb = getBoard();
        ChessHex pos = getPosition();

        Set<ChessHex> adj = cb.getAdjacentTiles(pos, true);
        Set<ChessHex> dia = cb.getDiagonalTiles(pos, true);

        adj.addAll(dia);

        return adj;
    }

    @Override
    public Set<ChessHex> getCriticalHexes() {
        return new HashSet<>();
    }

    public Boolean isInCheck() {
        return getBoard().getThreatenedTiles(!getColour()).contains(getPosition());
    }

    @Override
    public String getType() {
        return "K";
    }

    @Override
    public Image getImage() {
        return isInCheck() ? image : image;
    }
}
