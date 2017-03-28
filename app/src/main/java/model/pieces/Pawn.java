package model.pieces;

import model.Move;

public class Pawn extends ChessPiece {

    /**
     * @param style reference to image resource for this piece
     * @param color black or white
     */
    public Pawn(String style, String color) {
        super(style, "Pawn", color);

        legalMoves.add(new Move(1, 0, false));
        legalMoves.add(new Move(2, 0, false));

        captureMoves.add(new Move(1,1,false));
        captureMoves.add(new Move(1,-1,false));
    }
}
