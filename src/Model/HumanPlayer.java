package Model;

import UI.TextUI;

import java.util.Arrays;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 21/07/2022, Thursday
 **/
public class HumanPlayer implements Player {

  private int playerNo;
  private String name;
  private TextUI ui;
  private GamePieceCollection gamePieceCollection;

  public HumanPlayer(int playerNo, String name, TextUI ui) {
    this.playerNo = playerNo;
    this.name = name;
    this.ui = ui;
    this.gamePieceCollection = new GamePieceCollection(playerNo);
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public int getPlayerNumber() {
    return 0;
  }

  @Override
  public Move getMove() {

    GamePiece chosenPiece = ui.getChosenPiece();
    int[] newPosition = ui.getNewPosition();

    System.out.println(chosenPiece);
    System.out.println(Arrays.toString(newPosition));

    return new Move(chosenPiece, this.playerNo, new Location(newPosition[0], newPosition[1]));
  }

  @Override
  public GamePieceCollection getGamePieceCollection() {
    return this.gamePieceCollection;
  }

}
