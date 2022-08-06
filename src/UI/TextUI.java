package UI;

import Model.Board;
import Model.GamePiece;

import java.util.Scanner;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 26/07/2022, Tuesday
 **/
public class TextUI  {

  private GamePiece[][] gameBoard;
  private Scanner in;

  public TextUI() {
    in = new Scanner(System.in);
  }

  public void printBoardWhite() {
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
        if (this.gameBoard[i][j] == null) {
          System.out.print(" " + " | ");
        } else {
          System.out.print(this.gameBoard[i][j].getSymbol() + " | ");
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

  public void printBoardBlack() {
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
        if (this.gameBoard[i][j] == null) {
          System.out.print(" " + " | ");
        } else {
          System.out.print(this.gameBoard[i][j].getSymbol() + " | ");
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

  public GamePiece getChosenPiece() {
    System.out.println("Enter the position of the piece you will move (i.e., a1):");
    String position = this.in.nextLine().trim();
    int column = position.charAt(0) - 97; // Converts letters to column index. 97(a) - 97 = 0, 98(b) - 97 = 1 etc. Remember, internal representation is an 8x8 array
    int row = 8 - Character.getNumericValue(position.charAt(1)); // Converts external row index to internal row index. 8 - 8 = 0, 8 - 7 = 1 .... 0 - 0 = 0
    return gameBoard[row][column];
  }

  public int[] getNewPosition() {
    System.out.println("Enter the column and row that you want to move the piece to (letter then number, i.e. a1)");
    String newPosition = this.in.nextLine().trim();
    int column = newPosition.charAt(0) - 97; // Converts letters to column index. 97(a) - 97 = 0, 98(b) - 97 = 1 etc. Remember, internal representation is an 8x8 array
    int row = 8 - Character.getNumericValue(newPosition.charAt(1)); // Converts external row index to internal row index. 8 - 8 = 0, 8 - 7 = 1 .... 0 - 0 = 0
    return new int[]{row, column};
  }

  public void setGameBoard(GamePiece[][] gameBoard) {
    this.gameBoard = gameBoard;
  }

}
