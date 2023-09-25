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
        int bias = this.colour == PlayerColour.WHITE ? -1 : 1;

        // A valid move for a pawn is move up one space OR move down one space (black)
            // This doesn't take into account:
                // Taking
                // First move = 2 space allowance
                // Will allow the piece to go off the board
        validMoves.add(
                new Move(
                        from,
                        new Coordinates(
                                from.getRow() + bias,
                                from.getCol())
                )
        );

        return validMoves;
    }
}
