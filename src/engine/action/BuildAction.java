// File: engine/Action/BuildAction.java
package engine.action;

import engine.actors.Worker;
import engine.positions.Cell;
import engine.positions.GameBoard;
import engine.structures.Structure;
import engine.structures.StructureType;
import engine.structures.Wall;
import engine.displays.BoardUI;

public class BuildAction extends Action {
    private final Cell buildCell;
    private final GameBoard board;
    private final BoardUI   boardUI;
    private final Worker    worker;

    /**
     * Note: constructor signature unchanged.
     */
    public BuildAction(GameBoard board,
                       BoardUI boardUI,
                       Worker worker,
                       Cell buildCell) {
        super(board, boardUI, worker);  // make sure your Action base class takes (board, boardUI, worker)
        this.board     = board;
        this.boardUI   = boardUI;
        this.worker    = worker;
        this.buildCell = buildCell;
    }

    @Override
    public boolean execute() {
        if (!isValidBuild()) {
            boardUI.setError("Cannot build here!");
            return false;
        }

        if (boardUI.isBreakMode()) {
            return new BreakAction(board, boardUI, worker, buildCell).execute();
        }
        else if (boardUI.isWallMode()) {
            Structure s = buildCell.getStructure();

            if (!(s instanceof Wall)) {
                // phase 1: place a brand-new wall at level 1
                Wall w = new Wall();
                w.buildPhase();             // bump from level-0 (or constructor default) to level-1
                buildCell.setStructure(w);  // ← set on the real cell!
            }
            else {
                // phase 2+: advance the existing wall
                ((Wall)s).buildPhase();
            }

            boardUI.updateBoard();
            return true;
        }
        else {
            // standard tower / dome
            Structure prev = buildCell.getStructure();
            buildCell.setStructure(Structure.getNextStructure(prev));
            boardUI.updateBoard();
            return true;
        }
    }

    private boolean isValidBuild() {
        // basic checks
        if (buildCell == null || !board.hasCell(buildCell))       return false;
        if (board.isCellOccupied(buildCell))                      return false;
        Cell from = board.getLocationOf(worker);
        if (from == null || !from.isAdjacentTo(buildCell))        return false;

        // then structure‐specific rules
        Structure curr = buildCell.getStructure();
        if (boardUI.isBreakMode()) {
            // valid only if there's a wall to break
            return curr instanceof Wall;

        } else if (boardUI.isWallMode()) {
            // build wall only if empty or partial
            return curr == null || (curr instanceof Wall && ((Wall)curr).getBuildLevel() < 2);
        } else {
            // your existing: empty, or not yet a dome & below max height
            return curr == null || (curr.getType() != StructureType.DOME && curr.getLevel() < 4);
        }
    }
}
