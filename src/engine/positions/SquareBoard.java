// File: src/engine/Positions/SquareBoard.java
package engine.positions;

import java.util.ArrayList;

/**
 * A concrete square-grid implementation of {@link GameBoard}.
 * <p>
 * Initializes a two-dimensional array of {@link Cell cells} of the given dimension,
 * and provides methods to retrieve a cell by coordinates, check cell existence,
 * list all adjacent neighbors of a cell, and build/break walls.
 * </p>
 */
public class SquareBoard extends GameBoard {
    /** Underlying 2D array representing the board cells. */
    private final Cell[][] grid;

    /**
     * Constructs an n×n SquareBoard, creating a Cell for each coordinate pair.
     *
     * @param dimension the number of rows and columns
     */
    public SquareBoard(int dimension) {
        super();
        grid = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    /** Returns the Cell at (row,col). */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    @Override
    public boolean hasCell(Cell cell) {
        int r = cell.getRow(), c = cell.getCol();
        return r >= 0 && r < grid.length && c >= 0 && c < grid.length;
    }

    @Override
    public ArrayList<Cell> getAdjacentCells(Cell workersCell) {
        ArrayList<Cell> adjacent = new ArrayList<>();
        int row = workersCell.getRow(), col = workersCell.getCol();

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = row + dr, nc = col + dc;
                if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid.length) {
                    adjacent.add(grid[nr][nc]);
                }
            }
        }
        return adjacent;
    }
    public int getDimension() {
        return grid.length;
    }
}
