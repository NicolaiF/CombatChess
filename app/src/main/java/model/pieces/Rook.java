package model.pieces;

import model.Move;

public class Rook extends ChessPiece {
    /**
     * @param style reference to image resource for this piece
     * @param color black or white
     */
    public Rook(String style, String color) {
        super(style, "Rook", color);

        legalMoves.add(new Move(-1,0,true));
        legalMoves.add(new Move(1,0,true));
        legalMoves.add(new Move(0,-1,true));
        legalMoves.add(new Move(0,1,true));
    }
}
