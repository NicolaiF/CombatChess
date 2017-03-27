import java.util.ArrayList;

public interface Player {
    int PlayerID = 0;
    int Timer = 0;
    ArrayList<Square> SquareList = null;
    ArrayList<PowerUp> PowerUps = null;

    void addSquare(Square square);
    void addPowerUps(PowerUp powerUp);
    int getTimer();
    ArrayList<String> getPieces();
    boolean ownsSquare(Square square);
    void removeSquare(Square square);
    void usePowerUp(String type);
}
