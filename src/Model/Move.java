package Model;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 19/07/2022, Tuesday
 **/
public class Move {

  private GamePiece chosenPiece;
  private int playerNo;
  private Location location;
  private boolean enPassant;

  public Move(GamePiece chosenPiece, int playerNo, Location location) {
    this.chosenPiece = chosenPiece;
    this.playerNo = playerNo;
    this.location = location;
    this.enPassant = false;
  }

  public GamePiece getChosenPiece() {
    return chosenPiece;
  }

  public int getPlayerNo() {
    return playerNo;
  }

  public Location getLocation() {
    return location;
  }

  public boolean isEnPassant() {
    return enPassant;
  }

  public void setEnPassant(boolean enPassant) {
    this.enPassant = enPassant;
  }
}
