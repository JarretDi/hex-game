package main.model.GamePieces;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Pawn extends GamePiece {
    private int[] forwardVector;
    private int[] rightCapture;
    private int[] leftCapture;
    

    // Constructs an unmoved pawn at the given position with the given colour
    public Pawn(boolean colour, ChessHex position) {
        super(colour, position);

        forwardVector = getColour() ? ChessBoard.VECTOR_ADJ_N : ChessBoard.VECTOR_ADJ_S;
        rightCapture = getColour() ? ChessBoard.VECTOR_ADJ_NE : ChessBoard.VECTOR_ADJ_SE;
        leftCapture  = getColour() ? ChessBoard.VECTOR_ADJ_NW : ChessBoard.VECTOR_ADJ_SW;

        try {
            if (getColour() == GamePiece.WHITE) {
                image = ImageIO.read(new File("./src/data/pieces/white-pawn.png"));
            } else {
                image = ImageIO.read(new File("./src/data/pieces/black-pawn.png"));
            }
        } catch (Exception e) {
            // Really should not have got here
        }
    }

    public Pawn(Pawn other, ChessHex position) {
        super(other, position);

        forwardVector = other.forwardVector;
        rightCapture = other.rightCapture;
        leftCapture = other.leftCapture;

        image = other.image;
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        Set<ChessHex> ret = new HashSet<>();

        ChessHex tileAhead = getPosition().getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), forwardVector));
        if (!tileAhead.containsPiece()) {
            ret.add(tileAhead);
        }

        if (!hasMoved()) {
            ChessHex tile2Ahead = getPosition().getBoard().getTile(ChessBoard.addV(tileAhead.getCoords(), forwardVector));
            if (!tileAhead.containsPiece() && tile2Ahead != null && !tile2Ahead.containsPiece()) {
                ret.add(tile2Ahead);
            }
        }

        ChessHex tileEast = getPosition().getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), rightCapture));
        if (tileEast.containsEnemyPiece(this)) {
            ret.add(tileEast);
        }

        ChessHex tileWest = getPosition().getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), leftCapture));
        if (tileWest.containsEnemyPiece(this)) {
            ret.add(tileWest);
        }

        filterCriticals(ret);
        managePins(ret);

        return ret;
    }

    @Override
    public Set<ChessHex> getThreatenedHexes() {
        Set<ChessHex> ret = new HashSet<>();

        ChessHex tileEast = getPosition().getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), rightCapture));
        ChessHex tileWest = getPosition().getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), leftCapture));

        ret.add(tileEast);
        ret.add(tileWest);

        return ret;
    }

    @Override
    public Set<ChessHex> getCriticalHexes() {
        Set<ChessHex> ret = new HashSet<>();
        ret.add(getPosition());
        return ret;
    }

    @Override
    public String getType() {
        return "p";
    }
}
