package main.ui;

import java.awt.Point;
import java.awt.Polygon;

// Class representing a hexagon with a radius, center and polygon
// Adapted from 
public class Hexagon {
    private final int radius;
    private final Point center;
    private final Polygon hexagon;

    public Hexagon(Point center, int radius) {
        this.center = center;
        this.radius = radius;
        this.hexagon = createHexagon();
    }

    private Polygon createHexagon() {
        Polygon polygon = new Polygon();

        for (int i = 0; i < 6; i++) {
            int xval = (int) (center.x + radius
                    * Math.cos(i * 2 * Math.PI / 6D));
            int yval = (int) (center.y + radius
                    * Math.sin(i * 2 * Math.PI / 6D));
            polygon.addPoint(xval, yval);
        }

        return polygon;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public Polygon getHexagon() {
        return hexagon;
    }
}
