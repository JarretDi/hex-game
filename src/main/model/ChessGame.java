package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;
import main.model.GamePieces.*;

/*
 * Represents a class handling the operations of a chess game, including
 * managing pieces, updating the board states, etc.
 */
public class ChessGame {
    private ChessBoard cb;

    private GamePiece selectedPiece;
    private boolean turn;

    private Set<GamePiece> pieces;

    private List<GamePiece> capturedPieces;

    public ChessGame() {
        pieces = new HashSet<>();
        capturedPieces = new ArrayList<>();
        turn = GamePiece.WHITE;
    }

    public void setBoard() {
        cb = ChessBoard.getInstance();
    }

    public void resetBoard() {
        clearBoard();
        placePawns();
        placePieces();
    }

    private void clearBoard() {
        for (ChessHex hex : cb) {
            hex.removePiece();
        }
    }

    private void placePawns() {
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(-4, -1, 5)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(-3, -1, 4)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(-2, -1, 3)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(-1, -1, 2)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(0, -1, 1)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(1, -2, 1)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(2, -3, 1)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(3, -4, 1)));
        pieces.add(new Pawn(GamePiece.WHITE, cb.getTile(4, -5, 1)));

        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(4, 1, -5)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(3, 1, -4)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(2, 1, -3)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(1, 1, -2)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(0, 1, -1)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(-1, 2, -1)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(-2, 3, -1)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(-3, 4, -1)));
        pieces.add(new Pawn(GamePiece.BLACK, cb.getTile(-4, 5, -1)));
    }

    private void placePieces() {
        pieces.add(new Bishop(GamePiece.WHITE, cb.getTile(0, -3, 3)));
        pieces.add(new Bishop(GamePiece.WHITE, cb.getTile(0, -4, 4)));
        pieces.add(new Bishop(GamePiece.WHITE, cb.getTile(0, -5, 5)));

        pieces.add(new Knight(GamePiece.WHITE, cb.getTile(2, -5, 3)));
        pieces.add(new Knight(GamePiece.WHITE, cb.getTile(-2, -3, 5)));

        pieces.add(new Rook(GamePiece.WHITE, cb.getTile(3, -5, 2)));
        pieces.add(new Rook(GamePiece.WHITE, cb.getTile(-3, -2, 5)));

        pieces.add(new Queen(GamePiece.WHITE, cb.getTile(-1, -4, 5)));
        pieces.add(new King(GamePiece.WHITE, cb.getTile(1, -5, 4)));

        pieces.add(new Bishop(GamePiece.BLACK, cb.getTile(0, 3, -3)));
        pieces.add(new Bishop(GamePiece.BLACK, cb.getTile(0, 4, -4)));
        pieces.add(new Bishop(GamePiece.BLACK, cb.getTile(0, 5, -5)));

        pieces.add(new Knight(GamePiece.BLACK, cb.getTile(2, 3, -5)));
        pieces.add(new Knight(GamePiece.BLACK, cb.getTile(-2, 5, -3)));

        pieces.add(new Rook(GamePiece.BLACK, cb.getTile(3, 2, -5)));
        pieces.add(new Rook(GamePiece.BLACK, cb.getTile(-3, 5, -2)));

        pieces.add(new Queen(GamePiece.BLACK, cb.getTile(-1, 5, -4)));
        pieces.add(new King(GamePiece.BLACK, cb.getTile(1, 4, -5)));
    }

    // MODIFIES: ChessBoard
    // EFFECTS: moves a given piece to a specified hex
    // if postion.containsEnemyPiece(this) captures it, removing it from pieces
    // if the given position is not in piece.getMovableHexes() or it isn't that players turn
    // throws InvalidMoveException
    public void move(GamePiece piece, ChessHex position) throws InvalidMoveException {
        position = cb.getTile(position);

        if (!piece.getMovableHexes().contains(position) || turn != piece.getColour()) {
            throw new InvalidMoveException();
        }

        if (position.containsEnemyPiece(piece)) {
            GamePiece enemyPiece = position.removePiece();
            getEnemyPieces(piece).remove(enemyPiece);
            capturedPieces.add(enemyPiece);
        }

        piece.setPosition(position);
        selectedPiece = null;
        turn = !turn;
    }

    public void notify(ChessHex hex) {
        if (hex == null) {
            selectedPiece = null;
        } else if (selectedPiece == null && hex.containsPiece()) {
            selectedPiece = hex.getPiece();
        } else if (hex.containsPiece() && selectedPiece == hex.getPiece()) {
            selectedPiece = null;
        } if (selectedPiece != null && selectedPiece.getMovableHexes().contains(hex)) {
            try {
                move(selectedPiece, hex);
            } catch (InvalidMoveException e) {
                System.out.println("Invalid move");
            }
        } 
    }

    public Set<GamePiece> getWhitePieces() {
        Set<GamePiece> ret = new HashSet<>();
        for (GamePiece p : pieces) {
            if (p.getColour()) {
                ret.add(p);
            }
        }
        return ret;
    }

    public Set<GamePiece> getBlackPieces() {
        Set<GamePiece> ret = new HashSet<>();
        for (GamePiece p : pieces) {
            if (!p.getColour()) {
                ret.add(p);
            }
        }
        return ret;
    }

    public List<GamePiece> getCapturedPieces() {
        return capturedPieces;
    }

    public Set<GamePiece> getEnemyPieces(GamePiece piece) {
        return piece.getColour() ? getBlackPieces() : getWhitePieces();
    }

    public Set<GamePiece> getEnemyPieces(Boolean colour) {
        return colour ?  getBlackPieces() : getWhitePieces();
    }
}
