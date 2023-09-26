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
        final int direction = this.colour.DIRECTION_SCALAR;
        Coordinates oneStep = from.plus(direction, 0);
        Coordinates twoSteps = from.plus(direction * 2, 0);
        Coordinates rightDiagonal = from.plus(direction, 1);
        Coordinates leftDiagonal = from.plus(direction, -1);

        if (isWithinBoardBoundaries(oneStep) && isWithinBoardBoundaries(twoSteps)) {
            if (!isEnemyPieceInSquare(oneStep, board, this.colour)) {
                validMoves.add(new Move(from, oneStep));
            }
            if (isWithinBoardBoundaries(leftDiagonal) && isEnemyPieceInSquare(leftDiagonal, board, this.colour)) {
                validMoves.add(new Move(from, leftDiagonal));
            }
            if (isWithinBoardBoundaries(rightDiagonal) && isEnemyPieceInSquare(rightDiagonal, board, this.colour)) {
                validMoves.add(new Move(from, rightDiagonal));
            }

            // First turn movement allows two squares, so check two squares in front
            if (!isEnemyPieceInSquare(oneStep, board, this.colour) && !isEnemyPieceInSquare(twoSteps, board, this.colour)) {
                if (isFirstMove(from, this.colour)) {
                    validMoves.add(new Move(from, twoSteps));
                }
            }
        }

        return validMoves;
    }
}
