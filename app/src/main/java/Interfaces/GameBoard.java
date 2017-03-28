package Interfaces;

import Model.ChessPiece;

public interface GameBoard {
    void generateBoard();
    void setPiece(String pos, ChessPiece chessPiece);
    void setPowerUp(String pos, PowerUp powerUp);
    boolean hasPiece(String pos);
    boolean hasPowerUp(String pos);
    BoardSquare getSquare(String pos);
 }
