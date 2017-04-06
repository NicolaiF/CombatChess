package model.pieces;

import java.util.ArrayList;

import model.Move;
import sheep.game.Sprite;

public class Rook extends ChessPiece {

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();

    /**
     * @param sprite visual representation of the piece
     * @param isWhite piece is either white or black
     */
    public Rook(Sprite sprite, boolean isWhite) {
        super(sprite, "Rook", isWhite);

        legalMoves.add(new Move(-1,0,true));
        legalMoves.add(new Move(1,0,true));
        legalMoves.add(new Move(0,-1,true));
        legalMoves.add(new Move(0,1,true));
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
