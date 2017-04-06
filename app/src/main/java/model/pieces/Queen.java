package model.pieces;

import java.util.ArrayList;
import model.Move;

public class Queen extends ChessPiece {

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();

    /**
     * @param style reference to image resource for this piece
     * @param isWhite piece is either white or black
     */
    public Queen(String style, boolean isWhite) {
        super(style, "Queen", isWhite);

        legalMoves.add(new Move(-1,-1, true));
        legalMoves.add(new Move(-1,0, true));
        legalMoves.add(new Move(-1,1, true));
        legalMoves.add(new Move(0,-1, true));
        legalMoves.add(new Move(0,1, true));
        legalMoves.add(new Move(1,-1, true));
        legalMoves.add(new Move(1,0, true));
        legalMoves.add(new Move(1,1,true));
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
