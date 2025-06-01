package main.model.Logger;

import org.json.JSONObject;

import main.model.Board.ChessHex;
import main.model.GamePieces.GamePiece;
import main.persistenceJson.Writable;

public class BoardEvent {
    private GamePiece piece;
    private ChessHex origin;
    private ChessHex to;
    private boolean capture;
    private String promote;
    private boolean check;
    private boolean checkmate;

    public BoardEvent(GamePiece piece, ChessHex origin, ChessHex to, boolean capture, String promote, boolean check, boolean checkmate) {
        this.piece = piece;
        this.origin = origin;
        this.to = to;
        this.capture = capture;
        this.promote = promote;
        this.check = check;
        this.checkmate = checkmate;
    }

    public String getMsg() {
        return piece.getType() + " " + origin.toString() + (capture? " x " : " ") + to.toString() + promote + (check? "+" : "") + (checkmate? "#" : "");
    }
}
