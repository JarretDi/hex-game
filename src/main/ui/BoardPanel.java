package main.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JPanel;

import main.model.ChessGame;
import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class BoardPanel extends JPanel {
    private final int r = 50;

    public BoardPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ChessHex hex : ChessBoard.getInstance()) {
            drawHexagon(hex, g);
        }
    }

    private void drawHexagon(ChessHex h, Graphics g) {
        int x = h.getCoords()[0];
        int y = h.getCoords()[1];

        int midX = getWidth() / 2;
        int midY = getHeight() / 2;

        Hexagon hexui = new Hexagon(
            new Point(
                (midX) + (int) (r * x * (3/2)),
                (midY) + (int) (r * y * Math.sqrt(3)/2)),
                r / 2);
        g.drawPolygon(hexui.getHexagon());
    }
    // ((int) (Math.sqrt(3.0) * heX * radius + (3/2) * heY * radius), (int) ((3/2) *
    // heY * radius))
}
