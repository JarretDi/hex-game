package test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Board.ChessHex;
import main.model.GamePieces.GamePiece;
import main.model.GamePieces.King;

public class testPiece {
    GamePiece whiteKing;
    ChessHex testTile;

    @BeforeEach
    void setup() {
        
        testTile = new ChessHex(0, -2, 2);
        whiteKing = new King(true);
    }

    @Test
    void testConstructor() {
        assertTrue(whiteKing.getColour());

        GamePiece blackKing = new King(false);
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

    }

    @Test
    void testKnightMove() {

    }

    @Test
    void testBishopMove() {

    }

    @Test
    void testRookMove() {

    }

    @Test
    void testQueenMove() {
        
    }

    @Test
    void testKingMove() {
        // TODO: when check mechanics are implemented
    }
}
