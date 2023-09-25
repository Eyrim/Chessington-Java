package chessington.model.pieces;

import chessington.model.Board;
import chessington.model.Coordinates;
import chessington.model.Move;
import chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> validMoves = new ArrayList<>();
        final int direction = this.colour == PlayerColour.WHITE ? -1 : 1;
        Coordinates oneStep = from.plus(direction, 0);
        Coordinates twoSteps = from.plus(direction * 2, 0);
        Coordinates rightDiagonal = from.plus(direction, 1);
        Coordinates leftDiagonal = from.plus(direction, -1);

        // A valid move for a pawn is move up one space OR move down one space (black)
        // Or two spaces if first move
        if (isWithinBoardBoundaries(oneStep) && isWithinBoardBoundaries(twoSteps)) {
            if (!isPieceInSquare(oneStep, board, this.colour)) {
                validMoves.add(new Move(from, oneStep));
            }
            if (isWithinBoardBoundaries(leftDiagonal) && isPieceInSquare(leftDiagonal, board, this.colour)) {
                validMoves.add(new Move(from, leftDiagonal));
            }
            if (isWithinBoardBoundaries(rightDiagonal) && isPieceInSquare(rightDiagonal, board, this.colour)) {
                validMoves.add(new Move(from, rightDiagonal));
            }

            // First turn movement allows two squares, so check two squares in front
            if (!isPieceInSquare(oneStep, board, this.colour) && !isPieceInSquare(twoSteps, board, this.colour)) {
                if (isFirstMove(from)) {
                    validMoves.add(new Move(from, twoSteps));
                }
            }
        }

        return validMoves;
    }

    private boolean isWithinBoardBoundaries(Coordinates square) {
        final int TOP_OF_BOARD = 0; // Row
        final int BOTTOM_OF_BOARD = 7; // Row
        final int LEFT_OF_BOARD = 0; // Col
        final int RIGHT_OF_BOARD = 7; // Col

        if ((square.getRow() >= TOP_OF_BOARD) && (square.getRow() <= BOTTOM_OF_BOARD) &&
                ((square.getCol() >= LEFT_OF_BOARD) && (square.getCol() <= RIGHT_OF_BOARD))) {
            return true;
        }

        return false;
    }

    private boolean isPieceInSquare(Coordinates square, Board board, PlayerColour colour) {
        Piece inSquare = board.get(square);

        if (inSquare != null && inSquare.getColour() != colour) {
            return true;
        }

        return false;
    }

    private boolean isFirstMove(Coordinates from) {
        final int WHITE_STARTING_ROW = 6;
        final int BLACK_STARTING_ROW = 1;

        if ((from.getRow() == WHITE_STARTING_ROW && this.colour == PlayerColour.WHITE) ||
                (from.getRow() == BLACK_STARTING_ROW && this.colour == PlayerColour.BLACK)) {
            return true;
        }

        return false;
    }
}
