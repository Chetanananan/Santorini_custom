package engine.positions;

import engine.structures.Structure;

/**
 * Represents a single cell on the game board, identified by its row and column.
 * Holds the current structure built on this cell, if any.
 */
public class Cell {
    /** The row index of this cell. */
    private int row;
    /** The column index of this cell. */
    private int col;
    /** The structure currently on this cell, or null if none. */
    private Structure structure;

    /**
     * Constructs a Cell at the given coordinates.
     *
     * @param row the row index
     * @param col the column index
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row index of this cell.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index of this cell.
     *
     * @return the column
     */
    public int getCol() {
        return col;
    }

    /**
     * Determines whether this cell is adjacent (including diagonals)
     * to another cell.
     *
     * @param otherCell the cell to compare against
     * @return true if the cells are at most one row and one column apart
     */
    public boolean isAdjacentTo(Cell otherCell) {
        return !((Math.abs(row - otherCell.getRow()) > 1)
                || (Math.abs(col - otherCell.getCol()) > 1));
    }

    /**
     * Returns the structure currently on this cell.
     *
     * @return the structure, or null if none
     */
    public Structure getStructure() {
        return structure;
    }

    /**
     * Sets or replaces the structure on this cell.
     *
     * @param structure the new structure (may be null)
     */
    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}