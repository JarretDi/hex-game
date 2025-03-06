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
import main.model.GamePieces.GamePiece;

public class BoardPanel extends JPanel {
    private Image backgroundImage;
    private final int hexRadius = 66; // this one seems to look the most seamless
    private final int pieceSize =  13 * hexRadius / 16;

    public BoardPanel() {
        super();
        try {
            backgroundImage = ImageIO.read(new File("./src/data/157.jpg"));
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            backgroundImage = backgroundImage.getScaledInstance(((int)d.getWidth()), (int)d.getHeight(), Image.SCALE_SMOOTH);
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

        double x1 = (int) ((hexRadius * y * Math.sqrt(3)/2));
        double y1 = (int) ((hexRadius * x * (3/2) + hexRadius * y * 1/2));

        double theta = Math.PI / 3;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        int screenx = midX - (int) (x1 * cos - y1 * sin);
        int screeny = midY - (int) (x1 * sin + y1 * cos);

        int newrad = (int) (hexRadius * Math.sqrt(3) / 3);

        Hexagon hexui = new Hexagon(new Point(screenx, screeny), newrad);
        g.drawPolygon(hexui.getHexagon());
        g.setColor(h.getColour());
        g.fillPolygon(hexui.getHexagon());
        g.setColor(Color.BLACK);

        GamePiece piece = h.getPiece();
        if (piece != null) {
            Image pieceImage = piece.getImage().getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
            g.drawImage(pieceImage, screenx - pieceSize / 2, screeny - pieceSize / 2, this);
        }
    }
    // ((int) (Math.sqrt(3.0) * heX * radius + (3/2) * heY * radius), (int) ((3/2) *
    // heY * radius))
}
