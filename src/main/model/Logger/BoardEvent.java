package main.model.Logger;

import main.model.Board.ChessHex;
import main.model.GamePieces.GamePiece;

public class BoardEvent {
    private String msg;

    GamePiece piece;
    ChessHex origin;
    ChessHex to;
    Boolean capture;
    Boolean check;

    public BoardEvent(GamePiece piece, ChessHex origin, ChessHex to, Boolean capture, Boolean check) {
        msg = piece.getType() + " " + origin.toString() + (capture? " x " : " ") + to.toString() + (check? "+" : "");

        this.piece = piece;
        this.origin = origin;
        this.to = to;
        this.capture = capture;
        this.check = check;
    }

    public String getMsg() {
        return msg;
    }
}
