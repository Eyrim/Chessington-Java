package chessington.model.pieces;

import chessington.model.Board;
import chessington.model.Coordinates;
import chessington.model.Move;
import chessington.model.PlayerColour;

import java.util.List;

public interface Piece {
    enum PieceType {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
    }

    PieceType getType();
    PlayerColour getColour();

    List<Move> getAllowedMoves(Coordinates from, Board board);

    public default boolean isWithinBoardBoundaries(Coordinates square) {
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

    public default boolean isPieceInSquare(Coordinates square, Board board, PlayerColour colour) {
        Piece inSquare = board.get(square);

        if (inSquare != null && inSquare.getColour() != colour) {
            return true;
        }

        return false;
    }

    public default boolean isFirstMove(Coordinates from, PlayerColour colour) {
        final int WHITE_STARTING_ROW = 6;
        final int BLACK_STARTING_ROW = 1;

        if ((from.getRow() == WHITE_STARTING_ROW && colour == PlayerColour.WHITE) ||
                (from.getRow() == BLACK_STARTING_ROW && colour == PlayerColour.BLACK)) {
            return true;
        }

        return false;
    }
}
