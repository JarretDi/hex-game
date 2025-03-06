package main.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class BoardPanel extends JPanel {
    private Image backgroundImage;
    private final int hexRadius = 66; // this one seems to look the most seamless

    public BoardPanel() {
        super();
        try {
            backgroundImage = ImageIO.read(new File("./src/data/157.jpg"));
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            backgroundImage = backgroundImage.getScaledInstance(((int)d.getWidth()), (int)d.getHeight(), Image.SCALE_DEFAULT);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
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
                (midX) + (int) (hexRadius * y * Math.sqrt(3)/2),
                (midY) + (int) (hexRadius * x * (3/2) + hexRadius * y * 1/2)),
                (int) (hexRadius * Math.sqrt(3) / 3));
        g.drawPolygon(hexui.getHexagon());
        g.setColor(h.getColour());
        g.fillPolygon(hexui.getHexagon());
        g.setColor(Color.BLACK);
    }
    // ((int) (Math.sqrt(3.0) * heX * radius + (3/2) * heY * radius), (int) ((3/2) *
    // heY * radius))
}
