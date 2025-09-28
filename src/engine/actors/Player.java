package engine.actors;

import game.gods.God;
import engine.positions.Cell;
import engine.positions.GameBoard;
import engine.positions.GameEngine;
import engine.structures.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game, holding a name, an assigned God power,
 * a collection of workers, and a unique identifier. Provides methods to
 * query available moves, take a turn via the God’s logic, and track turn state.
 */
public class Player {
    /** The player’s display name. */
    private final String NAME;
    /** The God power card assigned to this player. */
    private final God GOD;
    /** The list of workers this player controls. */
    private final List<Worker> WORKERS;
    /** A unique identifier for this player. */
    private String id;

    /** Flag indicating whether this player has begun and not yet completed their turn. */
    private boolean turnProgress = false;

    /**
     * Constructs a new Player.
     *
     * @param name    the player’s name
     * @param god     the God power assigned to the player
     * @param workers the list of workers controlled by the player
     * @param id      a unique player identifier
     */
    public Player(String name, God god, List<Worker> workers, String id) {
        this.NAME = name;
        this.GOD = god;
        this.WORKERS = workers;
        this.id = id;
    }

    /**
     * Retrieves the player’s name.
     *
     * @return the NAME of this player
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * Retrieves the God power assigned to this player.
     *
     * @return the GOD of this player
     */
    public God getGod() {
        return GOD;
    }

    /**
     * Retrieves the list of workers under this player’s control.
     *
     * @return the WORKERS list
     */
    public List<Worker> getWORKERS() {
        return WORKERS;
    }

    /**
     * Determines whether this player has at least one valid move remaining.
     * <p>
     * Iterates each worker, checks adjacent cells on the shared GameBoard,
     * and verifies movement rules (cell exists, unoccupied, and height difference ≤ 1).
     *
     * @return {@code true} if any worker can move, {@code false} otherwise
     */
    public boolean hasValidMoves() {
        boolean valid = false;
        GameBoard board = GameEngine.getInstance().getBoard();
        List<Worker> workers = this.getWORKERS();
        for (Worker worker : workers) {
            Cell workersCell = board.getLocationOf(worker);
            ArrayList<Cell> adjacentCells = board.getAdjacentCells(workersCell);

            for (Cell cell : adjacentCells) {
                if (board.hasCell(cell) && board.getWorkerAt(cell) == null) {
                    Structure fromStructure = workersCell.getStructure();
                    Structure toStructure = cell.getStructure();

                    int fromLevel = (fromStructure != null) ? fromStructure.getLevel() : 0;
                    int toLevel = (toStructure != null) ? toStructure.getLevel() : 0;

                    if ((toLevel - fromLevel) <= 1) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    /**
     * Delegates the player's turn logic to their assigned God power.
     *
     * @param clickedCell     the cell clicked by the user
     * @param selectedWorker  the worker selected to perform an action
     */
    public void takeTurn(Cell clickedCell, Worker selectedWorker) {
        getGod().takeTurn(clickedCell, selectedWorker);
    }

    /**
     * Checks whether this player’s turn is currently in progress.
     *
     * @return {@code true} if the turn has started and not yet completed
     */
    public boolean isTurnProgress() {
        return turnProgress;
    }

    /**
     * Sets the turn-in-progress flag for this player.
     *
     * @param turnProgress {@code true} to mark turn as started, {@code false} when finished
     */
    public void setTurnProgress(boolean turnProgress) {
        this.turnProgress = turnProgress;
    }

    /**
     * Retrieves this player’s unique identifier.
     *
     * @return the id string
     */
    public String getId() {
        return id;
    }
}