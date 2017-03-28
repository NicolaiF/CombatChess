package model.pieces;

import model.Move;

public class Queen extends ChessPiece {

    /**
     * @param style reference to image resource for this piece
     * @param color black or white
     */
    public Queen(String style, String color) {
        super(style, "Queen", color);

        legalMoves.add(new Move(-1,-1, true));
        legalMoves.add(new Move(-1,0, true));
        legalMoves.add(new Move(-1,1, true));
        legalMoves.add(new Move(0,-1, true));
        legalMoves.add(new Move(0,1, true));
        legalMoves.add(new Move(1,-1, true));
        legalMoves.add(new Move(1,0, true));
        legalMoves.add(new Move(1,1,true));
    }
}
