package model.Map;

public class Hexagon {
    private int x;
    private int y;
    private int z;

    // EFFECTS: construct a hexagon with given x, y, z coordinates
    public Hexagon(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
