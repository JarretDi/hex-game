package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.ChessGame;
import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class testGameMap {
    private ChessGame testChessGame;
    private ChessBoard testGameMap;
    private ChessHex testTile;
    private Set<ChessHex> expected;

    @BeforeEach
    void setup() {
        testGameMap = new ChessBoard(5, 5, 5);
        testChessGame = new ChessGame(testGameMap, true);
        testTile = new ChessHex(0, 0, 0);
        expected = new HashSet<>();
    }

    @Test
    void testGetTile() {
        assertEquals(testTile, testGameMap.getTile(0, 0, 0));
        assertFalse(testTile == testGameMap.getTile(0, 0, 0));

        assertEquals(testTile, testGameMap.getTile(new ChessHex(0, 0, 0)));
        assertFalse(testTile == testGameMap.getTile(new ChessHex(0, 0, 0)));

        assertEquals(testGameMap.getTile(testTile), testGameMap.getTile(0, 0, 0));
        assertTrue(testGameMap.getTile(testTile) == testGameMap.getTile(0, 0, 0));
    }

    @Test
    void testGetAdjacentTiles() {
        expected.add(new ChessHex(1, -1, 0));
        expected.add(new ChessHex(1, 0, -1));
        expected.add(new ChessHex(0, 1, -1));
        expected.add(new ChessHex(-1, 1, 0));
        expected.add(new ChessHex(-1, 0, 1));
        expected.add(new ChessHex(0, -1, 1));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getAdjacentTiles(testTile)));
    }

    @Test
    void testGetAdjacentTilesDifferentCentre() {
        expected.add(new ChessHex(0, -2, 2));
        expected.add(new ChessHex(1, -2, 1));
        expected.add(new ChessHex(1, -1, 0));
        expected.add(new ChessHex(0, 0, 0));
        expected.add(new ChessHex(-1, 0, 1));
        expected.add(new ChessHex(-1, -1, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getAdjacentTiles(new ChessHex(0, -1, 1))));
    }

    @Test
    void testGetAdjacentTilesOffMap() {
        expected.add(new ChessHex(0, -4, 4));
        expected.add(new ChessHex(1, -5, 4));
        expected.add(new ChessHex(-1, -4, 5));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getAdjacentTiles(new ChessHex(0, -5, 5))));
    }

    @Test
    void testGetDiagonalTiles() {
        expected.add(new ChessHex(2, 1, -3));
        expected.add(new ChessHex(3, -1, -2));
        expected.add(new ChessHex(2, -2, 0));
        expected.add(new ChessHex(0, -1, 1));
        expected.add(new ChessHex(-1, 1, 0));
        expected.add(new ChessHex(0, 2, -2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getDiagonalTiles(new ChessHex(1, 0, -1))));
    }

    Boolean setsContainSameTilesWithSameCoords(Set<ChessHex> set1, Set<ChessHex> set2) {
        assertEquals(set1.size(), set2.size());

        for (ChessHex t : set1) {
            assertTrue(set2.contains(t));
        }

        for (ChessHex t : set2) {
            assertTrue(set1.contains(t));
        }

        return true;
    }
}
