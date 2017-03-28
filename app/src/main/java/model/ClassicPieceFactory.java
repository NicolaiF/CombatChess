package model;

import interfaces.AbstractPieceFactory;
import interfaces.Piece;
import model.pieces.*;

public class ClassicPieceFactory implements AbstractPieceFactory {

    private String style = "classic";

    @Override
    public Piece createPawn(String color) {
        return new Pawn(style + "_" + color + "_pawn", color);
    }

    @Override
    public Piece createRook(String color) {
        return new Rook(style + "_" + color + "_rook", color);
    }

    @Override
    public Piece createKnight(String color) {
        return new Knight(style + "_" + color + "_knight", color);
    }

    @Override
    public Piece createBishop(String color) {
        return new Bishop(style + "_" + color + "_bishop", color);
    }

    @Override
    public Piece createQueen(String color) {
        return new Queen(style + "_" + color + "_queen", color);
    }

    @Override
    public Piece createKing(String color) {
        return new King(style + "_" + color + "_king", color);
    }
}
