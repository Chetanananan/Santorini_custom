// === Overwrite the following files to implement timer functionality ===

// File: engine/Positions/GameEngine.java
package engine.positions;

import engine.actors.Player;
import engine.actors.Worker;
import engine.structures.Structure;
import engine.displays.BoardUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core engine that manages game flow, turn progression, timers,
 * win conditions, and interactions between model and UI.
 */
public class GameEngine {
    private static GameEngine instance;
    private final SquareBoard board;
    private final List<Player> players;
    private int currentPlayerIndex;
    private final BoardUI boardUI;
    private Worker selectedWorker = null;
    private boolean turnProgress = false;

    // Timer fields
    private static final int INITIAL_TIME_MS = 5*60*1000; // 15 minutes
    private final Map<Player, Integer> timeLeft = new HashMap<>();
    private Timer timer;

    /**
     * Constructs the GameEngine with board and players, sets up UI and timers.
     */
    public GameEngine(SquareBoard board, List<Player> players) {
        this.board = board;
        this.players = players;
        this.currentPlayerIndex = 0;
        // initialize UI
        this.boardUI = new BoardUI(board);
        // initialize timers for each player
        for (Player p : players) {
            timeLeft.put(p, INITIAL_TIME_MS);
        }
    }

    /**
     * Initializes the singleton and starts the game.
     */
    public static GameEngine init(SquareBoard board, List<Player> players) {
        instance = new GameEngine(board, players);
        instance.start();
        return instance;
    }

    /**
     * Returns the singleton instance.
     */
    public static GameEngine getInstance() {
        return instance;
    }

    /**
     * Starts UI update and timer for first player.
     */
    public void start() {
        SwingUtilities.invokeLater(() -> {
            boardUI.updateBoard();
            startTimerForCurrent();
        });
    }

    /**
     * Handles a click on the board at the specified coordinates.
     */
    public void takeTurn(int row, int col) {
        boardUI.clearError();
        boardUI.setStatus(getCurrentPlayer().getNAME() + "'s Turn (" + getCurrentPlayer().getId()
                + ") " + getCurrentPlayer().getGod().getName());
        Cell clickedCell = board.getCell(row, col);

        if (!turnProgress) {
            // select worker phase
            if (!getCurrentPlayer().hasValidMoves()) {
                switchTurn();
                JOptionPane.showMessageDialog(boardUI,
                        getCurrentPlayer().getNAME() + " wins!");
                System.exit(0);
            }
            Worker w = board.getWorkerAt(clickedCell);
            if (w != null && getCurrentPlayer().getWORKERS().contains(w)) {
                selectedWorker = w;
                turnProgress = true;
                boardUI.setStatus(getCurrentPlayer().getNAME() + "'s Turn - Move Phase");
            } else if (w != null) {
                boardUI.setError("Not your worker, cannot move");
            }
        } else {
            // execute move/build
            getCurrentPlayer().takeTurn(clickedCell, selectedWorker);
            if (!turnProgress) {
                switchTurn();
            }
        }
    }

    /**
     * Checks win condition: worker reaches level 3.
     */
    public void checkWinCondition(Worker worker) {
        Structure s = board.getLocationOf(worker).getStructure();
        int lvl = (s != null) ? s.getLevel() : 0;
        if (lvl == 3) {
            JOptionPane.showMessageDialog(boardUI,
                    getCurrentPlayer().getNAME() + " wins!");
            System.exit(0);
        }
    }

    /**
     * Switches to next player, resets turn state and timer.
     */
    public void switchTurn() {
        // stop current timer
        stopTimer();
        getCurrentPlayer().setTurnProgress(false);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        turnProgress = false;
        // update UI status
        boardUI.setStatus(getCurrentPlayer().getNAME() + "'s Turn - Select Worker");
        // restart timer for new player
        startTimerForCurrent();
    }

    /**
     * Starts or resumes timer for the active player.
     */
    private void startTimerForCurrent() {
        Player current = getCurrentPlayer();
        boardUI.updateTimer(current, formatTime(timeLeft.get(current)));
        if (timer != null) timer.stop();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rem = timeLeft.get(current) - 1000;
                timeLeft.put(current, rem);
                boardUI.updateTimer(current, formatTime(rem));
                if (rem <= 0) {
                    stopTimer();
                    boardUI.showGameOver(current.getNAME() + " ran out of time and loses.");
                }
            }
        });
        timer.start();
    }

    /**
     * Stops the active timer.
     */
    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    /**
     * Formats milliseconds to MM:SS.
     */
    private String formatTime(int ms) {
        int secs = ms / 1000;
        int m = secs / 60;
        int s = secs % 60;
        return String.format("%02d:%02d", m, s);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public BoardUI getBoardUI() {
        return boardUI;
    }

    public SquareBoard getBoard() {
        return board;
    }

    public void setTurnProgress(boolean turnProgress) {
        this.turnProgress = turnProgress;
    }

    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    /**
     * Removes the current player from the game and advances
     * the turn index to the next player.
     */
    public void removeCurrentPlayer() {
        players.remove(currentPlayerIndex);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }
}