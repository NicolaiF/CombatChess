package Model;

import Interfaces.Player;
import Interfaces.PowerUp;

import java.util.ArrayList;

public class ChessPlayer implements Player {

    private String ID;
    private ArrayList<ChessPiece> pieces;
    private ArrayList<PowerUp> powerUps;
    private int time;

    public ChessPlayer(String ID){
        this.ID = ID;
        pieces = new ArrayList<>();
        powerUps = new ArrayList<>();

        // Give this player the first 16 pieces

    }

    /** Gives the player the first 16 pieces when starting a new game
     * @param officerRow The officer row
     * @param pawnRow The pawn row
     */
    public void giveStartingPieces(ArrayList<Square> officerRow, ArrayList<Square> pawnRow) {
        for(Square square : officerRow){
            addPiece(square.getPiece());
        }

        for(Square square : pawnRow){
            addPiece(square.getPiece());
        }
    }

    @Override
    public void addPiece(ChessPiece chessPiece) {
        pieces.add(chessPiece);
    }

    @Override
    public void removePiece(ChessPiece chessPiece) {
        pieces.remove(chessPiece);
    }

    @Override
    public boolean ownsPiece(ChessPiece chessPiece) {
        return pieces.contains(chessPiece);
    }

    @Override
    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    @Override
    public void usePowerUp(String powerUpName) {
        /* TODO: Find a way to identify the power up in the power up list. Then use powerUp.activatePowerUp().
        Finally call removePowerUp() to remove it from the players available power ups */
    }

    @Override
    public void removePowerUp(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }

    public void setTime(int time){
        this.time = time;
    }

    @Override
    public void increaseTime(int time) {
        this.time += time;
    }

    @Override
    public void decreaseTime(int time) {
        this.time -= time;
    }

    @Override
    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    @Override
    public int getTime() {
        return time;
    }

    public String getID() {
        return ID;
    }
}
