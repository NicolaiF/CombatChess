package model.pieces;

import model.Move;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();

    /**
     * @param style reference to image resource for this piece
     * @param isWhite piece is either white or black
     */
    public Pawn(String style, boolean isWhite) {
        super(style, "Pawn", isWhite);

        if(isWhite){
            legalMoves.add(new Move(-1, 0, false));
            legalMoves.add(new Move(-2, 0, false));
            captureMoves.add(new Move(-1,1,false));
            captureMoves.add(new Move(-1,-1,false));
        } else {
            legalMoves.add(new Move(1, 0, false));
            legalMoves.add(new Move(2, 0, false));
            captureMoves.add(new Move(1,1,false));
            captureMoves.add(new Move(1,-1,false));
        }

    }

    public void moved(){
        // Removing possibility to move two steps forward
        legalMoves.remove(1);
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
