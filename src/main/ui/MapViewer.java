package main.ui;

import javax.swing.*;
import java.awt.*;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class MapViewer extends JFrame {
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 768;
    private static final String STATUS_OK = "System OK";

    private JLabel statusLabel;
    private JPanel boardUI;

    private ChessBoard chessBoard;

    public MapViewer(ChessBoard cb) {
        super("Hexagon Chess");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.chessBoard = cb;

        statusLabel = new JLabel(STATUS_OK);
        add(statusLabel, BorderLayout.NORTH);

        boardUI = new BoardPanel();
        add(boardUI, BorderLayout.CENTER);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        pack();
        setVisible(true);
    }
}
