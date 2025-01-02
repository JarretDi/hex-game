package main.ui;

import main.model.Board.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard gameMap = new ChessBoard(4, 4, 4);
        gameMap.printMap();
        new MapViewer(gameMap);
    }
}
