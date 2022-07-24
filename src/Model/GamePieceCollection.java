package Model;

import Model.GamePiece;

import java.util.HashMap;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 19/07/2022, Tuesday
 **/
public class GamePieceCollection {
  private final HashMap<String, GamePiece> pieces;

  public GamePieceCollection(int playerNo) {
    pieces = new HashMap<>();
    initialise(playerNo);
  }

  private void initialise(int playerNo) {
    // board is an 8x8 2D array i.e., row and column numbers are from top left to bottom right and start from 0 //
    int pawnRow;
    int otherPiecesRow;

    if (playerNo == 1) {
      pawnRow = 6;
      otherPiecesRow = 7;
      pieces.put("queen", new GamePiece(GamePiece.PieceType.QUEEN, playerNo, new Location(7, 3)));
      pieces.put("king", new GamePiece(GamePiece.PieceType.KING, playerNo, new Location(7, 4)));
    } else {
      pawnRow = 1;
      otherPiecesRow = 0;
      pieces.put("queen", new GamePiece(GamePiece.PieceType.QUEEN, playerNo, new Location(0, 3)));
      pieces.put("king", new GamePiece(GamePiece.PieceType.KING, playerNo, new Location(0, 4)));
    }
    for (int i = 0; i < 8; i++) {
      pieces.put("pawn" + i, new GamePiece(GamePiece.PieceType.PAWN, playerNo, new Location(pawnRow, i)));
    }

    int[] knightColumns = new int[]{1, 6};
    for (int i = 0; i < 2; i++) {
      pieces.put("knight" + i, new GamePiece(GamePiece.PieceType.KNIGHT, playerNo, new Location(otherPiecesRow, knightColumns[i])));
    }

    int[] bishopColumns = new int[]{2, 5};
    for (int i = 0; i < 2; i++) {
      pieces.put("bishop" + i, new GamePiece(GamePiece.PieceType.BISHOP, playerNo, new Location(otherPiecesRow, bishopColumns[i])));
    }

    int[] rookColumns = new int[]{0, 7};
    for (int i = 0; i < 2; i++) {
      pieces.put("rook" + i, new GamePiece(GamePiece.PieceType.ROOK, playerNo, new Location(otherPiecesRow, rookColumns[i])));
    }
  }

  public HashMap<String, GamePiece> getPieces() {
    return pieces;
  }
}
