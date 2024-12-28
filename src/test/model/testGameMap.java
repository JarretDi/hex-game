package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.map.GameMap;
import main.model.map.Tile;

public class testGameMap {
    private GameMap testGameMap;
    private Tile testTile;

    @BeforeEach
    void setup() {
        testGameMap = new GameMap(2, 2, 2, null);
        testTile = new Tile(0, 0, 0);
    }

    @Test
    void testGetTile() {
        assertEquals(testTile, testGameMap.getTile(0, 0, 0));
        assertFalse(testTile == testGameMap.getTile(0, 0, 0));

        assertEquals(testTile, testGameMap.getTile(new Tile(0, 0, 0)));
        assertFalse(testTile == testGameMap.getTile(new Tile(0, 0, 0)));

        assertEquals(testGameMap.getTile(testTile), testGameMap.getTile(0, 0, 0));
        assertTrue(testGameMap.getTile(testTile) == testGameMap.getTile(0, 0, 0));
    }

    @Test
    void testGetNeighbours() {
        Set<Tile> expected = new HashSet<>();
        expected.add(new Tile(1, -1, 0));
        expected.add(new Tile(1, 0, -1));
        expected.add(new Tile(0, 1, -1));
        expected.add(new Tile(-1, 1, 0));
        expected.add(new Tile(-1, 0, 1));
        expected.add(new Tile(0, -1, 1));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(testTile)));
    }

    @Test
    void testGetNeighborsDifferentCentre() {
        Set<Tile> expected = new HashSet<>();
        expected.add(new Tile(0, -2, 2));
        expected.add(new Tile(1, -2, 1));
        expected.add(new Tile(1, -1, 0));
        expected.add(new Tile(0, 0, 0));
        expected.add(new Tile(-1, 0, 1));
        expected.add(new Tile(-1, -1, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(new Tile(0, -1, 1))));
    }

    @Test
    void testGetNeighboursOffMap() {
        Set<Tile> expected = new HashSet<>();
        expected.add(new Tile(1, -2, 1));
        expected.add(new Tile(0, -1, 1));
        expected.add(new Tile(-1, -1, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(new Tile(0, -2, 2))));
    }

    @Test 
    void testGetNeighboursDistance2(){
        Set<Tile> expected = new HashSet<>();

        expected.add(new Tile(1, -2, 1));
        expected.add(new Tile(0, 2, -2));
        expected.add(new Tile(2, -2, 0));
        expected.add(new Tile(2, -1, -1));
        expected.add(new Tile(2, 0, -2));
        expected.add(new Tile(1, 1, -2));
        expected.add(new Tile(-1, 2, -1));
        expected.add(new Tile(-2, 2, 0));
        expected.add(new Tile(-2, 1, 1));
        expected.add(new Tile(-2, 0, 2));
        expected.add(new Tile(-1, -1, 2));
        expected.add(new Tile(0, -2, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(testTile, 1, 2)));
    }

    @Test
    void testGetNeighboursDistance1to2() {
        Set<Tile> expected = new HashSet<>();
        expected.add(new Tile(1, -1, 0));
        expected.add(new Tile(1, 0, -1));
        expected.add(new Tile(0, 1, -1));
        expected.add(new Tile(-1, 1, 0));
        expected.add(new Tile(-1, 0, 1));
        expected.add(new Tile(0, -1, 1));

        expected.add(new Tile(1, -2, 1));
        expected.add(new Tile(2, -2, 0));
        expected.add(new Tile(2, -1, -1));
        expected.add(new Tile(2, 0, -2));
        expected.add(new Tile(1, 1, -2));
        expected.add(new Tile(0, 2, -2));
        expected.add(new Tile(-1, 2, -1));
        expected.add(new Tile(-2, 2, 0));
        expected.add(new Tile(-2, 1, 1));
        expected.add(new Tile(-2, 0, 2));
        expected.add(new Tile(-1, -1, 2));
        expected.add(new Tile(0, -2, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(testTile, 2)));
    }

    Boolean setsContainSameTilesWithSameCoords(Set<Tile> set1, Set<Tile> set2) {
        assertEquals(set1.size(), set2.size());

        for (Tile t : set1) {
            assertTrue(set2.contains(t));
        }

        return true;
    }
}
