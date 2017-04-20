package model.pieces;

import java.util.ArrayList;

import model.Move;
import sheep.game.Sprite;

public class King extends ChessPiece {

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();
    private boolean isMoved = false;

    /**
     * @param sprite visual representation of the piece
     * @param isWhite piece is either white or black
     */
    public King(Sprite sprite, boolean isWhite) {
        super(sprite, "King", isWhite);

        legalMoves.add(new Move(-1,-1, false));
        legalMoves.add(new Move(-1,0, false));
        legalMoves.add(new Move(-1,1, false));
        legalMoves.add(new Move(0,-1, false));
        legalMoves.add(new Move(0,1, false));
        legalMoves.add(new Move(1,-1, false));
        legalMoves.add(new Move(1,0, false));
        legalMoves.add(new Move(1,1, false));
    }

    public boolean getIsMoved(){
        return isMoved;
    }

    @Override
    public ArrayList<Move> getLegalMoves() {
        return legalMoves;
    }

    @Override
    public ArrayList<Move> getCaptureMoves() {
        return captureMoves;
    }

    @Override
    public void moved() {
        isMoved = true;
    }
}
