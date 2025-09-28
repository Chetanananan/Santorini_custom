// File: engine/Action/MoveAction.java
package engine.action;

import engine.actors.Worker;
import engine.positions.Cell;
import engine.positions.GameBoard;
import engine.positions.GameEngine;
import engine.structures.Structure;
import engine.structures.StructureType;
import engine.displays.BoardUI;

/**
 * Represents a move action that a Worker can perform on the GameBoard.
 * This action will relocate the worker from its current cell to the target cell,
 * if the move is valid according to game rules.
 */
public class MoveAction extends Action {
    private final Cell fromCell;
    private final Cell toCell;

    public MoveAction(GameBoard board, BoardUI boardUI, Worker worker, Cell toCell) {
        super(board, boardUI, worker);
        this.fromCell = board.getLocationOf(worker);
        this.toCell   = toCell;
    }

    @Override
    public boolean execute() {
        if (!isValidMove()) {
            return false;
        }
        board.updateLocation(worker, toCell);
        boardUI.updateBoard();
        GameEngine.getInstance().checkWinCondition(worker);
        return true;
    }

    /**
     * Determines whether moving from {@code fromCell} to {@code toCell} is allowed:
     *  - Destination must exist and be unoccupied
     *  - Destination must not contain a wall
     *  - Destination must be adjacent
     *  - Can only move up at most one level
     */
    private boolean isValidMove() {
        // 1) In‐bounds
        if (!board.hasCell(toCell)) return false;

        // 2) Not already occupied by another worker
        if (board.isCellOccupied(toCell)) return false;

        // 3) Cannot move onto a wall
        Structure toStructure = toCell.getStructure();
        if (toStructure != null && toStructure.getType() == StructureType.WALL) {
            return false;
        }

        // 4) Must be adjacent
        if (!fromCell.isAdjacentTo(toCell)) return false;

        // 5) Height difference ≤ 1
        Structure fromStructure = fromCell.getStructure();
        int fromLevel = (fromStructure != null) ? fromStructure.getLevel() : 0;
        int toLevel   = (toStructure   != null) ? toStructure.getLevel()   : 0;
        return (toLevel - fromLevel) <= 1;
    }
}
