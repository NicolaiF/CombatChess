package Model;

import Interfaces.*;
import java.util.ArrayList;

public class ChessBoard implements GameBoard {

    private ArrayList<ArrayList<Square>> board = new ArrayList<>();
    private String[] colors = new String[2];
    private AbstractPieceFactory pieceFactory = new ClassicPieceFactory();

    public ChessBoard(){
        // Initializing the colors for the board to be black and white
        colors[0] = "#000000";
        colors[1] = "#FFFFFF";

        generateBoard();
    }

    /**
     * Generates a new Chess board with Squares. Adds Chess ChessPiece for all squares containing a piece
     * in the beginning of the game.
     */
    @Override
    public void generateBoard() {

        for(int row = 0; row < 8; row++){
            board.add(new ArrayList<Square>());
            for(int column = 0; column < 8; column++){
                board.get(row).add(new Square(insertColor(row, column), insertChessPiece(row, column), insertPowerUp(row, column)));
            }
        }
    }

    /** Adds a chess piece in this position of the chess board. The method must find the correct square and add the
     * chess piece to the square
     * @param pos The position for the chess piece
     * @param chessPiece The chess piece to be added
     */
    @Override
    public void setPiece(String pos, ChessPiece chessPiece) {
        // TODO: Convert position to a index in board and add the chess piece to the square
    }

    /** Adds a power up in this position on the chess board. The method must find the correct square and add the power
     * up to the square
     * @param pos The position for the new power up
     * @param powerUp The power up to be added
     */
    @Override
    public void setPowerUp(String pos, PowerUp powerUp) {
        // TODO: Convert position to a index in board and add the power up to the square

    }

    @Override
    public boolean hasPiece(String pos) {
        // TODO: Convert pos to index in board and check if the square contains a Piece
        return false;
    }

    @Override
    public boolean hasPowerUp(String pos) {
        // TODO: Convert pos to index in board and check if the square contains a PowerUp
        return false;
    }

    /**
     * @param row The row in the chess board
     * @param column The column in the chess board
     * @return a PowerUp or null if none
     */
    private PowerUp insertPowerUp(int row, int column) {
        // TODO: Find a way to place the power ups
        return null;
    }

    /** Sets the correct Chess ChessPiece in this square
     * @param row The row in the chess board
     * @param column The column in the chess board
     * @return the correct Chess ChessPiece, null if none
     */
    private ChessPiece insertChessPiece(int row, int column) {
        if(row == 0){
            switch (column){
                case 0: return (ChessPiece) pieceFactory.createPiece("white_rook");
                case 1: return (ChessPiece) pieceFactory.createPiece("white_knight");
                case 2: return (ChessPiece) pieceFactory.createPiece("white_bishop");
                case 3: return (ChessPiece) pieceFactory.createPiece("white_queen");
                case 4: return (ChessPiece) pieceFactory.createPiece("white_king");
                case 5: return (ChessPiece) pieceFactory.createPiece("white_bishop");
                case 6: return (ChessPiece) pieceFactory.createPiece("white_knight");
                case 7: return (ChessPiece) pieceFactory.createPiece("white_rook");
            }
        }
        else if(row == 1){
            return (ChessPiece) pieceFactory.createPiece("white_pawn");
        }
        else if(row == 6){
            return (ChessPiece) pieceFactory.createPiece("black_pawn");
        }
        else if(row == 7){
            switch (column){
                case 0: return (ChessPiece) pieceFactory.createPiece("black_rook");
                case 1: return (ChessPiece) pieceFactory.createPiece("black_knight");
                case 2: return (ChessPiece) pieceFactory.createPiece("black_bishop");
                case 3: return (ChessPiece) pieceFactory.createPiece("black_queen");
                case 4: return (ChessPiece) pieceFactory.createPiece("black_king");
                case 5: return (ChessPiece) pieceFactory.createPiece("black_bishop");
                case 6: return (ChessPiece) pieceFactory.createPiece("black_knight");
                case 7: return (ChessPiece) pieceFactory.createPiece("black_rook");
            }
        }
        return null;
    }

    /**
     * @param row The row in the chess board
     * @param column The column in the chess board
     * @return the color for this square
     */
    private String insertColor(int row, int column) {
        return colors[(row+column)%2];
    }


    /** Sets the colors for the chess board. The strings should be hexadecimals #XXXXXX
     * @param firstColor The color for A8, C8, ...
     * @param secondColor The color for B8, D8, ...
     */
    public void setColors(String firstColor, String secondColor){
        colors[0] = firstColor;
        colors[1] = secondColor;
    }

    @Override
    public BoardSquare getSquare(String pos) {
        // TODO: Convert pos to index in board, and return correct square
        return null;
    }

    public ArrayList<ArrayList<Square>> getBoard(){
        return  board;
    }

    @Override
    public String toString(){
        String string = "";

        for(ArrayList<Square> row : board){
            for(int i = 0; i < 8; i++){
                string += " " + row.get(i).toString();
            }
            string += "\n";
        }
        return string;
    }
}
