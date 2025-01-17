package chessington.model.pieces;

import chessington.model.Board;
import chessington.model.Coordinates;
import chessington.model.Move;
import chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractPiece {
    public King(PlayerColour colour) {
        super(PieceType.KING, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        final List<Move> validMoves = new ArrayList<>();
        final int direction = this.colour.DIRECTION_SCALAR;
        final List<Coordinates> surroundings = List.of(
                // ups and downs
                from.plus(direction, 0),
                from.plus(-direction, 0),
                // sideways
                from.plus(0, direction),
                from.plus(0, -direction),
                // diagonals
                from.plus(direction, direction),
                from.plus(direction, -direction),
                from.plus(-direction, direction),
                from.plus(-direction, -direction)
        );

        for (Coordinates coords : surroundings) {
            // If the square is empty
            // OR if the square has an enemy in
            if (isWithinBoardBoundaries(coords)) {
                if (board.get(coords) == null ||
                        board.get(coords).getColour() != this.colour) {
                    validMoves.add(new Move(from, coords));
                }
            }
        }

        return validMoves;
    }

    private PlayerColour invertColour(PlayerColour colour) {
        if (colour == PlayerColour.BLACK) {
            return PlayerColour.WHITE;
        }

        return PlayerColour.BLACK;
    }
}