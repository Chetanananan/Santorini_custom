package engine.structures;

import java.awt.*;

/**
 * Abstract base class representing a structure on a cell.
 * Structures have a type (EMPTY, TOWER, DOME) and a level (0–4).
 * Subclasses define the label and color for rendering.
 */
public abstract class Structure {
    /** The type of structure (e.g., TOWER, DOME). */
    private final StructureType type;
    /** The level of the structure, from 0 (empty) to 4 (dome). */
    private int level;

    /**
     * Constructs a structure with the given type and level.
     *
     * @param type  the structure type
     * @param level the current level
     */
    public Structure(StructureType type, int level) {
        this.type = type;
        this.level = level;
    }

    /**
     * Determines whether this structure can be built upon.
     * Only empty cells or tower levels below 3 are buildable.
     *
     * @return {@code true} if further building is allowed
     */
    public boolean isBuildable() {
        return type == StructureType.EMPTY || (type == StructureType.TOWER && level < 3);
    }

    /**
     * Returns the type of this structure.
     *
     * @return the {@link StructureType}
     */
    public StructureType getType() {
        return type;
    }

    /**
     * Returns the level of this structure.
     *
     * @return the integer level (0–4)
     */
    public int getLevel() {
        return level;
    }

    /**
     * Increments the level of this structure if it is below 4.
     * Used during tower upgrades.
     */
    public void incrementLevel() {
        if (level < 4) {
            this.level += 1;
        }
    }

    /**
     * Factory method to generate the next structure after a build action.
     *
     * @param structure the current structure (can be null)
     * @return the updated or new structure instance
     */
    public static Structure getNextStructure(Structure structure) {
        if (structure == null || structure.getType() == StructureType.EMPTY) {
            return new Tower(1);
        }
        if (structure.getType() == StructureType.TOWER) {
            structure.incrementLevel();
            if (structure.getLevel() == 4) {
                return new Dome();
            }
            return structure;
        }
        // DOME or unknown structures remain unchanged
        return structure;
    }

    /**
     * Returns the display label (e.g., "1", "Dome") for UI rendering.
     *
     * @return the label string
     */
    public abstract String getLabel();

    /**
     * Returns the display color for rendering the structure.
     *
     * @return the structure's color
     */
    public abstract Color getColor();

    public boolean isWall() {
        return this.type == StructureType.WALL;
    }
    public boolean isFullyBuilt() {
        return isWall() && level >= 2;
    }
    public void buildWallPhase() {
        if (isWall() && level < 2) {
            level++;
        }
    }
    public void breakWallPhase() {
        if (isWall() && level > 0) {
            level--;
        }
    }
}