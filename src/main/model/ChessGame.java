package main.model;

import java.util.HashSet;
import java.util.Set;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;
import main.model.GamePieces.*;

/*
 * Represents a class handling the operations of a chess game, including
 * managing pieces, updating the board states, etc.
 */
public class ChessGame {
    ChessBoard cb = ChessBoard.getInstance();

    private Set<GamePiece> whitePieces;
    private Set<GamePiece> blackPieces;

    public ChessGame() {
        whitePieces = new HashSet<>();
        blackPieces = new HashSet<>();
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
        whitePieces.add(new Pawn(true, cb.getTile(-4, -1, 5)));
        whitePieces.add(new Pawn(true, cb.getTile(-3, -1, 4)));
        whitePieces.add(new Pawn(true, cb.getTile(-2, -1, 3)));
        whitePieces.add(new Pawn(true, cb.getTile(-1, -1, 2)));
        whitePieces.add(new Pawn(true, cb.getTile(0, -1, 1)));
        whitePieces.add(new Pawn(true, cb.getTile(1, -2, 1)));
        whitePieces.add(new Pawn(true, cb.getTile(2, -3, 1)));
        whitePieces.add(new Pawn(true, cb.getTile(3, -4, 1)));
        whitePieces.add(new Pawn(true, cb.getTile(4, -5, 1)));

        blackPieces.add(new Pawn(false, cb.getTile(4, 1, -5)));
        blackPieces.add(new Pawn(false, cb.getTile(3, 1, -4)));
        blackPieces.add(new Pawn(false, cb.getTile(2, 1, -3)));
        blackPieces.add(new Pawn(false, cb.getTile(1, 1, -2)));
        blackPieces.add(new Pawn(false, cb.getTile(0, 1, -1)));
        blackPieces.add(new Pawn(false, cb.getTile(-1, 2, -1)));
        blackPieces.add(new Pawn(false, cb.getTile(-2, 3, -1)));
        blackPieces.add(new Pawn(false, cb.getTile(-3, 4, -1)));
        blackPieces.add(new Pawn(false, cb.getTile(-4, 5, -1)));
    }

    private void placePieces() {
        placeWhitePieces();
        placeBlackPieces();
    }

    private void placeWhitePieces() {
        whitePieces.add(new Bishop(true, cb.getTile(0, -3, 3)));
        whitePieces.add(new Bishop(true, cb.getTile(0, -4, 4)));
        whitePieces.add(new Bishop(true, cb.getTile(0, -5, 5)));

        whitePieces.add(new Knight(true, cb.getTile(2, -5, 3)));
        whitePieces.add(new Knight(true, cb.getTile(-2, -3, 5)));

        whitePieces.add(new Rook(true, cb.getTile(3, -5, 2)));
        whitePieces.add(new Rook(true, cb.getTile(-3, -2, 5)));

        whitePieces.add(new Queen(true, cb.getTile(-1, -4, 5)));
        whitePieces.add(new King(true, cb.getTile(1, -5, 4)));
    }

    private void placeBlackPieces() {
        blackPieces.add(new Bishop(false, cb.getTile(0, 3, -3)));
        blackPieces.add(new Bishop(false, cb.getTile(0, 4, -4)));
        blackPieces.add(new Bishop(false, cb.getTile(0, 5, -5)));

        blackPieces.add(new Knight(false, cb.getTile(2, 3, -5)));
        blackPieces.add(new Knight(false, cb.getTile(-2, 5, -3)));

        blackPieces.add(new Rook(false, cb.getTile(3, 2, -5)));
        blackPieces.add(new Rook(false, cb.getTile(-3, 5, -2)));

        blackPieces.add(new Queen(false, cb.getTile(-1, 5, -4)));
        blackPieces.add(new King(false, cb.getTile(1, 4, -5)));
    }

    // MODIFIES: ChessBoard
    // EFFECTS: moves a given piece to a specified hex
    // if postion.containsEnemyPiece(this) captures it, removing it from blackPieces
    // if the given position is not in piece.getMovableHexes() throws InvalidMoveException
    public void move(GamePiece piece, ChessHex position) throws InvalidMoveException {
        position = cb.getTile(position);

        if (!piece.getMovableHexes().contains(position)) {
            throw new InvalidMoveException();
        }

        if (position.containsEnemyPiece(piece)) {
            getEnemyPieces(piece).remove(position.removePiece());
        }

        piece.setPosition(position);
    }

    public Set<GamePiece> getWhitePieces() {
        return whitePieces;
    }

    public Set<GamePiece> getBlackPieces() {
        return blackPieces;
    }

    public Set<GamePiece> getEnemyPieces(GamePiece piece) {
        return piece.getColour() ? getBlackPieces() : getWhitePieces();
    }

    public Set<GamePiece> getEnemyPieces(Boolean colour) {
        return colour ? getBlackPieces() : getWhitePieces();
    }
}
