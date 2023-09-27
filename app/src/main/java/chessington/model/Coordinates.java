package chessington.model;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Coordinates {
    private final int row;
    private final int col;

    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return row == that.row &&
                col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return String.format("row %d, column %d", row, col);
    }

    public Coordinates plus(int rowDiff, int colDiff) {
        return new Coordinates(row + rowDiff, col + colDiff);
    }

    /**
     * <pre>
     *     Gets a specified range of coordinates, for example:
     *
     *     from = [0, 0]      | from  = [0, 0]
     *     to = [4, 0]        | to = [2, 2]
     *                        |
     *     returns: [         | returns [
     *      [0, 0],           |  [0, 0],
     *      [1, 0],           |  [1, 1],
     *      [2, 0],           |  [2, 2]
     *      [3, 0],           | ]
     *      [4, 0]            |
     *     ]
     * </pre>
     * @param from The starting value, this will be included in the output
     * @param to The end value, this will be included in the output
     * @return A list of coordinates containing the intermediate values between the two ranges
     */
    public static List<Coordinates> getRange(Coordinates from, Coordinates to) {
        List<Coordinates> coords = new ArrayList<>();

        if (to.getRow() == to.getCol()) {
            for (int i = 0; i < to.getRow(); i++) {
                coords.add(from.plus(i, i));
            }
        } else if (to.getRow() > to.getCol()) {
            for (int i = 0; i < to.getRow(); i++) {
                coords.add(from.plus(i, 0));
            }
        } else {
            for (int i = 0; i < to.getCol(); i++) {
                coords.add(from.plus(0, i));
            }
        }

        return coords;
    }
}
