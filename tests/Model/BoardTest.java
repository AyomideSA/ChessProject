package Model;

import UI.TextUI;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 29/07/2022, Friday
 **/
class BoardTest {


  TextUI ui = new TextUI();

  @org.junit.jupiter.api.Test
  void validMove() {

    Board board = new Board();
    ui.setGameBoard(board.getGameBoard());
    

    //  KNIGHT //

    board.getGameBoard()[2][3] = new GamePiece(GamePiece.PieceType.KNIGHT, 1, new Location(2, 3));
    ui.printBoardWhite();
    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(0, 4))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(0, 2))));

    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(4, 4))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(4, 2))));

    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 1))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(1, 1))));

    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 5))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[2][3], 1, new Location(1, 5))));

    ui.printBoardBlack();
    board.getGameBoard()[4][3] = new GamePiece(GamePiece.PieceType.KNIGHT, 2, new Location(4, 3));
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(2, 4))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(2, 2))));

    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(6, 4))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(6, 2))));

    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(5, 1))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(3, 1))));

    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(5, 5))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][3], 2, new Location(3, 5))));

    board.getGameBoard()[4][3] = null;
    board.getGameBoard()[2][3] = null;

    // BISHOP //

    board.getGameBoard()[3][3] = new GamePiece(GamePiece.PieceType.BISHOP, 1, new Location(3, 3));
    ui.printBoardWhite();
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(5, 1))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(1, 5))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(1, 1))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(5, 5))));

    board.getGameBoard()[3][3] = new GamePiece(GamePiece.PieceType.BISHOP, 2, new Location(3, 3));
    ui.printBoardBlack();
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(5, 1))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(1, 5))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(1, 1))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(5, 5))));

    // Rook //
    board.getGameBoard()[3][3] = new GamePiece(GamePiece.PieceType.ROOK, 1, new Location(3, 3));
    ui.printBoardWhite();
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(3, 7))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(3, 0))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(5, 3))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(2, 3))));

    board.getGameBoard()[3][3] = new GamePiece(GamePiece.PieceType.ROOK, 2, new Location(3, 3));
    ui.printBoardBlack();
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(3, 7))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(3, 0))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(5, 3))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(2, 3))));

    // Queen //
    board = new Board();
    board.getGameBoard()[3][3] = new GamePiece(GamePiece.PieceType.QUEEN, 1, new Location(3,3));
    ui.printBoardWhite();
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 1, new Location(7, 7))));

    // Pawn //
    board = new Board();
    GamePiece pawn = new GamePiece(GamePiece.PieceType.PAWN, 1, new Location(5,5));
    board.getGameBoard()[5][5] = pawn;
    ui.printBoardWhite();

    // Moving Two Spaces test
    assertTrue(board.validMove(new Move(board.getGameBoard()[5][5], 1, new Location(3, 5))));
    pawn.setWasMoved(true);
    assertFalse(board.validMove(new Move(board.getGameBoard()[5][5], 1, new Location(3, 5))));
    // Moving One Space
    assertTrue(board.validMove(new Move(board.getGameBoard()[5][5], 1, new Location(4, 5))));
    // Pawn Capturing as white
    board.getGameBoard()[4][4] = new GamePiece(GamePiece.PieceType.KNIGHT, 2, new Location(4,4));
    board.getGameBoard()[4][6] = new GamePiece(GamePiece.PieceType.KNIGHT, 2, new Location(4,6));
    assertTrue(board.validMove(new Move(board.getGameBoard()[5][5], 1, new Location(4,4))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[5][5], 1, new Location(4,6))));


    board = new Board();
    pawn = new GamePiece(GamePiece.PieceType.PAWN, 2, new Location(3,3));
    board.getGameBoard()[3][3] = pawn;
    ui.printBoardBlack();

    // Moving Two Spaces test
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(5, 3))));
    pawn.setWasMoved(true);
    assertFalse(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(5, 3))));
    // Moving One Space
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(4, 3))));
    // Pawn Capturing as black
    board.getGameBoard()[4][4] = new GamePiece(GamePiece.PieceType.KNIGHT, 1, new Location(4,4));
    board.getGameBoard()[4][2] = new GamePiece(GamePiece.PieceType.KNIGHT, 1, new Location(4,2));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(4,4))));
    assertTrue(board.validMove(new Move(board.getGameBoard()[3][3], 2, new Location(4,2))));


    board = new Board();
    ui.setGameBoard(board.getGameBoard());

    // Pawn enPassant as white
    board.getGameBoard()[4][4] = new GamePiece(GamePiece.PieceType.PAWN, 1,new Location(4,4));
    GamePiece opponent1 = new GamePiece(GamePiece.PieceType.PAWN, 2, new Location(4,3));
    GamePiece opponent2 = new GamePiece(GamePiece.PieceType.PAWN, 2, new Location(4, 5));
    board.getGameBoard()[4][3] = opponent1;
    board.getGameBoard()[4][5] = opponent2;
    ui.printBoardWhite();

    assertFalse(board.validMove(new Move(board.getGameBoard()[4][4], 1, new Location(3, 3))));
    opponent1.setMovedTwoSpaces(true);
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][4], 1, new Location(3, 3))));

    assertFalse(board.validMove(new Move(board.getGameBoard()[4][4], 1, new Location(3, 5))));
    opponent2.setMovedTwoSpaces(true);
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][4], 1, new Location(3, 5))));


    // Pawn enPassant as Black
    board.getGameBoard()[4][4] = new GamePiece(GamePiece.PieceType.PAWN, 2,new Location(4,4));
    opponent1 = new GamePiece(GamePiece.PieceType.PAWN, 1, new Location(4,3));
    opponent2 = new GamePiece(GamePiece.PieceType.PAWN, 1, new Location(4, 5));
    board.getGameBoard()[4][3] = opponent1;
    board.getGameBoard()[4][5] = opponent2;
    ui.printBoardBlack();

    assertFalse(board.validMove(new Move(board.getGameBoard()[4][4], 2, new Location(5, 3))));
    opponent1.setMovedTwoSpaces(true);
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][4], 2, new Location(5, 3))));

    assertFalse(board.validMove(new Move(board.getGameBoard()[4][4], 2, new Location(5, 5))));
    opponent2.setMovedTwoSpaces(true);
    assertTrue(board.validMove(new Move(board.getGameBoard()[4][4], 2, new Location(5, 5))));

  }

  void makeMove() {

    Board board = new Board();
    ui.setGameBoard(board.getGameBoard());

    // Knight check
    GamePiece n = new GamePiece(GamePiece.PieceType.KNIGHT, 1, new Location(2, 3));

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(0, 4)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[0][4], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(0, 2)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[0][2], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(4, 4)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[4][4], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(4, 2)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[4][2], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 5)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][5], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(1, 5)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][5], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 1)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][1], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(1, 1)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][1], n);

    // Bishop check
    board = new Board();
    n = new GamePiece(GamePiece.PieceType.BISHOP, 1, new Location(2, 3));

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 4)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][4], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(1, 2)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[1][2], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 2)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][2], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(1, 4)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[1][4], n);

    // Rook check
    board = new Board();
    n = new GamePiece(GamePiece.PieceType.ROOK, 1, new Location(2, 3));

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(2, 4)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[2][4], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(2, 2)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[2][2], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 3)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][3], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(1, 3)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[1][3], n);

    // Pawn check
    board = new Board();
    n = new GamePiece(GamePiece.PieceType.PAWN, 1, new Location(2, 3));

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(3, 3)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][3], n);

    board.getGameBoard()[2][3] = n;
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(4, 3)));
    assertNotNull(board.getGameBoard()[2][3]);
    assertNotSame(board.getGameBoard()[3][3], n);

    n.setWasMoved(false);
    board.makeMove(new Move(board.getGameBoard()[2][3], 1, new Location(4, 3)));
    assertNull(board.getGameBoard()[2][3]);
    assertSame(board.getGameBoard()[3][3], n);

  }

}