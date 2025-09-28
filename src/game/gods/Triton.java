// File: Game/Gods/Triton.java
package game.gods;

import engine.action.MoveAction;
import engine.actors.Worker;
import engine.positions.Cell;
import engine.positions.GameEngine;
import engine.positions.SquareBoard;
import engine.displays.BoardUI;

/**
 * Represents the Triton god power:
 * "Each time your worker moves into a perimeter space, it may immediately move again."
 */
public class Triton extends God implements Skippable {

    public Triton(String name, String description) {
        super(name, description);
        // skipButton might be null at construction time, so check first:
        if (skipButton != null) {
            skipButton.setVisible(false);
        }
    }

    @Override
    public void handleMove(Cell targetCell, Worker worker) {
        GameEngine engine = GameEngine.getInstance();
        BoardUI boardUI = engine.getBoardUI();
        SquareBoard board = engine.getBoard();

        MoveAction move = new MoveAction(board, boardUI, worker, targetCell);
        if (!move.execute()) {
            boardUI.setError("Invalid move!");
            // If skipButton has been created already, hide it.
            if (skipButton != null) {
                skipButton.setVisible(false);
            }
            return;
        }

        int row = targetCell.getRow();
        int col = targetCell.getCol();
        int size = board.getDimension();  // assumes SquareBoard.getDimension() returns 5

        boolean onPerimeter = (row == 0 || col == 0 || row == size - 1 || col == size - 1);
        if (onPerimeter) {
            // Landed on perimeter → allow an extra move
            currentPhase = TurnPhase.MOVE;
            boardUI.updateBoard();
            boardUI.setStatus(engine.getCurrentPlayer().getNAME()
                    + "'s Turn - Triton may move again");

            // Ensure skipButton exists before showing it
            if (skipButton != null) {
                skipButton.setVisible(true);
            }
            handleSkip(boardUI, engine, "Skip extra move");

        } else {
            // Landed off-perimeter → no extra move; hide skip if it exists, go to BUILD
            if (skipButton != null) {
                skipButton.setVisible(false);
            }
            currentPhase = TurnPhase.BUILD;
            boardUI.updateBoard();
            boardUI.setStatus(engine.getCurrentPlayer().getNAME()
                    + "'s Turn - Build Phase (" + engine.getCurrentPlayer().getId()
                    + ") " + engine.getCurrentPlayer().getGod().getName());
        }
    }

    @Override
    public void skipLogic(BoardUI boardUI, GameEngine engine) {
        // Hide skip button if it exists
        if (skipButton != null) {
            skipButton.setVisible(false);
        }
        // Transition directly to BUILD phase
        super.currentPhase = TurnPhase.BUILD;
        boardUI.setStatus(engine.getCurrentPlayer().getNAME()
                + "'s Turn - Build Phase (" + engine.getCurrentPlayer().getId()
                + ") " + engine.getCurrentPlayer().getGod().getName());
    }
}