package interfaces;

import sheep.game.Sprite;
import sheep.graphics.Image;

public interface AbstractPieceFactory {
    Piece createPawn(boolean isWhite);
    Piece createRook(boolean isWhite);
    Piece createKnight(boolean isWhite);
    Piece createBishop(boolean isWhite);
    Piece createQueen(boolean isWhite);
    Piece createKing(boolean isWhite);

    Sprite createPawnSprite(boolean isWhite);
    Sprite createRookSprite(boolean isWhite);
    Sprite createKnightSprite(boolean isWhite);
    Sprite createBishopSprite(boolean isWhite);
    Sprite createQueenSprite(boolean isWhite);
    Sprite createKingSprite(boolean isWhite);

    Image getSampleImage();
}
