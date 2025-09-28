package engine.action;

import engine.actors.Worker;
import engine.positions.GameBoard;
import engine.displays.BoardUI;

/**
 * Abstract base class for actions that a Worker can perform on the GameBoard.
 * Subclasses must implement the {@link #execute()} method to define action behavior.
 */
public abstract class Action {
    /**
     * The game board on which this action operates.
     */
    protected final GameBoard board;

    /**
     * The UI component responsible for rendering updates to the board.
     */
    protected final BoardUI boardUI;

    /**
     * The worker performing this action.
     */
    protected final Worker worker;

    /**
     * Constructs an Action.
     *
     * @param board   the game board instance
     * @param boardUI the UI for drawing board changes
     * @param worker  the worker who will perform the action
     */
    public Action(GameBoard board, BoardUI boardUI, Worker worker){
        this.board = board;
        this.boardUI = boardUI;
        this.worker = worker;
    };

    /**
     * Executes this action’s logic.
     *
     * @return {@code true} if the action succeeded, {@code false} otherwise
     */
    public abstract boolean execute();
}