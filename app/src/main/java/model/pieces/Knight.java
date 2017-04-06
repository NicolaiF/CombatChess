package model.pieces;

import java.util.ArrayList;

import model.Move;

public class Knight extends ChessPiece{

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();

    /**
     * @param style reference to image resource for this piece
     * @param isWhite piece is either white or black
     */
    public Knight(String style, boolean isWhite) {
        super(style, "Knight", isWhite);

        legalMoves.add(new Move(-2,-1,false));
        legalMoves.add(new Move(-2,1,false));
        legalMoves.add(new Move(-1,-2,false));
        legalMoves.add(new Move(-1,2,false));
        legalMoves.add(new Move(1,-2,false));
        legalMoves.add(new Move(1,2,false));
        legalMoves.add(new Move(2,-1,false));
        legalMoves.add(new Move(2,1,false));
    }

    @Override
    public ArrayList<Move> getLegalMoves() {
        return legalMoves;
    }

    @Override
    public ArrayList<Move> getCaptureMoves() {
        return captureMoves;
    }
}
