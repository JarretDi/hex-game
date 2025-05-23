package main.ui;

import main.model.ChessGame;
import main.model.Board.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessGame cg = new ChessGame(new ChessBoard(5, 5, 5), true);
        new MapViewer(cg);
    }
}
