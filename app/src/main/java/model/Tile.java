package model;

import interfaces.PowerUp;
import model.pieces.ChessPiece;


public class Tile {

    private String color;
    private ChessPiece chessPiece;
    private PowerUp powerUp;

    /**
     * Creates a new Tile on the chess board
     * @param color The color for this tile
     */
    public Tile(String color){
        this.color = color;
    }

    /**
     * Sets a ChessPiece for this Tile
     * @param chessPiece The new chess piece for this tile
     */
    public void setPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    /** Sets a PowerUp for this tile
     * @param powerUp The new power up for this tile
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public String getColor() {
        return color;
    }

    public ChessPiece getPiece() {
        return chessPiece;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    @Override
    public String toString(){
        if(chessPiece != null){
            return chessPiece.toString();
        }
        return color;
    }
}
