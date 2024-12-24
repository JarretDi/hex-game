package model.map;

import java.util.HashMap;

public class Map {
    private HashMap<Hexagon, Tile> map;

    public Map(int maxX, int maxY, int maxZ, TileGenerator tileGenerator) {
        map = new HashMap<>();
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                for (int z = 0; z <= maxZ; z++) {
                    map.put(new Hexagon(x, y, z), new Tile());
                }
            }
        }
    }

    public void printMap() {
        for (Hexagon h : map.keySet()) {
            System.out.println(h.toString() + " " + map.get(h).getType());
        }
    }
}
