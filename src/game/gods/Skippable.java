package game.gods;

import engine.positions.GameEngine;
import engine.displays.BoardUI;

/**
 * Interface for gods that allow the player to skip an optional second action,
 * such as a second move (e.g., Artemis) or a second build (e.g., Demeter).
 */
public interface Skippable {
    /**
     * Defines the logic to execute when a player chooses to skip
     * their optional second action.
     *
     * @param boardUI the UI component for updates
     * @param engine  the game engine managing state
     */
    void skipLogic(BoardUI boardUI, GameEngine engine);
}