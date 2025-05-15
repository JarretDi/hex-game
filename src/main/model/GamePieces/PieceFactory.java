package main.model.GamePieces;

import main.model.Board.ChessHex;

public class PieceFactory {
    public static GamePiece makePiece(GamePiece other, ChessHex position) {
        switch (other.getType()) {
            case "p":
                return new Pawn((Pawn)other, position);
            case "N":
                return new Knight((Knight)other, position);
            case "B":
                return new Bishop((Bishop)other, position);
            case "R":
                return new Rook((Rook)other, position);
            case "Q":
                return new Queen((Queen)other, position);
            case "K":
                return new King((King)other, position);
            default:
                return null;
        }
    }
}
