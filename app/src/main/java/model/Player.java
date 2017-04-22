package model;

import interfaces.PowerUp;
import model.pieces.ChessPiece;

import java.util.ArrayList;

public class Player {

    private String ID;
    private ArrayList<ChessPiece> pieces;
    private ArrayList<PowerUp> powerUps;
    private int time;

    public Player(String ID) {
        this.ID = ID;
        pieces = new ArrayList<>();
        powerUps = new ArrayList<>();
    }

    public void addPiece(ChessPiece chessPiece) {
        pieces.add(chessPiece);
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void decreaseTime(int time) {
        this.time -= time;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Gives the player the first 16 pieces when starting a new game. Row 0-1 is black, Row 6-7 is white
     *
     * @param officerRow The officer row
     * @param pawnRow    The pawn row
     */
    public void giveStartingPieces(Tile[] officerRow, Tile[] pawnRow) {
        for (Tile tile : officerRow) {
            addPiece(tile.getPiece());
        }

        for (Tile tile : pawnRow) {
            addPiece(tile.getPiece());
        }
    }

    public void increaseTime(int time) {
        this.time += time;
    }

    public boolean ownsPiece(ChessPiece chessPiece) {
        return pieces.contains(chessPiece);
    }

    public void removePiece(ChessPiece chessPiece) {
        pieces.remove(chessPiece);
    }

    public void removePowerUp(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }

    public void usePowerUp(String powerUpName) {
        /* TODO: Find a way to identify the power up in the power up list. Then use powerUp.activatePowerUp().
        Finally call removePowerUp() to remove it from the players available power ups */
    }
}
