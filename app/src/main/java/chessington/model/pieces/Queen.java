package chessington.model.pieces;

import chessington.model.Board;
import chessington.model.Coordinates;
import chessington.model.Move;
import chessington.model.PlayerColour;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {
    public Queen(PlayerColour colour) {
        super(PieceType.QUEEN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();
        final int direction = this.colour.DIRECTION_SCALAR;
        final List<Coordinates> surroundings = generateSurroundings(from, board);

        // Move up and down
        // Move left and right
        // Move diagonally
        // Not move off the board
        // Don't move through other pieces
        // Capture enemy pieces in our trajectory


        for (Coordinates coords : surroundings) {
            if (isWithinBoardBoundaries(coords) && (board.get(coords) == null ||
                    (board.get(coords).getColour() != this.colour))) {
                validMoves.add(new Move(from, coords));
            }
        }

        return validMoves;
    }

    // TODO: 26/09/2023 THIS METHOD GENERATES SOME OF THE SAME COORDINATES FOR DIFFERENT DIRECTIONS, SLOWER THAN SKIPPING
    private List<Coordinates> generateSurroundings(Coordinates from, Board board) {
        List<Coordinates> surroundings = new ArrayList<>();
        Coordinates current;
        Piece pieceInSquare;

        // Go left (walk column up to seven)
        for (int i = 7 - from.getCol(); i <= 7; i++) {
            if (i == from.getCol()) { continue; }

            current = new Coordinates(from.getRow(), i);
            pieceInSquare = getPieceInSquare(current, board);

            if (pieceInSquare != null) {
                // If there is a friendly piece in the target square, break as we cannot go further
                if (pieceInSquare.getColour() == this.colour) {
                    break;
                    // If there is an enemy piece in the target square
                    // Allow as a move to capture, but we cannot go further
                } else if (pieceInSquare.getColour() != this.colour) {
                    surroundings.add(current);
                    break;
                }
            }

            surroundings.add(current);
        }

        // Go right (walk column down to 0)
        for (int i = 7 - from.getCol(); i >= 0; i--) {
            // Don't allow the piece to move to itself
            if (i == from.getCol()) { continue; }

            current = new Coordinates(from.getRow(), i);
            pieceInSquare = getPieceInSquare(current, board);

            if (pieceInSquare != null) {
                // If there is a friendly piece in the target square, break as we cannot go further
                if (pieceInSquare.getColour() == this.colour) {
                    break;
                    // If there is an enemy piece in the target square
                    // Allow as a move to capture, but we cannot go further
                } else if (pieceInSquare.getColour() != this.colour) {
                    surroundings.add(current);
                    break;
                }
            }

            surroundings.add(current);
        }

        // Go up (walk row down to 0)
        for (int i = from.getRow(); i >= 0; i--) {
            // Don't allow the piece to move to itself
            if (i == from.getRow()) { continue; }

            current = new Coordinates(i, from.getCol());
            pieceInSquare = getPieceInSquare(current, board);

            if (pieceInSquare != null) {
                // If there is a friendly piece in the target square, break as we cannot go further
                if (pieceInSquare.getColour() == this.colour) {
                    break;
                } else if (pieceInSquare.getColour() != this.colour) {
                    surroundings.add(current);
                    break;
                }
            }

            surroundings.add(current);
        }

        // Go down (walk row up to 7)
        for (int i = from.getRow(); i <= 7; i++) {
            // Don't allow the piece to move to itself
            if (i == from.getRow()) { continue; }

            current = new Coordinates(i, from.getCol());
            pieceInSquare = getPieceInSquare(current, board);

            if (pieceInSquare != null) {
                // If there is a friendly piece in the target square, break as we cannot go further
                if (pieceInSquare.getColour() == this.colour) {
                    break;
                    // If there is an enemy piece in the target square
                    // Allow as a move to capture, but we cannot go further
                } else {
                    surroundings.add(current);
                    break;
                }
            }

            surroundings.add(current);
        }

        surroundings.addAll(generateDiagonal(from, board));

        return surroundings;
    }

    private List<Coordinates> generateDiagonal(Coordinates from, Board board) {
        ArrayList<Coordinates> coords = new ArrayList<>();

        // Up right
        coords.addAll(generateDiagonal(from, 1, 1, board));
        // Down right
        coords.addAll(generateDiagonal(from, 1, -1, board));
        // Up left
        coords.addAll(generateDiagonal(from, -1, 1, board));
        // Down left
        coords.addAll(generateDiagonal(from, -1, -1, board));

        return coords;
    }

    private List<Coordinates> generateDiagonal(Coordinates from, int rowChange, int colChange, Board board) {
        List<Coordinates> coords = new ArrayList<>();
        Coordinates currentCoords = from.plus(rowChange, colChange);
        Piece pieceInSquare;

        while (isWithinBoardBoundaries(currentCoords)) {
            pieceInSquare = getPieceInSquare(currentCoords, board);

            if (pieceInSquare != null) {
                if (pieceInSquare.getColour() == this.colour) {
                    break;
                    // If there is an enemy piece in the target square
                    // Allow as a move to capture, but we cannot go further
                } else {
                    coords.add(currentCoords);
                    break;
                }
            }

            currentCoords = currentCoords.plus(rowChange, colChange);
        }

        return coords;
    }

//    private List<Coordinates> generateDiagonalOld(Coordinates from) {
//        final List<Coordinates> coords = new ArrayList<>();
//        int newRow = from.getRow();
//        int newCol = from.getCol();
//
//        while (
//                (newRow != 0 && newCol != 0) ||
//                        (newRow != 7 && newCol != 7)
//        ) {
//            // Up and left (-1 row +1 col)
//            newRow -= 1;
//            newCol += 1;
//
//            coords.add(new Coordinates(newRow, newCol));
//        }
//
//        newRow = from.getRow();
//        newCol = from.getCol();
//
//        while (
//                (newRow != 0 && newCol != 0) ||
//                        (newRow != 7 && newCol != 7)
//        ) {
//            // Down and left (-1 row -1 col)
//            newRow -= 1;
//            newCol -= 1;
//
//            coords.add(new Coordinates(newRow, newCol));
//        }
//
//        newRow = from.getRow();
//        newCol = from.getCol();
//
//        while (
//                (newRow != 0 && newCol != 0) ||
//                        (newRow != 7 && newCol != 7)
//        ) {
//            // Up and right (+1 row +1 col)
//            newRow += 1;
//            newCol += 1;
//
//            coords.add(new Coordinates(newRow, newCol));
//        }
//
//        newRow = from.getRow();
//        newCol = from.getCol();
//
//        while (
//                (newRow != 0 && newCol != 0) ||
//                        (newRow != 7 && newCol != 7)
//        ) {
//            // Down and right (+1 row -1 col)
//            newRow += 1;
//            newCol -= 1;
//
//            coords.add(new Coordinates(newRow, newCol));
//        }
//
//        return coords;
//    }
}
