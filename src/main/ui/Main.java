package main.ui;

import main.model.ChessGame;
import main.model.InvalidMoveException;
import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Main {
    public static void main(String[] args) {
        ChessBoard gameMap = new ChessBoard(5, 5, 5);
        gameMap.printMap();
        new MapViewer(gameMap);
    }
}
