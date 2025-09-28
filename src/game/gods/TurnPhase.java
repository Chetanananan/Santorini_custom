package game.gods;

/**
 * Enum representing the phases of a player's turn.
 * A turn consists of a MOVE phase followed by a BUILD phase.
 */
public enum TurnPhase {
    /** The phase in which a player selects and moves a worker. */
    MOVE("Move"),

    /** The phase in which a player selects a cell to build on. */
    BUILD("Build");

    /** Human-readable label for the phase. */
    private final String label;

    /**
     * Constructs a TurnPhase with the given label.
     *
     * @param label the display label for the phase
     */
    TurnPhase(String label) {
        this.label = label;
    }

    /**
     * Returns the label for this turn phase.
     *
     * @return the phase label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the label when the enum is converted to a string.
     *
     * @return the label string
     */
    @Override
    public String toString() {
        return label;
    }
}
