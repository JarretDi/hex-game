package main.ui;

import javax.swing.*;
import java.awt.*;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class MapViewer extends JFrame {
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 768;
    private static final String STATUS_OK = "System OK";
    private static final int hexagonSize = 100;

    private JLabel statusLabel;
    private JPanel boardUI;

    private ChessBoard chessBoard;

    public MapViewer(ChessBoard cb) {
        super("Hexagon Chess");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.chessBoard = cb;

        statusLabel = new JLabel(STATUS_OK);
        add(statusLabel, BorderLayout.NORTH);

        boardUI = new JHexPanel(new Dimension(hexagonSize, hexagonSize));
        add(boardUI, BorderLayout.CENTER);
        drawHexesOntoBoard();

        pack();
        setVisible(true);
    }

    private void drawHexesOntoBoard() {
        for (ChessHex hex : chessBoard) {
            double hexX = hex.getX();
            double hexY = hex.getY();
            JHexPanel newHex = new JHexPanel(new Dimension(hexagonSize, hexagonSize));
            boardUI.add(newHex);
            newHex.setLocation((int) (Math.sqrt(3.0) * hexX * hexagonSize + (3/2) * hexY * hexagonSize), (int) ((3/2) * hexY * hexagonSize));
        }
    }

}
