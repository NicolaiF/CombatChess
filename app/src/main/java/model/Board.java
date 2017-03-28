package model;

import interfaces.*;
import model.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private Tile[][] board = new Tile[8][8];
    private String[] colors = new String[2];
    private AbstractPieceFactory pieceFactory = new ClassicPieceFactory();
    private Map<String, String> positionDictionary = new HashMap<>();

    public Board(){
        // Initializing the colors for the board to be black and white
        colors[0] = "#000000";
        colors[1] = "#FFFFFF";

        generateBoard();
        placeStartingPieces();
        generatePositionDictionary();
    }

    private void placeStartingPieces() {
        // Adding pawns
        for(int i=0; i<8; i++){
            board[1][i].setPiece((ChessPiece) pieceFactory.createPawn("black"));
            board[6][i].setPiece((ChessPiece) pieceFactory.createPawn("white"));
        }

        // Adding rooks
        board[0][0].setPiece((ChessPiece) pieceFactory.createRook("black"));
        board[0][7].setPiece((ChessPiece) pieceFactory.createRook("black"));
        board[7][0].setPiece((ChessPiece) pieceFactory.createRook("white"));
        board[7][7].setPiece((ChessPiece) pieceFactory.createRook("white"));

        // Adding knights
        board[0][1].setPiece((ChessPiece) pieceFactory.createKnight("black"));
        board[0][6].setPiece((ChessPiece) pieceFactory.createKnight("black"));
        board[7][1].setPiece((ChessPiece) pieceFactory.createKnight("white"));
        board[7][6].setPiece((ChessPiece) pieceFactory.createKnight("white"));

        // Adding bishops
        board[0][2].setPiece((ChessPiece) pieceFactory.createBishop("black"));
        board[0][5].setPiece((ChessPiece) pieceFactory.createBishop("black"));
        board[7][2].setPiece((ChessPiece) pieceFactory.createBishop("white"));
        board[7][5].setPiece((ChessPiece) pieceFactory.createBishop("white"));

        // Adding queens
        board[0][3].setPiece((ChessPiece) pieceFactory.createQueen("black"));
        board[7][3].setPiece((ChessPiece) pieceFactory.createQueen("white"));

        // Adding kings
        board[0][4].setPiece((ChessPiece) pieceFactory.createKing("black"));
        board[7][4].setPiece((ChessPiece) pieceFactory.createKing("white"));
    }

    /**
     * Generates a dictionary that converts a textual position to indexes in board
     */
    private void generatePositionDictionary() {
        for(int i = 0; i<8; i++){
            for(char c : "ABCDEFGH".toCharArray()){
                switch (c){
                    case 'A': positionDictionary.put("A" + Integer.toString(8-i),Integer.toString(i) + "," + "0");
                    case 'B': positionDictionary.put("B" + Integer.toString(8-i),Integer.toString(i) + "," + "1");
                    case 'C': positionDictionary.put("C" + Integer.toString(8-i),Integer.toString(i) + "," + "2");
                    case 'D': positionDictionary.put("D" + Integer.toString(8-i),Integer.toString(i) + "," + "3");
                    case 'E': positionDictionary.put("E" + Integer.toString(8-i),Integer.toString(i) + "," + "4");
                    case 'F': positionDictionary.put("F" + Integer.toString(8-i),Integer.toString(i) + "," + "5");
                    case 'G': positionDictionary.put("G" + Integer.toString(8-i),Integer.toString(i) + "," + "6");
                    case 'H': positionDictionary.put("H" + Integer.toString(8-i),Integer.toString(i) + "," + "7");
                }
            }
        }

    }

    /**
     * Generates a new Chess board with Tiles
     */
    public void generateBoard() {
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                board[i][j] = new Tile(insertColor(i,j));
            }
        }
    }

    /** Adds a chess piece in this position of the chess board. The method must find the correct tile and add the
     * chess piece to the tile
     * @param pos The position for the chess piece
     * @param chessPiece The chess piece to be added
     */
    public void setPiece(String pos, ChessPiece chessPiece) {
        getTile(pos).setPiece(chessPiece);
    }

    /** Adds a power up in this position on the chess board. The method must find the correct tile and add the power
     * up to the tile
     * @param pos The position for the new power up
     * @param powerUp The power up to be added
     */
    public void setPowerUp(String pos, PowerUp powerUp) {
        getTile(pos).setPowerUp(powerUp);
    }

    public boolean hasPiece(String pos) {
        return getTile(pos).getPiece() != null;
    }

    public boolean hasPowerUp(String pos) {
        return getTile(pos).getPowerUp() != null;
    }

    public ArrayList<String> getLegalMoves(String pos) {
        return null;
    }

    /** Calculates all legal moves for a specific chess piece in a specific position
     * @param chessPiece
     * @param row The row of this chess piece. row = 0 is 8 row in the graphical chess board. row = 7 is 1 in the graphical
     * @param column The column of this chess piece. column = 0 is A row in the graphical chess board. column = 7 is H in the graphical
     * @return A list of legal moves
     */
    private ArrayList<String> calculateLegalMoves(ChessPiece chessPiece, int row, int column) {
        return null;
    }

    /**
     * @param row The row in the chess board
     * @param column The column in the chess board
     * @return the color for this tile
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

    public Tile getTile(String pos) {
        return null;
    }

    public Tile[][] getBoard(){
        return  board;
    }

    /** Converts a textual position to indexes in board
     * @param pos A textual position in the chess board, i.e A8, A7, etc.
     * @return An integer array with indexes to the board
     */
    public int[] convertPosToInts(String pos){
        String value = positionDictionary.get(pos);
        int[] positions = new int[2];

        positions[0] = Integer.valueOf(value.split(",")[0]);
        positions[1] = Integer.valueOf(value.split(",")[1]);

        return positions;
    }

    /** Converts indexes to textual position
     * @param row
     * @param column
     * @return return textual position
     */
    public String convertIndexToPos(int row, int column){
        switch (column){
            case 0: return "A" + Integer.toString(8-row);
            case 1: return "B" + Integer.toString(8-row);
            case 2: return "C" + Integer.toString(8-row);
            case 3: return "D" + Integer.toString(8-row);
            case 4: return "E" + Integer.toString(8-row);
            case 5: return "F" + Integer.toString(8-row);
            case 6: return "G" + Integer.toString(8-row);
            case 7: return "H" + Integer.toString(8-row);
        }
        return "";
    }

    /** Returns the chess piece on this position
     * @param row The row in the board
     * @param column The column in the board
     * @return The chess piece on this position, null if none
     */
    public ChessPiece getPiece(int row, int column) {
        return null;
    }

    @Override
    public String toString() {
        String string = "";
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                string += board[i][j].toString() + "\t";
            }
            string += "\n";
        }
        return string;
    }
}
