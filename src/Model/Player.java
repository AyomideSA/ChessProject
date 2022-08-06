package Model;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 19/07/2022, Tuesday
 **/
public interface Player {

  public final static int WHITE = 1;
  public final static int BLACK = 2;

  String getName();

  int getPlayerNumber();

  Move getMove();

  GamePieceCollection getGamePieceCollection();

}
