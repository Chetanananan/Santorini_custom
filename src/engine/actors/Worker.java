package engine.actors;

/**
 * Represents a worker piece controlled by a player.
 * Each worker has a unique identifier.
 */
public class Worker {
    /** The unique identifier for this worker. */
    private final String id;

    /**
     * Constructs a new Worker.
     *
     * @param id  the unique identifier for the worker
     * @param row the starting row position (currently unused)
     * @param col the starting column position (currently unused)
     */
    public Worker(String id, int row, int col) {
        this.id = id;
    }

    /**
     * Retrieves this worker’s unique identifier.
     *
     * @return the worker’s id
     */
    public String getId() {
        return id;
    }
}








