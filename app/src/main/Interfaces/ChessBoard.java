import java.util.ArrayList;

public interface ChessBoard {
    ArrayList<ArrayList<Square>> board = null;
    void generateBoard();
    Square getSquare(String pos);
 }
