import Model.*;
import UI.*;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 21/07/2022, Thursday
 **/
public class Main {

  public static void main(String[] args) {


    TextUI ui = new TextUI();
    Player player1 = new HumanPlayer(1, "Ayo", ui);
    Player player2 = new HumanPlayer(2, "Wiki", ui);
    Board board = new Board(player1.getGamePieceCollection(), player2.getGamePieceCollection());
    ui.setGameBoard(board.getGameBoard());
    ui.printBoardWhite();
    System.out.println(board.validMove(new Move(board.getGameBoard()[4][4], 1, new Location(2, 3))));
    //player1.getMove();
    ui.printBoardBlack();
    System.out.println(board.validMove(new Move(board.getGameBoard()[0][1], 2, new Location(2, 0))));
    //player2.getMove();
  }

}
