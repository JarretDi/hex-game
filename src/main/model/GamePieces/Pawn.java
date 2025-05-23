package main.model.GamePieces;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Pawn extends GamePiece {
    private boolean enpassant;

    // Constructs an unmoved pawn at the given position with the given colour
    public Pawn(boolean colour, ChessHex position) {
        super(colour, position);

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

    // copy constructor
    public Pawn(Pawn other, ChessHex position) {
        super(other, position);

        image = other.image;
        enpassant = other.enpassant;
    }

    private int[] getForwardVector() {
        return getColour() ? ChessBoard.VECTOR_ADJ_N : ChessBoard.VECTOR_ADJ_S;
    }

    private int[] getRightCapture() {
        return getColour() ? ChessBoard.VECTOR_ADJ_NE : ChessBoard.VECTOR_ADJ_SE;
    }

    private int[] getLeftCapture() {
        return getColour() ? ChessBoard.VECTOR_ADJ_NW : ChessBoard.VECTOR_ADJ_SW;
    }

    private int[] getRightEnPassant() {
        return getColour() ? ChessBoard.VECTOR_ADJ_SW : ChessBoard.VECTOR_ADJ_NW;
    }

    private int[] getLeftEnPassant() {
        return getColour() ? ChessBoard.VECTOR_ADJ_SE : ChessBoard.VECTOR_ADJ_NE;
    }

    private int[] getBehind() {
        return getColour() ? ChessBoard.VECTOR_ADJ_S : ChessBoard.VECTOR_ADJ_N;
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        Set<ChessHex> ret = new HashSet<>();

        ChessHex tileAhead = getBoard()
                .getTile(ChessBoard.addV(getPosition().getCoords(), getForwardVector()));
        if (!tileAhead.containsPiece()) {
            ret.add(tileAhead);
        }

        if (!hasMoved()) {
            ChessHex tile2Ahead = getBoard()
                    .getTile(ChessBoard.addV(tileAhead.getCoords(), getForwardVector()));
            if (!tileAhead.containsPiece() && tile2Ahead != null && !tile2Ahead.containsPiece()) {
                ret.add(tile2Ahead);
            }
        }

        ChessHex tileEast = getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), getRightCapture()));
        if (tileEast.containsEnemyPiece(this)) {
            ret.add(tileEast);
        }

        ChessHex tileWest = getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), getLeftCapture()));
        if (tileWest.containsEnemyPiece(this)) {
            ret.add(tileWest);
        }

        ChessHex enPassantEast = getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), getLeftEnPassant()));
        if (enPassantEast != null && enPassantEast.containsEnemyPiece(this) && enPassantEast.getPiece().getType() == "p" && ((Pawn)enPassantEast.getPiece()).enPassant()) {
            ret.add(tileEast);
        }

        ChessHex enPassantWest = getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), getRightEnPassant()));
        if (enPassantWest != null && enPassantWest.containsEnemyPiece(this) && enPassantWest.getPiece().getType() == "p" && ((Pawn)enPassantWest.getPiece()).enPassant()) {
            ret.add(tileWest);
        }

        // filterCriticals(ret);
        filterChecks(ret);

        return ret;
    }

    @Override
    public Set<ChessHex> getThreatenedHexes() {
        Set<ChessHex> ret = new HashSet<>();

        ChessHex tileEast = getPosition().getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), getRightCapture()));
        ChessHex tileWest = getPosition().getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), getLeftCapture()));

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

    public Set<ChessHex> getPromotionHexes() {
        Set<ChessHex> ret = new HashSet<>();
        if (getColour() == WHITE) {
            ret.add(new ChessHex(-5, 5, 0));
            ret.add(new ChessHex(-4, 5, -1));
            ret.add(new ChessHex(-3, 5, -2));
            ret.add(new ChessHex(-2, 5, -3));
            ret.add(new ChessHex(-1, 5, -4));
            ret.add(new ChessHex(1, 4, -5));
            ret.add(new ChessHex(2, 3, -5));
            ret.add(new ChessHex(3, 2, -5));
            ret.add(new ChessHex(4, 1, -5));
            ret.add(new ChessHex(5, 0, -5));
        } else {
            ret.add(new ChessHex(5, -5, 0));
            ret.add(new ChessHex(4, -5, 1));
            ret.add(new ChessHex(3, -5, 2));
            ret.add(new ChessHex(2, -5, 3));
            ret.add(new ChessHex(1, -5, 4));
            ret.add(new ChessHex(-1, -4, 5));
            ret.add(new ChessHex(-2, -3, 5));
            ret.add(new ChessHex(-3, -2, 5));
            ret.add(new ChessHex(-4, -1, 5));
            ret.add(new ChessHex(-5, 0, 5));
        }
        return ret;
    }

    @Override
    public void update(Object obj, String msg) {
        super.update(obj, msg);

        if (msg.contains("promote") && (((Pawn) obj).equals(this))) {
            promote(msg);
        }
        if (msg == "Piece moved") {
            GamePiece pc = (GamePiece) obj;
            if (pc.getType() != "p" || (pc.getType() == "p" && !((Pawn) pc).equals(this))) {
                enpassant = false;
            } 
            if (pc.getType() == "p") {
                if (pc.getPosition().equals(getBoard().getTile(ChessBoard.addV(getPosition().getCoords(), getBehind())))) {
                    getBoard().getPieces().remove(this);
                    getBoard().getCapturedPieces().add(this);
                    getPosition().removePiece();
                    removePosition();
                }
            }

        }
    }

    @Override
    public void move(ChessHex newPosition) {
        ChessHex orig = getPosition();
        if (ChessBoard.sameVector(ChessBoard.addV(orig.getCoords(), ChessBoard.multV(getForwardVector(), 2)), newPosition.getCoords())) {
            enpassant = true;
        }
        super.move(newPosition);
    }

    private void promote(String msg) {
        ChessHex pos = getPosition();
        ChessBoard cb = getBoard();
        removePosition();
        cb.getPieces().remove(this);

        switch (msg) {
            case "Q promote":
                cb.getPieces().add(new Queen(getColour(), pos));
                break;
            case "N promote":
                cb.getPieces().add(new Knight(getColour(), pos));
                break;
            case "R promote":
                cb.getPieces().add(new Rook(getColour(), pos));
                break;
            case "B promote":
                cb.getPieces().add(new Bishop(getColour(), pos));
                break;
        }
    }

    public boolean enPassant() {
        return enpassant;
    }

    @Override
    public String getType() {
        return "p";
    }
}
