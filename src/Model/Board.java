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
    initialization(player1Collection, player2Collection);
  }

  public void initialization(GamePieceCollection player1Collection, GamePieceCollection player2Collection) {
    for (GamePiece piece : player1Collection.getPieces().values()) {
      gameBoard[piece.getLocation().getRow()][piece.getLocation().getColumn()] = piece;
    }
    for (GamePiece piece : player2Collection.getPieces().values()) {
      gameBoard[piece.getLocation().getRow()][piece.getLocation().getColumn()] = piece;
    }
  }

  public Board() { // for testing;
    gameBoard = new GamePiece[BOARD_HEIGHT][BOARD_WIDTH];
  }

  public boolean validMove(Move move) {
    GamePiece piece = move.getChosenPiece();
    int rowDestination = move.getLocation().getRow();
    int columnDestination = move.getLocation().getColumn();
    char pieceSymbol = piece.getSymbol();
    // TODO condition that prevents king from being put into check
    if (!occupied(piece.getLocation().getRow(), piece.getLocation().getColumn())
        || notPlayersPiece(pieceSymbol, move.getPlayerNo()) // Might just change this line to comparing numbers
        || outOfBounds(move)
        || occupied(rowDestination, columnDestination) && !oppositePlayerPiece(rowDestination, columnDestination, move.getPlayerNo())) {
      return false;
    }

    pieceSymbol = Character.toLowerCase(pieceSymbol);
    return switch (pieceSymbol) {
      case 'n' -> validMoveKnight(move);
      case 'b' -> validMoveBishop(move);
      case 'r' -> validMoveRook(move);
      case 'q' -> validMoveBishop(move) || validMoveRook(move);
      case 'p' ->
          // TODO Make sure pawn can't move two spaces if there is a piece in its way
          validMovePawn(move);
      case 'k' -> validMoveKing(move);
      default -> false;
    };

  }

  // TODO implement basic functionality
  // TODO castling
  // TODO make tests
  private boolean validMoveKing(Move move) {
    int rowDifference = move.getLocation().getRow() - move.getChosenPiece().getLocation().getRow();
    int columnDifference = move.getLocation().getColumn() - move.getChosenPiece().getLocation().getColumn();
    int destinationRow = move.getLocation().getRow();
    int destinationColumn = move.getLocation().getColumn();

    if (Math.abs(rowDifference) > 1 || Math.abs(columnDifference) > 1) {
      return false;
    }

    GamePiece[][] boardCopy = new GamePiece[BOARD_HEIGHT][BOARD_WIDTH];

    // copy of board made to check whether king would be in checkmate or not
    for (int i = 0; i < boardCopy.length; i++) {
      for (int j = 0; j < boardCopy[0].length; j++) {
        if (occupied(i, j)) {
          boardCopy[i][j] = gameBoard[i][j].clone();
        }
      }
    }
    boardCopy[destinationRow][destinationColumn] = move.getChosenPiece();
    if (inCheckmate(destinationRow, destinationColumn, boardCopy)) {
      return false;
    }

    return true;
  }

  // TODO
  private boolean inCheckmate(int destinationRow, int destinationColumn, GamePiece[][] boardCopy) {
    return false;
  }

  private boolean outOfBounds(Move move) {
    Location location = move.getLocation();
    return location.getColumn() < 0 || location.getColumn() > 7 || location.getRow() < 0 || location.getRow() > 7;
  }

  private boolean validMovePawn(Move move) {
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

    if (validForwardMovement(move, moveDirection)) {
      return true;
    } else
      return validCapture(captureDirections, move);
  }

  private boolean validForwardMovement(Move move, int moveDirection) {
    int rowDifference = move.getLocation().getRow() - move.getChosenPiece().getLocation().getRow();
    int columnDifference = move.getLocation().getColumn() - move.getChosenPiece().getLocation().getColumn();
    int destinationRow = move.getLocation().getRow();
    int destinationColumn = move.getLocation().getColumn();

    return ((rowDifference == moveDirection && columnDifference == 0) // One space forward
        || (!move.getChosenPiece().wasMoved() && rowDifference == moveDirection * 2 && columnDifference == 0))
            && !occupied(destinationRow, destinationColumn); // Two Spaces forward
  }

  private boolean validCapture(int[][] captureDirections, Move move) {
    int rowDifference = move.getLocation().getRow() - move.getChosenPiece().getLocation().getRow();
    int columnDifference = move.getLocation().getColumn() - move.getChosenPiece().getLocation().getColumn();

    for (int[] directions : captureDirections) { // diagonals
      if (directions[0] == rowDifference && directions[1] == columnDifference) {
        if (regularCaptureAllowed(move)){
          return true;
        }
        if (enPassantAllowed(move, directions[1])) { // column+directions[1] as a space beside current piece being occupied is a condition for enPassant
          move.setEnPassant(true);
          return true;
        }
      }
    }
    return false;
  }


  private boolean regularCaptureAllowed(Move move) {
    return (occupied(move.getLocation().getRow(), move.getLocation().getColumn())
        && isOpponentsPiece(move.getPlayerNo(), move.getLocation().getRow(), move.getLocation().getColumn()));
  }

  private boolean enPassantAllowed(Move move, int direction) {
    int adjacentPieceRow = move.getChosenPiece().getLocation().getRow();
    int adjacentPieceColumn = move.getChosenPiece().getLocation().getColumn() + direction;
    return occupied(adjacentPieceRow, adjacentPieceColumn)
        && gameBoard[adjacentPieceRow][adjacentPieceColumn].movedTwoSpaces()
        && isOpponentsPiece(move.getPlayerNo(), adjacentPieceRow, adjacentPieceColumn)
        && !occupied(move.getLocation().getRow(), move.getLocation().getColumn());
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
          direction[1] == columnDiff) {
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

  // TODO implement piece promotion for pawn
  public void makeMove(Move move) {
    int newRow = move.getChosenPiece().getLocation().getRow();
    int newColumn = move.getChosenPiece().getLocation().getColumn();
    int pieceRow = move.getChosenPiece().getLocation().getRow();
    int pieceColumn = move.getChosenPiece().getLocation().getColumn();
    if (move.isEnPassant()) {
      int opponentPieceDirection;
      int columnDifference = move.getLocation().getColumn() - newColumn;
      if (columnDifference > 0) {
        opponentPieceDirection = 1;
      } else {
        opponentPieceDirection = -1;
      }
      gameBoard[pieceRow][pieceColumn+opponentPieceDirection] = null;
      gameBoard[newRow][newColumn] = move.getChosenPiece();
      gameBoard[pieceRow][pieceColumn] = null;
      move.getChosenPiece().setLocation(new Location(newRow, newColumn));
    } else {
      gameBoard[newRow][newColumn] = move.getChosenPiece();
      gameBoard[pieceRow][pieceColumn] = null;
    }
    if (Math.abs(newRow - pieceRow) == 2) {
      move.getChosenPiece().setMovedTwoSpaces(true);
    }
    move.getChosenPiece().setWasMoved(true);
  }

  private boolean oppositePlayerPiece(int rowDestination, int columnDestination, int playerNo) {
    return (gameBoard[rowDestination][columnDestination].getPlayerNo() - playerNo) != 0;
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
