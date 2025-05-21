package main.model;

import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;
import main.model.GamePieces.*;
import main.model.Logger.BoardEvent;
import main.model.Logger.BoardLogger;

/*
 * Represents a class handling the operations of a chess game, including
 * managing pieces, updating the board states, etc.
 */
public class ChessGame extends Observable {
    private ChessBoard cb;

    private GamePiece selectedPiece;

    public ChessGame(ChessBoard board, Boolean shouldReset) {
        cb = board;

        if (shouldReset) {
            clearBoard();
            placePawns();
            placePieces();
            BoardLogger.getInstance().logBoard(cb);
        }

        addObservers();
    }

    private void clearBoard() {
        for (ChessHex hex : cb) {
            hex.removePiece();
        }
    }

    private void placePawns() {
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(-4, -1, 5)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(-3, -1, 4)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(-2, -1, 3)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(-1, -1, 2)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(0, -1, 1)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(1, -2, 1)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(2, -3, 1)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(3, -4, 1)));
        cb.getPieces().add(new Pawn(GamePiece.WHITE, cb.getTile(4, -5, 1)));

        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(4, 1, -5)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(3, 1, -4)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(2, 1, -3)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(1, 1, -2)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(0, 1, -1)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(-1, 2, -1)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(-2, 3, -1)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(-3, 4, -1)));
        cb.getPieces().add(new Pawn(GamePiece.BLACK, cb.getTile(-4, 5, -1)));
    }

    private void placePieces() {
        cb.getPieces().add(new Bishop(GamePiece.WHITE, cb.getTile(0, -3, 3)));
        cb.getPieces().add(new Bishop(GamePiece.WHITE, cb.getTile(0, -4, 4)));
        cb.getPieces().add(new Bishop(GamePiece.WHITE, cb.getTile(0, -5, 5)));

        cb.getPieces().add(new Knight(GamePiece.WHITE, cb.getTile(2, -5, 3)));
        cb.getPieces().add(new Knight(GamePiece.WHITE, cb.getTile(-2, -3, 5)));

        cb.getPieces().add(new Rook(GamePiece.WHITE, cb.getTile(3, -5, 2)));
        cb.getPieces().add(new Rook(GamePiece.WHITE, cb.getTile(-3, -2, 5)));

        cb.getPieces().add(new Queen(GamePiece.WHITE, cb.getTile(-1, -4, 5)));
        cb.getPieces().add(new King(GamePiece.WHITE, cb.getTile(1, -5, 4)));

        cb.getPieces().add(new Bishop(GamePiece.BLACK, cb.getTile(0, 3, -3)));
        cb.getPieces().add(new Bishop(GamePiece.BLACK, cb.getTile(0, 4, -4)));
        cb.getPieces().add(new Bishop(GamePiece.BLACK, cb.getTile(0, 5, -5)));

        cb.getPieces().add(new Knight(GamePiece.BLACK, cb.getTile(2, 3, -5)));
        cb.getPieces().add(new Knight(GamePiece.BLACK, cb.getTile(-2, 5, -3)));

        cb.getPieces().add(new Rook(GamePiece.BLACK, cb.getTile(3, 2, -5)));
        cb.getPieces().add(new Rook(GamePiece.BLACK, cb.getTile(-3, 5, -2)));

        cb.getPieces().add(new Queen(GamePiece.BLACK, cb.getTile(-1, 5, -4)));
        cb.getPieces().add(new King(GamePiece.BLACK, cb.getTile(1, 4, -5)));
    }

    private void addObservers() {
        observers.clear();
        for (GamePiece piece : cb.getPieces()) {
            observers.add(piece);
        }
        for (ChessHex hex : cb.getMap()) {
            observers.add(hex);
        }
    }

    // MODIFIES: ChessBoard
    // EFFECTS: moves a given piece to a specified hex
    // if postion.containsEnemyPiece(piece) captures it, removing it from pieces
    // if the given position is not in piece.getMovableHexes() or it isn't that
    // players turn
    // throws InvalidMoveException
    public void move(GamePiece piece, ChessHex newPosition) throws InvalidMoveException {
        ChessHex originalPosition = piece.getPosition();
        newPosition = cb.getTile(newPosition);
        boolean isCapture = false;
        String promote = "";
        boolean isCheck = false;
        boolean isCheckmate = false;

        if (!validMove(piece, newPosition)) {
            throw new InvalidMoveException();
        }

        if (newPosition.containsEnemyPiece(piece)) {
            GamePiece enemyPiece = newPosition.removePiece();
            cb.getPieces().remove(enemyPiece);
            cb.getCapturedPieces().add(enemyPiece);
            observers.remove(enemyPiece);
            isCapture = true;
        }

        piece.setPosition(newPosition);
        piece.move();
        selectPiece(null);

        promote = checkPromotions(piece, newPosition);

        if (cb.isInCheck()) {
            isCheck = true;
        }
        if (cb.isCheckmate()) {
            isCheckmate = true;
            isCheck = false;
            notifyObservers(this, "Checkmate");
        }

        cb.swapTurn();

        BoardLogger.getInstance().logBoard(cb);
        BoardLogger.getInstance()
                .addEvent(
                        new BoardEvent(piece, originalPosition, newPosition, isCapture, promote, isCheck, isCheckmate));
    }

    // EFFECTS: checks to see whether a pawn has landed on a promotion square
    // if one has, prompt user for input on what to promote to
    // then, sends out a message of promotion, and then returns the promotion
    // notation
    private String checkPromotions(GamePiece piece, ChessHex newPosition) {
        if (piece.getType() != "p" || !((Pawn) piece).getPromotionHexes().contains(newPosition)) {
            return "";
        }

        ImageIcon[] icons = getPieceIcons(piece.getColour());
        int selection = JOptionPane.showOptionDialog(null, 
                "What does this pawn promote to?", 
                "Promotion", 
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, 
                piece.getColour() ? new ImageIcon("./src/data/pieces/white-pawn.png") : new ImageIcon("./src/data/pieces/black-pawn.png"),
                icons, icons[0]);

        switch (selection) {
            case 0:
                notifyObservers(piece, "Q promote");
                return " = Q";
            case 1:
                notifyObservers(piece, "N promote");
                return " = N";
            case 2:
                notifyObservers(piece, "R promote");
                return " = R";
            case 3:
                notifyObservers(piece, "B promote");
                return " = B";
            default:
                return "";
        }
    }

    private ImageIcon[] getPieceIcons(boolean colour) {
        if (colour == GamePiece.WHITE) {
            ImageIcon WQueen = new ImageIcon("./src/data/pieces/white-queen.png");
            ImageIcon WKnight = new ImageIcon("./src/data/pieces/white-knight.png");
            ImageIcon WRook = new ImageIcon("./src/data/pieces/white-rook.png");
            ImageIcon WBishop = new ImageIcon("./src/data/pieces/white-bishop.png");
            ImageIcon[] ret = { WQueen, WKnight, WRook, WBishop };
            return ret;
        } else {
            ImageIcon BQueen = new ImageIcon("./src/data/pieces/black-queen.png");
            ImageIcon BKnight = new ImageIcon("./src/data/pieces/black-knight.png");
            ImageIcon BRook = new ImageIcon("./src/data/pieces/black-rook.png");
            ImageIcon BBishop = new ImageIcon("./src/data/pieces/black-bishop.png");
            ImageIcon[] ret = { BQueen, BKnight, BRook, BBishop };
            return ret;
        }
    }

    private boolean validMove(GamePiece piece, ChessHex position) {
        if (cb.getTurn() != piece.getColour()) {
            return false;
        }
        if (!piece.getMovableHexes().contains(position)) {
            return false;
        }

        return true;
    }

    public void notify(ChessHex hex) {
        if (hex == null || hex.containsPiece() && selectedPiece == hex.getPiece()) {
            selectPiece(null);
        } else if (hex.containsPiece() && (selectedPiece == null || hex.getPiece().getColour() == cb.getTurn())) {
            selectPiece(hex.getPiece());
        } else if (selectedPiece != null && selectedPiece.getMovableHexes().contains(hex)) {
            try {
                move(selectedPiece, hex);
            } catch (InvalidMoveException e) {
                System.out.println("Invalid move");
            }
        }
    }

    public void selectPiece(GamePiece piece) {
        if (piece == null) {
            selectedPiece = null;
            notifyObservers(null, "All unselected");
        }

        selectedPiece = piece;
        notifyObservers(piece, "Piece selected");
    }

    public ChessBoard getBoard() {
        return cb;
    }

    public void setBoard(ChessBoard cb) {
        this.cb = cb;
    }
}
