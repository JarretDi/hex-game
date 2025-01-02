package main.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

public class JHexPanel extends JPanel {
    private int dimX;
    private int dimY;

    public JHexPanel(Dimension dimension) {
        setPreferredSize(dimension);

        dimX = dimension.width;
        dimY = dimension.height;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);            
        Polygon p = new Polygon();
        for (int i = 0; i < 6; i++)
            p.addPoint((int) (dimX / 2 + dimX / 4 * Math.cos(i * 2 * Math.PI / 6)),
              (int) (dimY / 2 + dimY / 4 * Math.sin(i * 2 * Math.PI / 6)));        
        g.drawPolygon(p); 
    }
}
