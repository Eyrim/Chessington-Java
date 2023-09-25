package chessington.model.pieces;

import chessington.model.Board;
import chessington.model.Coordinates;
import chessington.model.Move;
import chessington.model.PlayerColour;
import org.apache.logging.log4j.core.Core;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.List;

import static chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class KingTest {
    @Test
    public void whiteKingCanMoveUpOneSquare() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-1, 0)));
    }

    @Test
    public void whiteKingCanMoveDownOneSquare() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
            // Starts on row 6 because moving back on the starting row is invalid
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 0)));
    }

    @Test
    public void kingsCannotMoveIfPieceInFront() {
        // Arrange
        Board board = Board.empty();

        Piece whiteKing = new King(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(3, 4);
        board.placePiece(whiteCoords, whiteKing);

        Piece blackKing = new King(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(4, 4);
        board.placePiece(blackCoords, blackKing);

        // Act
        List<Move> whiteMoves = whiteKing.getAllowedMoves(whiteCoords, board);
        List<Move> blackMoves = blackKing.getAllowedMoves(blackCoords, board);

        // Assert
        assertThat(whiteMoves).doesNotContain(new Move(whiteCoords, blackCoords));
        assertThat(blackMoves).doesNotContain(new Move(blackCoords, whiteCoords));
    }

    @Test
    public void whiteKingCannotMoveAtTopOfBoard() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(0, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-1, 0)));
    }

    @Test
    public void blackKingCannotMoveAtTopOfBoard() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1, 0)));
    }

    @Test
    public void kingsCannotMoveDiagonallyOffBoard() {
        // Arrange
        Board board = Board.empty();

        Piece whiteKing = new King(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(3, 0);
        board.placePiece(whiteCoords, whiteKing);

        Piece blackKing = new King(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(4, 0);
        board.placePiece(blackCoords, blackKing);

        // Act
        List<Move> whiteMoves = whiteKing.getAllowedMoves(whiteCoords, board);
        List<Move> blackMoves = blackKing.getAllowedMoves(blackCoords, board);

        // Assert
        assertThat(whiteMoves).doesNotContain(new Move(whiteCoords, whiteCoords.plus(0, -1)));
        assertThat(blackMoves).doesNotContain(new Move(blackCoords, blackCoords.plus(0, -1)));
    }
}
