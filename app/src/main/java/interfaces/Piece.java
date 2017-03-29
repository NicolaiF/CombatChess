package interfaces;

import model.Move;

import java.util.ArrayList;

public interface Piece {
    String getType();
    String getStyle();
    String getColor();
    ArrayList<Move> getLegalMoves();
    ArrayList<Move> getCaptureMoves();
    boolean isAlive();
    void setStyle(String style);
}
