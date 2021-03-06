package interfaces;

import model.Board;
import model.pieces.ChessPiece;
import sheep.game.Sprite;

public interface PowerUp {
    String getName();
    String getPowerUpInfo();
    void activatePowerUp(Board boar, ChessPiece chessPiece, int row, int column);
    Sprite getSprite();
}
