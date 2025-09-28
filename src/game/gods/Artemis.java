package game.gods;

import engine.action.MoveAction;
import engine.actors.Worker;
import engine.positions.GameBoard;
import engine.positions.Cell;
import engine.positions.GameEngine;
import engine.displays.BoardUI;


/**
 * Represents the Artemis god power, allowing the player to move a second time
 * if the second move is not to the cell the worker started from.
 * Implements the {@link Skippable} interface to allow skipping the second move.
 */
public class Artemis extends God implements Skippable {
    /** The cell the worker was originally on before any move. */
    private Cell lastCell;

    /** Tracks whether the first move has already been made. */
    private boolean movedState = false;

    /**
     * Constructs the Artemis god card with a name and description.
     *
     * @param name        the name of the god
     * @param description a description of the god's power
     */
    public Artemis(String name, String description) {
        super(name, description);
    }

    /**
     * Handles Artemis's move logic:
     * - On first move: store last cell and allow second move.
     * - On second move: disallow moving back to the original cell.
     * Transitions to build phase after second move or skip.
     *
     * @param cell   the cell to move to
     * @param worker the worker making the move
     */
    @Override
    public void handleMove(Cell cell, Worker worker) {
        GameEngine engine = GameEngine.getInstance();
        BoardUI boardUI = engine.getBoardUI();
        GameBoard board = engine.getBoard();

        MoveAction move = new MoveAction(board, boardUI, worker, cell);

        if (movedState) {
            if (!cell.equals(lastCell)) {
                if (move.execute()) {
                    super.currentPhase = TurnPhase.BUILD;
                    boardUI.setStatus(engine.getCurrentPlayer().getNAME()
                            + "'s Turn - build Phase (" + engine.getCurrentPlayer().getId()
                            + ") " + engine.getCurrentPlayer().getGod().getName());
                    movedState = false;
                    skipButton.setVisible(false);
                } else {
                    boardUI.setError("Invalid Move!!");
                }
            } else {
                boardUI.setError("You can't move back to the starting cell!");
            }
        } else {
            lastCell = board.getLocationOf(worker);
            if (move.execute()) {
                boardUI.setStatus(engine.getCurrentPlayer().getNAME()
                        + "'s Turn - move Phase (" + engine.getCurrentPlayer().getId()
                        + ") " + engine.getCurrentPlayer().getGod().getName());
                movedState = true;
                handleSkip(boardUI, engine, "Skip second move");
            }
        }
    }

    /**
     * Invoked when the player chooses to skip Artemis's optional second move.
     * Proceeds to the build phase.
     *
     * @param boardUI the UI to update
     * @param engine  the game engine managing state
     */
    @Override
    public void skipLogic(BoardUI boardUI, GameEngine engine) {
        movedState = false;
        super.currentPhase = TurnPhase.BUILD;
        boardUI.setStatus(engine.getCurrentPlayer().getNAME()
                + "'s Turn - build Phase (" + engine.getCurrentPlayer().getId()
                + ") " + engine.getCurrentPlayer().getGod().getName());
    }
}