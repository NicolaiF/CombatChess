package model.pieces;

import model.Move;

public class Bishop extends ChessPiece {

    /**
     * @param style reference to image resource for this piece
     * @param color black or white
     */
    public Bishop(String style, String color) {
        super(style, "Bishop", color);

        legalMoves.add(new Move(1,1, true));
        legalMoves.add(new Move(1,-1, true));
        legalMoves.add(new Move(-1,1, true));
        legalMoves.add(new Move(-1,-1, true));
    }
}
