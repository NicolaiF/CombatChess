package interfaces;

public interface AbstractPieceFactory {
    Piece createPawn(boolean isWhite);
    Piece createRook(boolean isWhite);
    Piece createKnight(boolean isWhite);
    Piece createBishop(boolean isWhite);
    Piece createQueen(boolean isWhite);
    Piece createKing(boolean isWhite);
}
