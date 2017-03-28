package model.pieces;

import model.Move;

public class King extends ChessPiece {

    /**
     * @param style reference to image resource for this piece
     * @param color black or white
     */
    public King(String style, String color) {
        super(style, "King", color);

        legalMoves.add(new Move(-1,-1, false));
        legalMoves.add(new Move(-1,0, false));
        legalMoves.add(new Move(-1,1, false));
        legalMoves.add(new Move(0,-1, false));
        legalMoves.add(new Move(0,1, false));
        legalMoves.add(new Move(1,-1, false));
        legalMoves.add(new Move(1,0, false));
        legalMoves.add(new Move(1,1, false));
    }
}
