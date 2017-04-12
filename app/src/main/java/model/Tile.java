package model;

import interfaces.PowerUp;
import main.R;
import model.pieces.ChessPiece;
import sheep.game.Sprite;
import sheep.graphics.Image;


public class Tile {

    private String color;
    private ChessPiece chessPiece;
    private PowerUp powerUp;
    private boolean highlighted;
    private Sprite highlightSprite;

    /**
     * Creates a new Tile on the chess board
     * @param color The color for this tile
     */
    public Tile(String color){
        this.color = color;
        highlightSprite = new Sprite(new Image(R.drawable.highlight_babyblue));
    }

    /** Removes the piece in this tile
     * @return the piece that was removed
     */
    public ChessPiece removePiece(){
        ChessPiece chessPiece = this.chessPiece;
        this.chessPiece = null;
        return chessPiece;
    }

    /** Removes the power up in this tile
     * @return the powerUp that was removed
     */
    public PowerUp removePowerUp(){
        PowerUp powerUp = this.powerUp;
        this.powerUp = null;
        return powerUp;
    }

    public String getColor() {
        return color;
    }

    public ChessPiece getPiece() {
        return chessPiece;
    }

    /**
     * Sets a ChessPiece for this Tile
     * @param chessPiece The new chess piece for this tile
     */
    public void setPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    /** Sets a PowerUp for this tile
     * @param powerUp The new power up for this tile
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    @Override
    public String toString(){
        if(chessPiece != null){
            return chessPiece.toString();
        }
        return color;
    }

    public boolean hasPiece() {
        return chessPiece != null;
    }

    /**
     * @param highlighted true if this tile is highlighted
     *
     * */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public Sprite getHighlightSprite(){
        return highlightSprite;
    }

    public boolean isHighlighted() {
        return highlighted;
    }
}
