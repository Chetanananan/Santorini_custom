package game.gods;

import engine.action.BuildAction;
import engine.actors.Worker;
import engine.positions.GameBoard;
import engine.positions.Cell;
import engine.positions.GameEngine;
import engine.displays.BoardUI;

/**
 * Represents the Demeter god power, which allows a player to build a second time
 * on a different cell during their build phase. Implements {@link Skippable}
 * to let the player skip the second build.
 */
public class Demeter extends God implements Skippable {

    /** Flag indicating whether the first build has occurred. */
    private boolean secondBuildCheck = false;

    /** The cell used for the first build (to prevent re-use). */
    private Cell firstBuildCell;

    /**
     * Constructs the Demeter god with its name and description.
     *
     * @param name        the name of the god
     * @param description a description of the god's power
     */
    public Demeter(String name, String description) {
        super(name, description);
    }

    /**
     * Routes the turn based on current phase: move or build.
     *
     * @param cell   the selected cell
     * @param worker the worker performing the action
     */
    @Override
    public void takeTurn(Cell cell, Worker worker) {
        if (currentPhase == TurnPhase.MOVE) {
            handleMove(cell, worker);
        } else {
            handleBuild(cell, worker);
        }
    }

    /**
     * Handles Demeter's custom build logic:
     * - First build: proceed normally and prompt optional second build.
     * - Second build: must be on a different cell from the first.
     *
     * @param cell   the target cell to build on
     * @param worker the worker performing the build
     */
    @Override
    public void handleBuild(Cell cell, Worker worker) {
        GameEngine engine = GameEngine.getInstance();
        BoardUI boardUI = engine.getBoardUI();
        GameBoard board = engine.getBoard();

        if (!secondBuildCheck) {
            // First build
            BuildAction build = new BuildAction(board, boardUI, worker, cell);
            if (build.execute()) {
                firstBuildCell = cell;
                secondBuildCheck = true;
                boardUI.updateBoard();
                boardUI.setStatus(engine.getCurrentPlayer().getNAME()
                        + "'s Turn - Second Build (Optional) (" + engine.getCurrentPlayer().getId()
                        + ") " + engine.getCurrentPlayer().getGod().getName());
                handleSkip(boardUI, engine, "Skip second build");
            } else {
                boardUI.setError("Invalid Build!!");
            }
        } else {
            // Second build
            if (cell.equals(firstBuildCell)) {
                boardUI.setError("Cannot build on the same cell again!");
                return;
            }

            BuildAction secondBuild = new BuildAction(board, boardUI, worker, cell);
            if (secondBuild.execute()) {
                boardUI.updateBoard();
                resetBuildState();
                engine.setTurnProgress(false);
                skipButton.setVisible(false);
            } else {
                boardUI.setError("Invalid Build!!");
            }
        }
    }

    /**
     * Resets build-related flags and phase state for the next turn.
     */
    private void resetBuildState() {
        currentPhase = TurnPhase.MOVE;
        secondBuildCheck = false;
        firstBuildCell = null;
    }

    /**
     * Invoked when the player chooses to skip the second build.
     * Ends the turn and resets Demeter-specific state.
     *
     * @param boardUI the UI to update
     * @param engine  the game engine managing the game state
     */
    @Override
    public void skipLogic(BoardUI boardUI, GameEngine engine) {
        boardUI.updateBoard();
        resetBuildState();
        engine.setTurnProgress(false);
        engine.switchTurn();
    }
}
