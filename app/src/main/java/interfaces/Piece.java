package interfaces;

import model.Move;
import sheep.game.Sprite;

import java.util.ArrayList;

public interface Piece {
    String getType();
    Sprite getSprite();
    boolean isWhite();
    ArrayList<Move> getLegalMoves();
    ArrayList<Move> getCaptureMoves();
    boolean isAlive();
    void setSprite(Sprite sprite);
    void moved();
}
