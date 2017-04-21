package interfaces;

import controller.ChessBoardController;
import model.pieces.ChessPiece;
import sheep.game.Sprite;

public interface PowerUp {
    String getName();
    String getPowerUpInfo();
    void activatePowerUp(ChessBoardController controller, ChessPiece chessPiece, int row, int column);
    Sprite getSprite();
}
