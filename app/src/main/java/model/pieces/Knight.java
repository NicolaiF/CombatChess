package model.pieces;

import java.util.ArrayList;

import model.Move;
import sheep.game.Sprite;

public class Knight extends ChessPiece{

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();

    /**
     * @param sprite visual representation of the piece
     * @param isWhite piece is either white or black
     */
    public Knight(Sprite sprite, boolean isWhite) {
        super(sprite, "Knight", isWhite);

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
