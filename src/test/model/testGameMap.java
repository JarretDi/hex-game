package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class testGameMap {
    private ChessBoard testGameMap;
    private ChessHex testTile;
    private Set<ChessHex> expected;

    @BeforeEach
    void setup() {
        testGameMap = new ChessBoard(2, 2, 2);
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
    void testGetNeighbours() {
        expected.add(new ChessHex(1, -1, 0));
        expected.add(new ChessHex(1, 0, -1));
        expected.add(new ChessHex(0, 1, -1));
        expected.add(new ChessHex(-1, 1, 0));
        expected.add(new ChessHex(-1, 0, 1));
        expected.add(new ChessHex(0, -1, 1));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(testTile)));
    }

    @Test
    void testGetNeighborsDifferentCentre() {
        expected.add(new ChessHex(0, -2, 2));
        expected.add(new ChessHex(1, -2, 1));
        expected.add(new ChessHex(1, -1, 0));
        expected.add(new ChessHex(0, 0, 0));
        expected.add(new ChessHex(-1, 0, 1));
        expected.add(new ChessHex(-1, -1, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(new ChessHex(0, -1, 1))));
    }

    @Test
    void testGetNeighboursOffMap() {
        expected.add(new ChessHex(1, -2, 1));
        expected.add(new ChessHex(0, -1, 1));
        expected.add(new ChessHex(-1, -1, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(new ChessHex(0, -2, 2))));
    }

    @Test 
    void testGetNeighboursDistance2(){
        expected.add(new ChessHex(1, -2, 1));
        expected.add(new ChessHex(0, 2, -2));
        expected.add(new ChessHex(2, -2, 0));
        expected.add(new ChessHex(2, -1, -1));
        expected.add(new ChessHex(2, 0, -2));
        expected.add(new ChessHex(1, 1, -2));
        expected.add(new ChessHex(-1, 2, -1));
        expected.add(new ChessHex(-2, 2, 0));
        expected.add(new ChessHex(-2, 1, 1));
        expected.add(new ChessHex(-2, 0, 2));
        expected.add(new ChessHex(-1, -1, 2));
        expected.add(new ChessHex(0, -2, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(testTile, 1, 2)));
    }

    @Test
    void testGetNeighboursDistance1to2() {
        expected.add(new ChessHex(1, -1, 0));
        expected.add(new ChessHex(1, 0, -1));
        expected.add(new ChessHex(0, 1, -1));
        expected.add(new ChessHex(-1, 1, 0));
        expected.add(new ChessHex(-1, 0, 1));
        expected.add(new ChessHex(0, -1, 1));

        expected.add(new ChessHex(1, -2, 1));
        expected.add(new ChessHex(2, -2, 0));
        expected.add(new ChessHex(2, -1, -1));
        expected.add(new ChessHex(2, 0, -2));
        expected.add(new ChessHex(1, 1, -2));
        expected.add(new ChessHex(0, 2, -2));
        expected.add(new ChessHex(-1, 2, -1));
        expected.add(new ChessHex(-2, 2, 0));
        expected.add(new ChessHex(-2, 1, 1));
        expected.add(new ChessHex(-2, 0, 2));
        expected.add(new ChessHex(-1, -1, 2));
        expected.add(new ChessHex(0, -2, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getNeighbours(testTile, 2)));
    }

    @Test
    void testGetLine() {
        expected.add(new ChessHex(1,-1,0));
        expected.add(new ChessHex(2,-2,0));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getLine(testTile, ChessBoard.VECTOR_X_POS, 2)));
    }

    @Test
    void testGetLineDifferentTile() {
        ChessHex newTestTile = new ChessHex(-1,-1,2);

        expected.add(new ChessHex(-1,0,1));
        expected.add(new ChessHex(-1,1,0));
        expected.add(new ChessHex(-1,2,-1));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getLine(newTestTile, ChessBoard.VECTOR_Z_POS, 3)));
    }

    @Test
    void testGetLineOffTheMap() {
        expected.add(new ChessHex(-1,0,1));
        expected.add(new ChessHex(-2,0,2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testGameMap.getLine(testTile, ChessBoard.VECTOR_Y_NEG, 3)));
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
