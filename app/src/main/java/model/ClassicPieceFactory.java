package model;

import interfaces.AbstractPieceFactory;
import interfaces.Piece;
import main.R;
import model.pieces.*;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class ClassicPieceFactory implements AbstractPieceFactory {

    @Override
    public Piece createPawn(boolean isWhite) {
        if(isWhite){
            Image img = new Image(R.drawable.classic_fill_white_pawn);
            return new Pawn(new Sprite(img), isWhite);
        } else {
            Image img = new Image(R.drawable.classic_fill_black_pawn);
            return new Pawn(new Sprite(img), isWhite);
        }
    }

    @Override
    public Piece createRook(boolean isWhite) {
        if(isWhite){
            return new Rook(new Sprite(new Image(R.drawable.classic_fill_white_rook)), isWhite);
        } else {
            return new Rook(new Sprite(new Image(R.drawable.classic_fill_black_rook)), isWhite);
        }
    }

    @Override
    public Piece createKnight(boolean isWhite) {
        if(isWhite){
            return new Knight(new Sprite(new Image(R.drawable.classic_fill_white_knight)), isWhite);
        } else {
            return new Knight(new Sprite(new Image(R.drawable.classic_fill_black_knight)), isWhite);
        }
    }

    @Override
    public Piece createBishop(boolean isWhite) {
        if(isWhite){
            return new Bishop(new Sprite(new Image(R.drawable.classic_fill_white_bishop)), isWhite);
        } else {
            return new Bishop(new Sprite(new Image(R.drawable.classic_fill_black_bishop)), isWhite);
        }
    }

    @Override
    public Piece createQueen(boolean isWhite) {
        if(isWhite){
            return new Queen(new Sprite(new Image(R.drawable.classic_fill_white_queen)), isWhite);
        } else {
            return new Queen(new Sprite(new Image(R.drawable.classic_fill_black_queen)), isWhite);
        }
    }

    @Override
    public Piece createKing(boolean isWhite) {
        if(isWhite){
            return new King(new Sprite(new Image(R.drawable.classic_fill_white_king)), isWhite);
        } else {
            return new King(new Sprite(new Image(R.drawable.classic_fill_black_king)), isWhite);
        }
    }
}
