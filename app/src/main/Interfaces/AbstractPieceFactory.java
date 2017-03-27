import android.graphics.drawable.Drawable;

public interface AbstractPieceFactory {
    Drawable style = null;

    ChessPiece createPiece(String type);
}
