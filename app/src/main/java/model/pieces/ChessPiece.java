package model.pieces;

import interfaces.Piece;
import model.Move;
import sheep.game.Sprite;

import java.util.ArrayList;

public abstract class ChessPiece implements Piece {

    private Sprite sprite;
    private boolean isAlive = true;
    private String color;
    private String type;

    /**
     * @param sprite visual representation of the piece
     * @param type name for this piece
     * @param isWhite piece is either white or black
     */
    public ChessPiece(Sprite sprite, String type, boolean isWhite){
        this.sprite = sprite;
        this.type = type;

        if(isWhite){
            color = "white";
        } else {
            color = "black";
        }
    }

    public void killed(){ isAlive = false; }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public abstract ArrayList<Move> getLegalMoves();

    @Override
    public abstract ArrayList<Move> getCaptureMoves();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setStyle(Sprite sprite) {this.sprite = sprite;}

    @Override
    public String toString(){
        return type;
    }
}
