package main.ui;

import main.model.Board.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard gameMap = new ChessBoard(5, 5, 5);
        gameMap.printMap();
        new MapViewer(gameMap);
    }
}
