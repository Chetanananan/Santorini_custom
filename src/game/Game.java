package game;

import game.gods.Artemis;
import game.gods.Demeter;
import game.gods.Triton;
import game.gods.God;
import engine.actors.Player;
import engine.positions.SquareBoard;
import engine.actors.Worker;
import engine.positions.GameEngine;
import engine.displays.MainMenuUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Entry point for launching the Santorini game.
 * Displays the main menu and sets up a random game state with two players and gods.
 */
public class Game {

    /**
     * Launches the game UI by displaying the main menu.
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuUI::new);
    }

    /**
     * Starts the game after main menu interaction.
     * Initializes the game state.
     */
    public static void startGame() {
        setupGame();
    }

    /**
     * Sets up the board, randomly places workers, assigns gods,
     * and initializes the GameEngine singleton.
     */
    private static void setupGame() {
        Artemis artemis = new Artemis("Artemis", "Your Worker may move one\nadditional time, but not back to its initial space");
        Demeter demeter = new Demeter("Demeter", "Your Worker may build one\nadditional time, but not on the same space.");
        Triton triton = new Triton("Triton", "Each time your worker moves into\n a perimeter space, it may immediately move again. ");

        ArrayList<God> gods = new ArrayList<>();
        gods.add(artemis);
        gods.add(demeter);
        gods.add(triton);

        SquareBoard board = new SquareBoard(5);
        Random random = new Random();

        List<Worker> player1Workers = new ArrayList<>();
        List<Worker> player2Workers = new ArrayList<>();

        // Place 4 workers randomly on the board (2 for each player)
        int assigned = 0;
        while (assigned < 4) {
            int x = random.nextInt(5);
            int y = random.nextInt(5);

            if (!board.isCellOccupied(board.getCell(x, y))) {
                Worker worker;
                if (player1Workers.size() < 2) {
                    worker = new Worker("P1W", x, y);
                    player1Workers.add(worker);
                } else {
                    worker = new Worker("P2W", x, y);
                    player2Workers.add(worker);
                }

                board.addWorker(worker, board.getCell(x, y));
                assigned++;
            }
        }

        // Randomly assign gods to players
        int i1 = random.nextInt(gods.size());
        int i2;
        do {
            i2 = random.nextInt(gods.size());
        } while (i2 == i1);
        Player player1 = new Player("Robby", gods.get(i1), player1Workers, "P1");
        Player player2 = new Player("Miguel", gods.get(i2), player2Workers, "P2");
        ArrayList<Player> players = new ArrayList<>(List.of(player1, player2));

        // Initialize and start the game engine
        GameEngine.init(board, players);
        GameEngine.getInstance().start();
    }
}