package model;

import interfaces.AbstractPieceFactory;
import interfaces.Piece;
import model.pieces.*;

public class ClassicPieceFactory implements AbstractPieceFactory {

    private String style = "classic";

    @Override
    public Piece createPawn(boolean isWhite) {
        if(isWhite){
            return new Pawn(style + "_white_pawn", isWhite);
        } else {
            return new Pawn(style + "_black_pawn", isWhite);
        }
    }

    @Override
    public Piece createRook(boolean isWhite) {
        if(isWhite){
            return new Rook(style + "_white_rook", isWhite);
        } else {
            return new Rook(style + "_black_rook", isWhite);
        }
    }

    @Override
    public Piece createKnight(boolean isWhite) {
        if(isWhite){
            return new Knight(style + "_white_knight", isWhite);
        } else {
            return new Knight(style + "_black_knight", isWhite);
        }
    }

    @Override
    public Piece createBishop(boolean isWhite) {
        if(isWhite){
            return new Bishop(style + "_white_bishop", isWhite);
        } else {
            return new Bishop(style + "_black_bishop", isWhite);
        }
    }

    @Override
    public Piece createQueen(boolean isWhite) {
        if(isWhite){
            return new Queen(style + "_white_queen", isWhite);
        } else {
            return new Queen(style + "_black_queen", isWhite);
        }
    }

    @Override
    public Piece createKing(boolean isWhite) {
        if(isWhite){
            return new King(style + "_white_king", isWhite);
        } else {
            return new King(style + "_black_king", isWhite);
        }
    }
}
