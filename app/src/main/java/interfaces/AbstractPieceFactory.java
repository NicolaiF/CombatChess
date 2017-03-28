package interfaces;

public interface AbstractPieceFactory {
    Piece createPawn(String color);
    Piece createRook(String color);
    Piece createKnight(String color);
    Piece createBishop(String color);
    Piece createQueen(String color);
    Piece createKing(String color);
}
