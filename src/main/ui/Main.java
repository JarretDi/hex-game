package main.ui;

import main.model.Board.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard gameMap = ChessBoard.getInstance();
        gameMap.printMap();
        new MapViewer(gameMap);
    }
}
