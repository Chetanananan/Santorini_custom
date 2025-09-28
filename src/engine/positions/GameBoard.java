package engine.positions;

import engine.actors.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract representation of the game board, maintaining bidirectional mappings
 * between Workers and their Cells. Provides common placement, movement, and occupancy
 * operations, while deferring board‐specific cell existence and adjacency logic to subclasses.
 */
public abstract class GameBoard {
    /** Maps each worker to the cell it currently occupies. */
    protected final Map<Worker, Cell> workerToCell;

    /** Maps each cell to the worker occupying it, if any. */
    protected final Map<Cell, Worker> cellToWorker;

    /**
     * Initializes the internal mappings for worker↔cell relationships.
     */
    public GameBoard() {
        workerToCell = new HashMap<>();
        cellToWorker = new HashMap<>();
    }

    /**
     * Checks whether the given worker is placed on this board.
     *
     * @param worker the worker to check
     * @return {@code true} if the worker exists on this board, {@code false} otherwise
     */
    public boolean hasWorker(Worker worker) {
        return workerToCell.containsKey(worker);
    }

    /**
     * Retrieves the cell in which the specified worker is located.
     *
     * @param worker the worker whose location is requested
     * @return the cell occupied by the worker, or {@code null} if not present
     */
    public Cell getLocationOf(Worker worker) {
        return workerToCell.get(worker);
    }

    /**
     * Moves a worker from its current cell to a new cell, updating both mappings.
     *
     * @param worker the worker to move
     * @param cell   the destination cell
     */
    public void updateLocation(Worker worker, Cell cell) {
        Cell oldCell = workerToCell.get(worker);
        cellToWorker.remove(oldCell);
        workerToCell.put(worker, cell);
        cellToWorker.put(cell, worker);
    }

    /**
     * Places a worker on the specified cell, adding to both mappings.
     *
     * @param worker the worker to place
     * @param cell   the cell on which to place the worker
     */
    public void addWorker(Worker worker, Cell cell) {
        workerToCell.put(worker, cell);
        cellToWorker.put(cell, worker);
    }

    /**
     * Determines if a given cell is occupied by a worker.
     *
     * @param cell the cell to check
     * @return {@code true} if occupied, {@code false} otherwise
     */
    public boolean isCellOccupied(Cell cell) {
        return cellToWorker.containsKey(cell);
    }

    /**
     * Retrieves the worker occupying a specific cell.
     *
     * @param cell the cell to query
     * @return the occupying worker, or {@code null} if the cell is empty
     */
    public Worker getWorkerAt(Cell cell) {
        return cellToWorker.get(cell);
    }

    /**
     * Checks whether this board implementation contains the given cell.
     *
     * @param cell the cell to verify
     * @return {@code true} if the cell is part of this board
     */
    public abstract boolean hasCell(Cell cell);

    /**
     * Returns all cells adjacent to the specified cell.
     *
     * @param workersCell the reference cell
     * @return a list of neighboring cells
     */
    public abstract ArrayList<Cell> getAdjacentCells(Cell workersCell);
}
