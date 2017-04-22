package model.pieces;

import interfaces.Piece;
import model.Move;
import sheep.game.Sprite;

import java.util.ArrayList;

public abstract class ChessPiece implements Piece {

    private Sprite sprite;
    private boolean isAlive = true;
    private boolean isWhite;
    private String type;

    /**
     * @param sprite visual representation of the piece
     * @param type name for this piece
     * @param isWhite piece is either white or black
     */
    public ChessPiece(Sprite sprite, String type, boolean isWhite){
        this.sprite = sprite;
        this.type = type;
        this.isWhite = isWhite;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public abstract ArrayList<Move> getLegalMoves();

    @Override
    public abstract ArrayList<Move> getCaptureMoves();

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setSprite(Sprite sprite) {this.sprite = sprite;}

    @Override
    public void moved() {

    }

    @Override
    public String toString(){
        return type;
    }

    public void killed(){ isAlive = false; }
}
