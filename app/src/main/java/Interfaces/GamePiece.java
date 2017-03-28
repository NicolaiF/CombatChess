package Interfaces;

import android.graphics.drawable.Drawable;

public interface GamePiece {
    String getType();
    String getStyle();
    String getColor();
    boolean isAlive();
    void setStyle(String style);
}
