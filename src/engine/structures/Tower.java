package engine.structures;

import java.awt.*;

/**
 * Represents a Tower structure that can be built up to level 3.
 * Each level has a unique color and label ("L1", "L2", "L3").
 */
public class Tower extends Structure {

    /**
     * Constructs a Tower structure at the specified level.
     *
     * @param level the level of the tower (1 to 3)
     */
    public Tower(int level) {
        super(StructureType.TOWER, level);
    }

    /**
     * Returns the label representing this tower level (e.g., "L1").
     *
     * @return a string label like "L1", "L2", etc.
     */
    @Override
    public String getLabel() {
        return "L" + getLevel();
    }

    /**
     * Returns the color corresponding to the tower level.
     *
     * @return a {@link Color} used for UI rendering
     */
    @Override
    public Color getColor() {
        switch (getLevel()) {
            case 1:
                return new Color(173, 216, 230); // Light Blue
            case 2:
                return new Color(100, 149, 237); // Cornflower Blue
            case 3:
                return new Color(25, 25, 112);   // Midnight Blue
            default:
                return Color.LIGHT_GRAY;         // Fallback
        }
    }
}

