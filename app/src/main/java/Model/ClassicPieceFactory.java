package Model;

import Interfaces.AbstractPieceFactory;
import Interfaces.GamePiece;

public class ClassicPieceFactory implements AbstractPieceFactory {

    private String style = "classic";

    /**
     * @param type The chess piece type, i.e rook, bishop etc.
     * @return A new chess piece
     */
    @Override
    public GamePiece createPiece(String type) {
        return new ChessPiece(type, style + "_" + type);
    }
}
