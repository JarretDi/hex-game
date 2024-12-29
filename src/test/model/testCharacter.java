package test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Character.Character;
import main.model.Character.Damage;
import main.model.Character.Damage.DamageType;
import main.model.map.Tile;

public class testCharacter {
    Character testCharacter;
    Tile testTile;

    @BeforeEach
    void setup() {
        
        testTile = new Tile(0, -2, 2);
        testCharacter =  new Character("test character", null);
    }

    @Test
    void testConstructor() {
        assertEquals("test character", testCharacter.getName());
        // TODO: test class

        // TODO: test statboard
        // TODO: test hp
        // TODO: test actions
    }

    @Test
    void testSetPosition() {
        assertEquals(null, testTile.getCharacter());
        
        testCharacter.setPosition(testTile);
        assertEquals(testTile, testCharacter.getPosition());
        assertEquals(testCharacter, testTile.getCharacter());

        Tile newTestTile = new Tile(1, -1, 0);

        assertEquals(null, newTestTile.getCharacter());

        testCharacter.setPosition(newTestTile);
        assertEquals(newTestTile, testCharacter.getPosition());
        assertEquals(testCharacter, newTestTile.getCharacter());
        assertEquals(null, testTile.getCharacter());
    }

    @Test
    void testTakeDamage() {
        // TODO: when hp is decided
        Damage damage = new Damage(10, DamageType.Sharp);
    }
}
