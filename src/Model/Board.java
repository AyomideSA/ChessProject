package Model;

import java.util.Objects;

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
                                                              // TODO condition that checks if move is out of bounds
                                                              // TODO condition that prevents king from being put into check
    if ( notPlayersPiece(pieceSymbol, move.getPlayerNo()) || outOfBounds(move)) { // Might just change this line to comparing numbers
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
    int rowDiff = rowDestination - row;
    int columnDiff = columnDestination - column;
    int moveDirection; // One Space Forward
    int[][] captureDirections; // Diagonals
    if (move.getPlayerNo() == 1) {
      moveDirection = -1;
      captureDirections = new int[][]{
          {-1, -1},
          {-1, 1}
      };
    } else {
      moveDirection = 1;
      captureDirections = new int[][]{
          {1, -1},
          {1, 1}
      };
    }

    if (validForwardMovement(rowDiff, columnDiff, !pawn.wasMoved(), moveDirection, rowDestination, columnDestination)) {
      return true;
    } else return validCapture(captureDirections, row, column, rowDiff, columnDiff, rowDestination, columnDestination, move.getPlayerNo());
  }

  private boolean validForwardMovement(int rowDiff, int columnDiff, boolean pawnMoved, int moveDirection, int rowDestination, int columnDestination) {
    return ((rowDiff == moveDirection && columnDiff == 0) || (pawnMoved && rowDiff == moveDirection*2 && columnDiff == 0)) && Objects.isNull(gameBoard[rowDestination][columnDestination]);
  }

  private boolean validCapture(int[][] captureDirections, int row, int column, int rowDiff, int columnDiff, int rowDestination, int columnDestination, int playerNo) {
    for (int[] directions : captureDirections) {
      if (directions[0] == rowDiff && directions[1] == columnDiff) {
        if (!Objects.isNull(gameBoard[rowDestination][columnDestination]) && gameBoard[rowDestination][columnDestination].getPlayerNo() != playerNo) {
          return true;
        }
        if (!Objects.isNull(gameBoard[row][column+directions[1]]) && gameBoard[row][column+directions[1]].isLastTurnPassant() && gameBoard[row][column+directions[1]].getPlayerNo() != playerNo && Objects.isNull(gameBoard[rowDestination][columnDestination])) {
          return true;
        }
      }
    }
    return false;
  }


  private boolean validMoveKnight(Move move) {
    Location pieceLocation = move.getChosenPiece().getLocation();
    int row = pieceLocation.getRow();
    int column = pieceLocation.getColumn();
    int rowDestination = move.getLocation().getRow();
    int columnDestination = move.getLocation().getColumn();
    int rowDiff = rowDestination - row;
    int columnDiff = columnDestination - column;

    int[][] directions = new int[][] {
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
          (!occupied(rowDestination, columnDestination) || oppositePlayerPiece(rowDestination, columnDestination, move.getPlayerNo())) )  {
        return true;
      }
    }

    System.out.println("testKnight");
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

    int[][] directions = new int[][] {
                 // From White's view
        {1, -1}, // down left
        {-1, 1}, // up right
        {1, 1}, // down right
        {-1, -1} // up left
    };

    int directionIndex = -1; // Set to zero just to avoid variable may not be initialized error
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
    int columnOffset= directions[directionIndex][1];

    while ( (row + rowOffset) != rowDestination && (column + columnOffset) != columnDestination) { // Only checks cells on path to player move
      row += rowOffset;
      column += columnOffset;
      if (gameBoard[row][column] != null) {
        return false;
      }
    }

    System.out.println("testBishop");
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

    int[][] directions = new int[][] {
        // From White's view
        {0, -1}, // left
        {0, 1}, // right
        {-1, 0}, // up
        {1, 0} // down
    };

    int directionIndex = -1; // Set to zero just to avoid variable may not be initialized error
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
    int columnOffset= directions[directionIndex][1];

    while ( (row + rowOffset) != rowDestination && (column + columnOffset) != columnDestination) { // Only checks cells on path to player move
      row += rowOffset;
      column += columnOffset;
      if (gameBoard[row][column] != null) {
        return false;
      }
    }

    System.out.println("testRook");
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
