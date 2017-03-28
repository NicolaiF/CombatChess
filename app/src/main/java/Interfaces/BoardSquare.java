package Interfaces;

import Model.ChessPiece;
import android.graphics.Color;
import java.util.ArrayList;

public interface BoardSquare {
    void setPiece(ChessPiece chessPiece);
    void setPowerUp(PowerUp powerUp);
    ArrayList<String> getLegalMoves();
    String getColor();
    GamePiece getPiece();
    PowerUp getPowerUp();
}
