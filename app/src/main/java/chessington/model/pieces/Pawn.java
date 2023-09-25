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
        final int WHITE_STARTING_ROW = 6;
        final int BLACK_STARTING_ROW = 1;
        final int TOP_OF_BOARD = 0;
        final int BOTTOM_OF_BOARD = 7;
        int bias = this.colour == PlayerColour.WHITE ? -1 : 1;

        // A valid move for a pawn is move up one space OR move down one space (black)
        // Or two spaces if first move
        if ((from.getRow() + bias >= TOP_OF_BOARD) &&
                (from.getRow() + bias <= BOTTOM_OF_BOARD)) {
            if (board.get(from.plus(bias, 0)) == null) {
                    validMoves.add(
                            new Move(
                                    from,
                                    new Coordinates(
                                            from.getRow() + bias,
                                            from.getCol()
                                    )
                            )
                    );
            }


            // First turn movement allows two squares, so check two squares in front
            if (board.get(from.plus((bias * 2), 0)) == null) {
                if ((from.getRow() == WHITE_STARTING_ROW && this.colour == PlayerColour.WHITE) ||
                        (from.getRow() == BLACK_STARTING_ROW && this.colour == PlayerColour.BLACK)) {
                    validMoves.add(
                            new Move(
                                    from,
                                    new Coordinates(
                                            from.getRow() + (bias * 2),
                                            from.getCol()
                                    )
                            )
                    );
                }
            }
        }
//        if (this.colour == PlayerColour.WHITE) {
//                if (board.get(from.plus(-1, 0)) == null) {
//                    coords = new Coordinates(from.getRow() - 1, from.getCol());
//                    if (!checkIfMoveWillLeaveBoard(coords)) {
//                        validMoves.add(
//                                new Move(
//                                        from,
//                                        coords
//                                )
//                        );
//                    }
//
//                    if (from.getRow() == WHITE_STARTING_ROW) {
//                        coords = new Coordinates(from.getRow() - 2, from.getCol());
//                        if (!checkIfMoveWillLeaveBoard(coords)) {
//                            validMoves.add(
//                                    new Move(
//                                            from,
//                                            coords
//                                    )
//                            );
//                        }
//                    }
//                }
//        } else {
//            if (board.get(from.plus(1, 0)) == null) {
//                validMoves.add(
//                        new Move(
//                                from,
//                                new Coordinates(
//                                        from.getRow() + 1,
//                                        from.getCol())
//                        )
//                );
//
//                if (from.getRow() == BLACK_STARTING_ROW) {
//                    validMoves.add(
//                            new Move(
//                                    from,
//                                    new Coordinates(
//                                            from.getRow() + 2,
//                                            from.getCol())
//                            )
//                    );
//                }
//            }
//        }

        return validMoves;
    }

    private boolean checkIfMoveWillLeaveBoard(Coordinates target) {
        // Geographical not index based
        final int TOP_OF_BOARD = 0;
        final int BOTTOM_OF_BOARD = 7;

        return target.getRow() <= TOP_OF_BOARD || target.getRow() >= BOTTOM_OF_BOARD;
    }
}
