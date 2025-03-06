package main.ui;

import main.model.ChessGame;
import main.model.InvalidMoveException;
import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Main {
    public static void main(String[] args) {
        ChessBoard gameMap = ChessBoard.getInstance();
        ChessGame game = gameMap.getGame();
        game.setBoard();
        game.resetBoard();
        gameMap.printMap();
        gameMap.colourBoard();
        new MapViewer(gameMap);
    }
}
