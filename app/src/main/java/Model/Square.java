package Model;

import Interfaces.BoardSquare;
import Interfaces.GamePiece;
import Interfaces.PowerUp;

import java.util.ArrayList;

public class Square implements BoardSquare {

    private String color;
    private ChessPiece chessPiece;
    private PowerUp powerUp;

    /**
     * Creates a new Square on the chess board
     * @param color The color for this square
     * @param chessPiece Reference to the chess piece in this square, if any
     * @param powerUp Reference to the power up in this square, if any
     */
    public Square(String color, ChessPiece chessPiece, PowerUp powerUp){
        this.color = color;
        this.chessPiece = chessPiece;
        this.powerUp = powerUp;
    }

    /**
     * Sets a ChessPiece for this Square
     * @param chessPiece The new chess piece for this square
     */
    @Override
    public void setPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    /** Sets a PowerUp for this square
     * @param powerUp The new power up for this square
     */
    @Override
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    /**
     * @return A list of legal moves for the chess piece in this square
     */
    @Override
    public ArrayList<String> getLegalMoves() {
        // TODO: Find legal moves for the chess piece in this square
        return null;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public ChessPiece getPiece() {
        return chessPiece;
    }

    @Override
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
