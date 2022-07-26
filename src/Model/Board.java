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

  public GamePiece[][] getGameBoard() {
    return gameBoard;
  }

}
