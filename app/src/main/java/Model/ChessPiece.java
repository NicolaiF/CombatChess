package Model;

import Interfaces.GamePiece;

public class ChessPiece implements GamePiece {

    private String type;
    private String style;

    public ChessPiece(String type, String style){
        this.type = type;
        this.style = style;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getStyle() {
        return style;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void setStyle(String style) {

    }

    @Override
    public String toString(){
        return type;
    }
}
