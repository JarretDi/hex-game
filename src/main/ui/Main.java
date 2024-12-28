package main.ui;

import main.model.map.GameMap;

public class Main {
    public static void main(String[] args) throws Exception {
        GameMap testMap = new GameMap(2, 2, 2, null);
        testMap.printMap();
    }
}
