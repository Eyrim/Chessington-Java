package chessington.model.pieces;

import chessington.model.Board;
import chessington.model.Coordinates;
import chessington.model.PlayerColour;
import org.junit.jupiter.api.Test;

import static chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class QueenTest {
    @Test
    public void whiteQueenCanMoveUpOneSquare() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 3);
        board.placePiece(coords, queen);
    }
}
