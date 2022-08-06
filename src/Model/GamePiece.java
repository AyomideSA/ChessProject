package Model;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 19/07/2022, Tuesday
 **/
public class GamePiece {
  enum PieceType {
    PAWN,
    KNIGHT,
    BISHOP,
    ROOK,
    QUEEN,
    KING
  };

  private PieceType pieceType;
  private int playerNo;
  private char symbol;
  private Location location;

  public GamePiece(PieceType pieceType, int playerNo, Location location) {
    this.pieceType = pieceType;
    this.playerNo = playerNo;

    if (playerNo == 1) {
      if (pieceType != PieceType.KNIGHT) {
        symbol = pieceType.toString().charAt(0); // 'Q' represents queen, 'K' for king etc....
      } else {
        symbol = 'N'; // 'N' instead of 'K used to represent Knight to prevent confusion with King
      }
    } else {
      if (pieceType != PieceType.KNIGHT) {
        symbol = pieceType.toString().toLowerCase().charAt(0);
      } else {
        symbol = 'n';
      }
    }
    this.location = location;
  }

  public char getSymbol() {
    return symbol;
  }

  public Location getLocation() {
    return location;
  }

  public int getPlayerNo() {
    return playerNo;
  }

  // Test Method
  @Override
  public String toString() {
    return "GamePiece{" +
        "pieceType=" + pieceType +
        ", playerNo=" + playerNo +
        ", symbol=" + symbol +
        ", location=" + location +
        '}';
  }
}
