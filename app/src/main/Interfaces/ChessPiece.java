import android.graphics.drawable.Drawable;

public interface ChessPiece {
    String type = null;
    Drawable style = null;
    boolean alive = true;
    String color = null;

    String getType();
    Drawable getStyle();
    String getColor();
    boolean isAlive();
}
