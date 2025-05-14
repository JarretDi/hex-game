package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;
import main.model.GamePieces.GamePiece;
import main.model.GamePieces.King;
import main.model.GamePieces.Pawn;

public class testPiece {
    ChessBoard cb;
    GamePiece whiteKing;
    ChessHex testTile;

    Set<ChessHex> expected;

    @BeforeEach
    void setup() {
        cb = new ChessBoard(5, 5, 5);
        testTile = new ChessHex(0, -2, 2);
        whiteKing = new King(true, new ChessHex(0, 0, 0));
        expected = new HashSet<>();
    }

    @Test
    void testConstructor() {
        assertTrue(whiteKing.getColour());

        GamePiece blackKing = new King(false, new ChessHex(0, 2, -2));
        assertFalse(blackKing.getColour());
    }

    @Test
    void testSetPosition() {
        assertEquals(null, testTile.getPiece());
        
        whiteKing.setPosition(testTile);
        assertEquals(testTile, whiteKing.getPosition());
        assertEquals(whiteKing, testTile.getPiece());

        ChessHex newTestTile = new ChessHex(1, -1, 0);

        assertEquals(null, newTestTile.getPiece());

        whiteKing.setPosition(newTestTile);
        assertEquals(newTestTile, whiteKing.getPosition());
        assertEquals(whiteKing, newTestTile.getPiece());
        assertEquals(null, testTile.getPiece());
    }

    @Test
    void testPawnMove() {
        GamePiece testPawn = cb.getTile(0, -1, 1).getPiece();
        assertEquals("p", testPawn.getType());

        expected.add(new ChessHex(0, 0, 0));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testPawn.getMovableHexes()));
    }

    @Test
    void testPawnDoubleMove() {
        GamePiece testPawn = cb.getTile(-2, -1, 3).getPiece();
        assertEquals("p", testPawn.getType());

        expected.add(new ChessHex(-2, 0, 2));
        expected.add(new ChessHex(-2, 1, 1));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testPawn.getMovableHexes()));
    }

    @Test
    void testKnightMove() {
        GamePiece testKnight = cb.getTile(2, -5, 3).getPiece();
        assertEquals("N", testKnight.getType());

        expected.add(new ChessHex(-1, -3, 4));
        expected.add(new ChessHex(0, -2, 2));
        expected.add(new ChessHex(3, -3, 0));
        expected.add(new ChessHex(4, -4, 0));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testKnight.getMovableHexes()));
    }

    @Test
    void testBishopMove() {
        GamePiece testBishop = cb.getTile(0, -4, 4).getPiece();
        assertEquals("B", testBishop.getType());

        expected.add(new ChessHex(-1, -2, 3));
        expected.add(new ChessHex(-2, 0, 2));
        expected.add(new ChessHex(-3, 2, 1));
        expected.add(new ChessHex(-4, 4, 0));

        expected.add(new ChessHex(1, -3, 2));
        expected.add(new ChessHex(2, -2, 0));
        expected.add(new ChessHex(3, -1, -2));
        expected.add(new ChessHex(4, 0, -4));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testBishop.getMovableHexes()));
    }

    @Test
    void testRookMove() {
        GamePiece testRook = cb.getTile(-3, -2, 5).getPiece();
        assertEquals("R", testRook.getType());

        expected.add(new ChessHex(-2, -2, 4));
        expected.add(new ChessHex(-1, -2, 3));
        expected.add(new ChessHex(0, -2, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testRook.getMovableHexes()));
    }

    @Test
    void testQueenMove() {
        GamePiece testQueen = cb.getTile(-1, -4, 5).getPiece();
        assertEquals("Q", testQueen.getType());

        expected.add(new ChessHex(-1, -3, 4));
        expected.add(new ChessHex(-1, -2, 3));

        expected.add(new ChessHex(-2, -2, 4));
        expected.add(new ChessHex(-3, 0, 3));
        expected.add(new ChessHex(-4, 2, 2));
        expected.add(new ChessHex(-5, 4, 1));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testQueen.getMovableHexes()));
    }

    @Test
    void testKingMove() {
        GamePiece testKing = cb.getTile(1, -5, 4).getPiece();
        assertEquals("K", testKing.getType());

        expected.add(new ChessHex(1, -4, 3));
        expected.add(new ChessHex(2, -4, 2));

        assertTrue(setsContainSameTilesWithSameCoords(expected, testKing.getMovableHexes()));
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
