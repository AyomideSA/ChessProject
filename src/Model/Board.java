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

  public Board(GamePieceCollection player1Collection, GamePieceCollection player2Collection) {
    gameBoard = new GamePiece[BOARD_HEIGHT][BOARD_WIDTH];
    initialization(player1Collection, player2Collection);
  }

  public void initialization(GamePieceCollection player1Collection, GamePieceCollection player2Collection) {
    for (int i = 0; i < BOARD_HEIGHT; i++) {
      for (int j = 0; j < BOARD_WIDTH; j++) {
        gameBoard[i][j] = null;
      }
    }
    for (GamePiece piece : player1Collection.getPieces().values()) {
      gameBoard[piece.getLocation().getRow()][piece.getLocation().getColumn()] = piece;
    }
    for (GamePiece piece : player2Collection.getPieces().values()) {
      gameBoard[piece.getLocation().getRow()][piece.getLocation().getColumn()] = piece;
    }
  }

  public Board() { // for testing;
    gameBoard = new GamePiece[BOARD_HEIGHT][BOARD_WIDTH];
    for (int i = 0; i < BOARD_HEIGHT; i++) {
      for (int j = 0; j < BOARD_WIDTH; j++) {
        gameBoard[i][j] = null;
      }
    }
  }

  public boolean validMove(Move move) {
    char pieceSymbol = move.getChosenPiece().getSymbol();
    // TODO condition that prevents king from being put into check
    if (notPlayersPiece(pieceSymbol, move.getPlayerNo()) || outOfBounds(move)) { // Might just change this line to comparing numbers
      System.out.println("test1");
      return false;
    }
    if (characterEqualsIgnoreCase(pieceSymbol, 'n')) {
      return validMoveKnight(move);
    }
    if (characterEqualsIgnoreCase(pieceSymbol, 'b')) {
      return validMoveBishop(move);
    }
    if (characterEqualsIgnoreCase(pieceSymbol, 'r')) {
      return validMoveRook(move);
    }
    if (characterEqualsIgnoreCase(pieceSymbol, 'q')) {
      return validMoveBishop(move) || validMoveRook(move);
    }
    if (characterEqualsIgnoreCase(pieceSymbol, 'p')) {
      return validMovePawn(move);
    }
    return false;
  }

  private boolean outOfBounds(Move move) {
    Location location = move.getLocation();
    return location.getColumn() < 0 || location.getColumn() > 7 || location.getRow() < 0 || location.getRow() > 7;
  }

  private boolean validMovePawn(Move move) {
    Location pieceLocation = move.getChosenPiece().getLocation();
    GamePiece pawn = move.getChosenPiece();
    int row = pieceLocation.getRow();
    int column = pieceLocation.getColumn();
    int rowDestination = move.getLocation().getRow();
    int columnDestination = move.getLocation().getColumn();
    int rowDifference = rowDestination - row;
    int columnDifference = columnDestination - column;
    int moveDirection; // One Space Forward
    int[][] captureDirections; // Diagonals

    if (move.getPlayerNo() == 1) {
      moveDirection = -1; // White moves up rows
      captureDirections = new int[][]{
      // {row_difference, column_difference}
          {-1, -1},
          {-1, 1}
      };
    } else {
      moveDirection = 1; // black moves down rows
      captureDirections = new int[][]{
          {1, -1},
          {1, 1}
      };
    }

    if (validForwardMovement(rowDifference, columnDifference, pawn.wasMoved(), moveDirection, rowDestination, columnDestination)) {
      return true;
    } else
      return validCapture(captureDirections, row, column, rowDifference, columnDifference, rowDestination, columnDestination, move.getPlayerNo());
  }

  private boolean validForwardMovement(int rowDiff, int columnDiff, boolean pawnMoved, int moveDirection, int rowDestination, int columnDestination) {
    return ((rowDiff == moveDirection && columnDiff == 0) // One space forward
        || (!pawnMoved && rowDiff == moveDirection * 2 && columnDiff == 0)) && !occupied(rowDestination, columnDestination); // Two Spaces forward
  }

  private boolean validCapture(int[][] captureDirections, int row, int column, int rowDiff, int columnDiff, int rowDestination, int columnDestination, int playerNo) {
    for (int[] directions : captureDirections) { // diagonals
      if (directions[0] == rowDiff && directions[1] == columnDiff) {
        if (regularCaptureAllowed(rowDestination, columnDestination, playerNo)
            || enPassantAllowed(row, column+directions[1], rowDestination, columnDestination, playerNo)) { // column+directions[1] as a space beside current piece being occupied
                                                                                                                  // is a condition for enPassant
          return true;
        }
      }
    }
    return false;
  }

  private boolean regularCaptureAllowed(int rowDestination, int columnDestination, int playerNo) {
    return (occupied(rowDestination, columnDestination)
        && isOpponentsPiece(playerNo, rowDestination, columnDestination));
  }

  private boolean enPassantAllowed(int row, int column, int rowDestination, int columnDestination, int playerNo) {
    return occupied(row, column)
        && gameBoard[row][column].movedTwoSpaces()
        && isOpponentsPiece(playerNo, row, column)
        && !occupied(rowDestination, columnDestination);
  }

  private boolean isOpponentsPiece(int playerNo, int row, int column) {
    return gameBoard[row][column].getPlayerNo() != playerNo;
  }

  private boolean validMoveKnight(Move move) {
    Location pieceLocation = move.getChosenPiece().getLocation();
    int row = pieceLocation.getRow();
    int column = pieceLocation.getColumn();
    int rowDestination = move.getLocation().getRow();
    int columnDestination = move.getLocation().getColumn();
    int rowDiff = rowDestination - row;
    int columnDiff = columnDestination - column;

    int[][] directions = new int[][]{
    // {row_difference, column_difference}
        {-2, 1},
        {-2, -1},
        {2, 1},
        {2, -1},
        {1, -2},
        {-1, -2},
        {1, 2},
        {-1, 2}
    };

    for (int[] direction : directions) {
      if (direction[0] == rowDiff &&
          direction[1] == columnDiff &&
          (!occupied(rowDestination, columnDestination) || oppositePlayerPiece(rowDestination, columnDestination, move.getPlayerNo()))) {
        return true;
      }
    }
    return false;
  }

  private boolean validMoveBishop(Move move) {
    Location pieceLocation = move.getChosenPiece().getLocation();
    int row = pieceLocation.getRow();
    int column = pieceLocation.getColumn();
    int rowDestination = move.getLocation().getRow();
    int columnDestination = move.getLocation().getColumn();
    int rowDiff = rowDestination - row;
    int columnDiff = columnDestination - column;

    int[][] directions = new int[][]{
    //  {row_difference, column_difference}
        {1, -1},
        {-1, 1},
        {1, 1},
        {-1, -1}
    };

    int directionIndex = -1; // Set to -1 to avoid variable may not be initialized error
    // Correct direction chosen based on sign of row and column differences
    if (rowDiff > 0 && columnDiff < 0) {
      directionIndex = 0;
    } else if (rowDiff < 0 && columnDiff > 0) {
      directionIndex = 1;
    } else if (rowDiff > 0 && columnDiff > 0) {
      directionIndex = 2;
    } else if (rowDiff < 0 && columnDiff < 0) {
      directionIndex = 3;
    }

    int rowOffset = directions[directionIndex][0];
    int columnOffset = directions[directionIndex][1];

    while ((row + rowOffset) != rowDestination && (column + columnOffset) != columnDestination) { // Only checks cells ON PATH to player move
      row += rowOffset;
      column += columnOffset;
      if (occupied(row,column)) {
        return false;
      }
    }

    return true;
  }

  private boolean validMoveRook(Move move) {
    Location pieceLocation = move.getChosenPiece().getLocation();
    int row = pieceLocation.getRow();
    int column = pieceLocation.getColumn();
    int rowDestination = move.getLocation().getRow();
    int columnDestination = move.getLocation().getColumn();
    int rowDiff = rowDestination - row;
    int columnDiff = columnDestination - column;

    int[][] directions = new int[][]{
    // {row_difference, column_difference}
        {0, -1},
        {0, 1},
        {-1, 0},
        {1, 0}
    };

    // Correct direction chosen based on sign of row and column differences
    int directionIndex = -1; // Set to -1 just to avoid variable may not be initialized error
    if (columnDiff < 0) {
      directionIndex = 0;
    } else if (columnDiff > 0) {
      directionIndex = 1;
    } else if (rowDiff < 0) {
      directionIndex = 2;
    } else if (rowDiff > 0) {
      directionIndex = 3;
    }

    int rowOffset = directions[directionIndex][0];
    int columnOffset = directions[directionIndex][1];

    while ((row + rowOffset) != rowDestination && (column + columnOffset) != columnDestination) { // Only checks cells on path to player move
      row += rowOffset;
      column += columnOffset;
      if (occupied(row, column)) {
        return false;
      }
    }

    return true;
  }

  private boolean oppositePlayerPiece(int rowDestination, int columnDestination, int playerNu) {
    return (gameBoard[rowDestination][columnDestination].getPlayerNo() - playerNu) != 0;
  }

  public GamePiece[][] getGameBoard() {
    return gameBoard;
  }

  private boolean characterEqualsIgnoreCase(char char1, char char2) {
    return Character.toLowerCase(char1) == Character.toLowerCase(char2);
  }

  private boolean notPlayersPiece(char pieceSymbol, int playerNo) {
    return Character.isUpperCase(pieceSymbol) && playerNo != 1 ||
        Character.isLowerCase(pieceSymbol) && playerNo != 2;
  }

  private boolean occupied(int row, int column) {
    return gameBoard[row][column] != null;
  }

}
