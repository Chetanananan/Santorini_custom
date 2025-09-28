// File: engine/Action/BreakAction.java
package engine.action;

import engine.actors.Worker;
import engine.positions.Cell;
import engine.positions.GameBoard;
import engine.structures.Structure;
import engine.structures.Wall;
import engine.displays.BoardUI;

/**
 * Represents the action of breaking a two-phase wall entirely within the Action class,
 * keeping all wall-breaking logic here (SRP).
 */
public class BreakAction extends Action {
    /** The cell containing the wall to break. */
    private final Cell breakCell;

    /**
     * @param board      the game board instance
     * @param boardUI    the UI component for rendering updates
     * @param worker     the worker performing the break
     * @param breakCell  the target cell containing a Wall
     */
    public BreakAction(GameBoard board, BoardUI boardUI, Worker worker, Cell breakCell) {
        super(board, boardUI, worker);
        this.breakCell = breakCell;
    }

    /**
     * Executes the break action:
     * 1) Validates via {@link #isValid()}
     * 2) Lowers the wall’s build level by one
     * 3) Removes the structure if it reaches level 0
     * 4) Refreshes the UI
     *
     * @return true if the break succeeded; false otherwise
     */
    @Override
    public boolean execute() {
        if (!isValid()) {
            boardUI.setError("Cannot break here!");
            return false;
        }

        // All logic in this class—no board methods called
        Structure s = breakCell.getStructure();
        Wall w = (Wall) s;
        w.breakPhase();

        if (w.getBuildLevel() == 0) {
            // fully broken → clear the cell
            breakCell.setStructure(null);
        }

        boardUI.updateBoard();
        return true;
    }

    /**
     * Validates whether the worker can break the wall:
     *  - Cell exists on board
     *  - Cell’s structure is a Wall
     *  - Worker is adjacent to that cell
     */
    public boolean isValid() {
        // 1) Cell must be on the board
        if (breakCell == null || !board.hasCell(breakCell)) {
            return false;
        }

        // 2) Must actually have a Wall
        Structure s = breakCell.getStructure();
        if (!(s instanceof Wall)) {
            return false;
        }

        // 3) Worker must be adjacent
        Cell from = board.getLocationOf(worker);
        if (from == null || !from.isAdjacentTo(breakCell)) {
            return false;
        }

        return true;
    }
}
