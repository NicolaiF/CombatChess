package Interfaces;

import Model.ChessPiece;

import java.util.ArrayList;

public interface Player {
    void addPiece(ChessPiece chessPiece);
    void removePiece(ChessPiece chessPiece);
    boolean ownsPiece(ChessPiece chessPiece);
    void addPowerUp(PowerUp powerUp);
    void usePowerUp(String powerUpName);
    void removePowerUp(PowerUp powerUp);
    void increaseTime(int time);
    void decreaseTime(int time);
    ArrayList<ChessPiece> getPieces();
    int getTime();
}
