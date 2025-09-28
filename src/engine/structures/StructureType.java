package engine.structures;

/**
 * Enum representing the types of structures that can exist on a cell.
 * Each type has a human-readable label for display purposes.
 */
public enum StructureType {
    /** Represents an empty cell with no structure. */
    EMPTY("Empty"),
    /** Represents a tower structure that can be built up to level 3. */
    TOWER("Tower"),
    /** Represents the dome structure placed at level 4. */
    DOME("Dome"),
    WALL("Wall");


    /** Display label for the structure type. */
    private final String label;

    /**
     * Constructs a StructureType with the given label.
     *
     * @param label the label to associate with the structure type
     */
    StructureType(String label) {
        this.label = label;
    }

    /**
     * Returns the label for this structure type.
     *
     * @return the structure label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the label when the enum is printed or converted to a string.
     *
     * @return the label
     */
    @Override
    public String toString() {
        return label;
    }
}
