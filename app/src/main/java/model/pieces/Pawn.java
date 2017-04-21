package model.pieces;

import model.Move;
import sheep.game.Sprite;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();
    private boolean isMoved = false;
    private boolean isPassantable = false;

    /**
     * @param sprite visual representation of the piece
     * @param isWhite piece is either white or black
     */
    public Pawn(Sprite sprite, boolean isWhite) {
        super(sprite, "Pawn", isWhite);

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
        if(!isMoved) {
            isMoved = true;
            legalMoves.remove(1);
        }
    }

    @Override
    public ArrayList<Move> getLegalMoves() {
        return legalMoves;
    }

    @Override
    public ArrayList<Move> getCaptureMoves() {
        return captureMoves;
    }

    public boolean isPassantable() {
        return isPassantable;
    }

    public void setPassantable(boolean passantable) {
        isPassantable = passantable;
    }
}
