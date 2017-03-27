import android.graphics.Color;

import java.util.ArrayList;

public interface Square {
    Color color = null;
    ChessPiece chessPiece = null;
    PowerUp powerUp = null;

    void setPiece(ChessPiece chessPiece);
    void setPowerUp(PowerUp powerUp);
    ArrayList<String> getLegalMoves();
    Color getColor();
    ChessPiece getPiece();
    PowerUp getPowerUp();
}
