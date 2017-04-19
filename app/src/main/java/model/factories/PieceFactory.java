package model.factories;

import interfaces.AbstractPieceFactory;
import interfaces.Piece;
import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;

public abstract class PieceFactory implements AbstractPieceFactory {


    @Override
    public Piece createPawn(boolean isWhite) {
        return new Pawn(createPawnSprite(isWhite), isWhite);
    }

    @Override
    public Piece createRook(boolean isWhite) {
        return new Rook(createRookSprite(isWhite), isWhite);
    }

    @Override
    public Piece createKnight(boolean isWhite) {
        return new Knight(createKnightSprite(isWhite), isWhite);
    }

    @Override
    public Piece createBishop(boolean isWhite) {
        return new Bishop(createBishopSprite(isWhite), isWhite);
    }

    @Override
    public Piece createQueen(boolean isWhite) {
        return new Queen(createQueenSprite(isWhite), isWhite);
    }

    @Override
    public Piece createKing(boolean isWhite) {
        return new King(createKingSprite(isWhite), isWhite);
    }
}
