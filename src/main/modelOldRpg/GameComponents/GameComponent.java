package main.modelOldRpg.GameComponents;

import java.awt.Image;

/*
 * Represents something that can be put on the map that is not a character
 * includes things like walls, buildings, etc.
 */
public interface GameComponent {

    // EFFECTS: Returns the image associated with this item
    public Image getImage();

    // EFFECTS: Returns whether this component is passable or impassible
    public Boolean isPassable();
}
