package model.pieces;

import model.Move;

public class Knight extends ChessPiece{

    /**
     * @param style reference to image resource for this piece
     * @param color black or white
     */
    public Knight(String style, String color) {
        super(style, "Knight", color);

        legalMoves.add(new Move(-2,-1,false));
        legalMoves.add(new Move(-2,1,false));
        legalMoves.add(new Move(-1,-2,false));
        legalMoves.add(new Move(-1,2,false));
        legalMoves.add(new Move(1,-2,false));
        legalMoves.add(new Move(1,2,false));
        legalMoves.add(new Move(2,-1,false));
        legalMoves.add(new Move(2,1,false));
    }
}
