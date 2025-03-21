package main.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;
import main.model.GamePieces.GamePiece;
import main.model.GamePieces.King;

public class BoardPanel extends JPanel implements MouseListener {
    private Image backgroundImage;
    private final int hexRadius = 42;
    private final int pieceSize = (int) (hexRadius * Math.sqrt(2));

    public BoardPanel() {
        super();
        try {
            backgroundImage = ImageIO.read(new File("./src/data/157.jpg"));
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            backgroundImage = backgroundImage.getScaledInstance(((int) d.getWidth()), (int) d.getHeight(),
                    Image.SCALE_SMOOTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        addMouseListener(this);
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

        // Somewhat confusing math, see below for matrix form
        int x1 = midX - (int) ((hexRadius * x * 3 / 2));
        int y1 = midY - (int) ((hexRadius * (x * Math.sqrt(3) / 2 + y * Math.sqrt(3))));

        Hexagon hexui = new Hexagon(new Point(x1, y1), hexRadius);
        g.drawPolygon(hexui.getHexagon());
        // g.drawString(h.toString(), screenx - hexRadius / 2, screeny - hexRadius / 2);
        g.setColor(h.getColour());
        g.fillPolygon(hexui.getHexagon());
        g.setColor(Color.BLACK);

        GamePiece piece = h.getPiece();
        if (piece != null) {
            if (piece.getType() == "King" && ((King) piece).isInCheck()) {
                g.setColor(Color.red);
                g.fillOval(x1 - pieceSize / 2, y1 - pieceSize / 2, pieceSize, pieceSize);
            }
            Image pieceImage = piece.getImage().getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
            g.drawImage(pieceImage, x1 - pieceSize / 2, y1 - pieceSize / 2, this);
        }
    }
    // ((int) (Math.sqrt(3.0) * heX * radius + (3/2) * heY * radius), (int) ((3/2) *
    // heY * radius))

    @Override
    public void mouseClicked(MouseEvent e) {
        int midX = getWidth() / 2;
        int midY = getHeight() / 2;

        int x = e.getX() - midX;
        int y = e.getY() - midY;

        double x1 = ((2.0 / 3.0) * -x) / hexRadius;
        double y1 = (-1.0 / 3.0 * -x + Math.sqrt(3) / 3.0 * -y) / hexRadius;

        ChessHex hex = ChessBoard.getInstance().getTile(cubeRound(x1, y1));
        ChessBoard.getInstance().getGame().notify(hex);
        repaint();
        revalidate();
    }

    private ChessHex cubeRound(double x, double y) {
        int x2 = (int) Math.round(x);
        int y2 = (int) Math.round(y);
        x -= x2;
        y -= y2;

        int finalx;
        int finaly;
        if (Math.abs(x) >= Math.abs(y)) {
            finalx = (int) (x2 + Math.round(x + 0.5 * y));
            finaly = y2;
        } else {
            finalx = x2;
            finaly = (int) (y2 + Math.round(y + 0.5 * x));
        }

        return new ChessHex(finalx, finaly, -(finalx + finaly));
    }

    // def axial_round(x, y):
    // xgrid = round(x); ygrid = round(y)
    // x -= xgrid; y -= ygrid # remainder
    // if abs(x) >= abs(y):
    // return [xgrid + round(x + 0.5*y), ygrid]
    // else:
    // return [xgrid, ygrid + round(y + 0.5*x)]

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
