package model.pieces;

import java.util.ArrayList;
import model.Move;
import sheep.game.Sprite;

public class Bishop extends ChessPiece {

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> captureMoves = new ArrayList<>();

    /**
     * @param sprite visual representation of the piece
     * @param isWhite white or black piece
     */
    public Bishop(Sprite sprite, boolean isWhite) {

        super(sprite, "Bishop", isWhite);

        legalMoves.add(new Move(1,1, true));
        legalMoves.add(new Move(1,-1, true));
        legalMoves.add(new Move(-1,1, true));
        legalMoves.add(new Move(-1,-1, true));
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
