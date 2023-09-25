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

        // A valid move for a pawn is move up one space OR move down one space (black)
        // Or two spaces if first move
        if (isWithinBoardBoundaries(from, direction)) {
            if (!isPieceInSquare(from, direction, board)) {
                validMoves.add(new Move(from, oneStep));
            }

            // First turn movement allows two squares, so check two squares in front
            if (!isPieceInSquare(from, direction * 2, board)) {
                if (isFirstMove(from)) {
                    validMoves.add(new Move(from, twoSteps));
                }
            }
        }

        return validMoves;
    }

    private boolean isWithinBoardBoundaries(Coordinates from, int direction) {
        final int TOP_OF_BOARD = 0;
        final int BOTTOM_OF_BOARD = 7;

        if ((from.getRow() + direction >= TOP_OF_BOARD) &&
                (from.getRow() + direction <= BOTTOM_OF_BOARD)) {
            return true;
        }

        return false;
    }

    private boolean isPieceInSquare(Coordinates from, int direction, Board board) {
        if (board.get(from.plus(direction, 0)) != null) {
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
