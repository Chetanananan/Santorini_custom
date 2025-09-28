package game.gods;

import engine.action.BuildAction;
import engine.action.MoveAction;
import engine.actors.Worker;
import engine.positions.GameBoard;
import engine.positions.Cell;
import engine.positions.GameEngine;
import engine.displays.BoardUI;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract base class for all Gods in the game.
 * Defines default move/build behavior and skip handling.
 * Each god may override methods to enable special powers.
 */
public abstract class God {
    /** Name of the god (e.g., "Artemis", "Demeter"). */
    protected String name;

    /** Description of the god's power. */
    protected String description;

    /** The current phase of the god's turn (MOVE or BUILD). */
    protected TurnPhase currentPhase;

    /** Skip button shown for optional second actions (if applicable). */
    protected JButton skipButton;

    /**
     * Constructs a God with the given name and description.
     *
     * @param name        the god's name
     * @param description description of the god's power
     */
    public God(String name, String description) {
        this.name = name;
        this.description = description;
        this.currentPhase = TurnPhase.MOVE;
    }

    /**
     * Handles a turn by delegating to either move or build phase logic.
     *
     * @param cell   the clicked cell
     * @param worker the worker performing the action
     */
    public void takeTurn(Cell cell, Worker worker) {
        if (currentPhase == TurnPhase.MOVE) {
            handleMove(cell, worker);
        } else {
            handleBuild(cell, worker);
        }
    }

    /**
     * Default move logic for gods without custom powers.
     * Transitions to the build phase on success.
     *
     * @param cell   the destination cell
     * @param worker the worker to move
     */
    public void handleMove(Cell cell, Worker worker) {
        GameEngine engine = GameEngine.getInstance();
        BoardUI boardUI = engine.getBoardUI();

        MoveAction move = new MoveAction(engine.getBoard(), boardUI, worker, cell);
        if (move.execute()) {
            currentPhase = TurnPhase.BUILD;
            boardUI.setStatus(engine.getCurrentPlayer().getNAME() + "'s Turn - Build Phase");
        } else {
            boardUI.setError("Invalid Move!!");
        }
    }

    /**
     * Default build logic for gods without special powers.
     * Resets to MOVE phase and ends turn on success.
     *
     * @param cell   the target cell to build on
     * @param worker the worker performing the build
     */
    public void handleBuild(Cell cell, Worker worker) {
        GameEngine engine = GameEngine.getInstance();
        BoardUI boardUI = engine.getBoardUI();
        GameBoard board = engine.getBoard();

        BuildAction build = new BuildAction(board, boardUI, worker, cell);
        if (build.execute()) {
            currentPhase = TurnPhase.MOVE;
            boardUI.updateBoard();
            engine.setTurnProgress(false);
        } else {
            boardUI.setError("Invalid Build!!");
        }
    }

    /**
     * Creates and displays a skip button that allows a player to skip
     * an optional second move or build, depending on the god's power.
     *
     * @param boardUI the UI component
     * @param engine  the current game engine instance
     * @param skipText the text to show on the skip button
     */
    public void handleSkip(BoardUI boardUI, GameEngine engine, String skipText) {
        if (skipButton == null) {
            skipButton = new JButton(skipText);
            skipButton.setFocusable(false);

            skipButton.addActionListener(e -> {
                skipButton.setVisible(false);
                skipLogic(boardUI, engine);
            });

            Container contentPane = boardUI.getContentPane();
            JPanel bottomPanel = (JPanel) ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
            bottomPanel.add(skipButton);
        }

        skipButton.setVisible(true);
        skipButton.revalidate();
        skipButton.repaint();
    }

    /**
     * Default skip logic (can be overridden).
     * Ends the turn and hides the skip button.
     *
     * @param boardUI the UI component
     * @param engine  the game engine
     */
    public void skipLogic(BoardUI boardUI, GameEngine engine) {
        skipButton.setVisible(false);
        boardUI.updateBoard();
        engine.setTurnProgress(false);
        engine.switchTurn();
    }

    /**
     * Returns the current turn phase of this god.
     *
     * @return MOVE or BUILD
     */
    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Returns the name of the god.
     *
     * @return the god's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the god's power.
     *
     * @return the god's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string representation (the god's name).
     *
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
