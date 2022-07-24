package Model;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 19/07/2022, Tuesday
 **/
public class Board {
  private GamePiece[][] gameBoard;
  public final static int BOARD_WIDTH = 8;
  public final static int BOARD_HEIGHT = 8;

  public Board(GamePieceCollection player1Collection, GamePieceCollection player2Collection){
    gameBoard = new GamePiece[BOARD_HEIGHT][BOARD_WIDTH];
    //Set up the board
    initialization(player1Collection, player2Collection);
  }

  public void initialization(GamePieceCollection player1Collection, GamePieceCollection player2Collection) {
    // Sets actual positions on the board
    for (int i = 0; i < BOARD_HEIGHT; i++) {
      for (int j = 0; j < BOARD_WIDTH; j++) {
        gameBoard[i][j] = null;
      }
    }
    for (GamePiece piece: player1Collection.getPieces().values()) {
      gameBoard[piece.getLocation().getRow()][piece.getLocation().getColumn()] = piece;
    }
    for (GamePiece piece: player2Collection.getPieces().values()) {
      gameBoard[piece.getLocation().getRow()][piece.getLocation().getColumn()] = piece;
    }
  }

  // TODO move to separate UI
  public void printBoardWhite(GamePiece[][] gameBoard) {
    //Prints Skeleton of board.
    System.out.println("\n");
    System.out.println("BLOKUS DUOS\n\n");

    System.out.println("  \t---------------------------------");
    for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
      //Indexing rows.
      int rowNumber=8-i;
      System.out.print(rowNumber);
      System.out.print("\t| ");
      for (int j = 0; j < Board.BOARD_WIDTH; j++) {
        if (gameBoard[i][j] == null) {
          System.out.print(" " + " | ");
        } else {
          System.out.print(gameBoard[i][j].getSymbol() + " | ");
        }
      }
      System.out.println();
      System.out.println("  \t---------------------------------");
    }

    System.out.print("   ");

    //Indexing columns
    for (char i = 'a'; i <= 'h'; i++) {
        System.out.printf("%4s", i);
      }
    System.out.println("\n\n");
  }

  public void printBoardBlack(GamePiece[][] gameBoard) {
    //Prints Skeleton of board.
    System.out.println("\n");
    System.out.println("BLOKUS DUOS\n\n");

    System.out.println("  \t---------------------------------");
    for (int i = Board.BOARD_HEIGHT-1; i > -1; i--) {
      //Indexing rows.
      int rowNumber=8-i;
      System.out.print(rowNumber);
      System.out.print("\t| ");
      for (int j = Board.BOARD_WIDTH-1; j > -1; j--) {
        if (gameBoard[i][j] == null) {
          System.out.print(" " + " | ");
        } else {
          System.out.print(gameBoard[i][j].getSymbol() + " | ");
        }
      }
      System.out.println();
      System.out.println("  \t---------------------------------");
    }

    System.out.print("   ");

    //Indexing columns
    for (char i = 'a'; i <= 'h'; i++) {
      System.out.printf("%4s", i);
    }
    System.out.println("\n\n");
  }

  // TODO move to separate UI
  public GamePiece[][] getGameBoard() {
    return gameBoard;
  }

}
