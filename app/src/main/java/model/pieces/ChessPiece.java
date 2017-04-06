package model.pieces;

import interfaces.Piece;
import model.Move;

import java.util.ArrayList;

public abstract class ChessPiece implements Piece {

    private String style;
    private boolean isAlive = true;
    private String color;
    private String type;

    /**
     * @param style reference to image resource for this piece
     * @param type name for this piece
     * @param isWhite piece is either white or black
     */
    public ChessPiece(String style, String type, boolean isWhite){
        this.style = style;
        this.type = type;

        if(isWhite){
            color = "white";
        } else {
            color = "black";
        }
    }

    public void killed(){ isAlive = false; }

    @Override
    public String getStyle() {
        return style;
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
    public void setStyle(String style) {this.style = style;}

    @Override
    public String toString(){
        return type;
    }
}
