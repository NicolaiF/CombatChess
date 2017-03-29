package model.pieces;

import interfaces.Piece;
import model.Move;

import java.util.ArrayList;

public abstract class ChessPiece implements Piece {

    private String style;
    private boolean isAlive = true;
    private String color;
    protected ArrayList<Move> legalMoves = new ArrayList<>();
    private String type;

    /**
     * @param style reference to image resource for this piece
     * @param type name for this piece
     * @param color black or white
     */
    public ChessPiece(String style, String type, String color){
        this.style = style;
        this.type = type;
        this.color = color;
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
    public ArrayList<Move> getLegalMoves() {
        return legalMoves;
    }

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
