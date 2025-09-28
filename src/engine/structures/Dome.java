package engine.structures;

import java.awt.*;

/**
 * Represents the Dome structure, the final level in the build sequence.
 * Domes cannot be built upon or moved onto.
 */
public class Dome extends Structure {

    /**
     * Constructs a Dome with structure type DOME and level 4.
     */
    public Dome() {
        super(StructureType.DOME, 4);
    }

    /**
     * Returns the label text to display for a dome.
     *
     * @return "Dome"
     */
    @Override
    public String getLabel() {
        return "Dome";
    }

    /**
     * Returns the color used to render the dome on the board.
     *
     * @return a purple color (RGB 128, 0, 128)
     */
    @Override
    public Color getColor() {
        return new Color(128, 0, 128);
    }
}