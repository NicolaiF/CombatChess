package model;

import android.widget.ImageView;
import interfaces.*;
import model.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private Tile[][] board = new Tile[8][8];
    private String[] colors = new String[2];
    private AbstractPieceFactory pieceFactory = new ClassicPieceFactory();
    private Map<String, String> positionToIntsDictionary = new HashMap<>();
    private Map<String, String> intsToPositionDictionary = new HashMap<>();

    public Board(){
        // Initializing the colors for the board to be black and white
        colors[0] = "#000000";
        colors[1] = "#FFFFFF";

        generateBoard();
        placeStartingPieces();
        generatePositionToIntsDictionary();
        generateIntsToPositionDictionary();
    }

    /**
     * Generates a dictionary that converts indexes to a textual representation
     */
    private void generateIntsToPositionDictionary() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                intsToPositionDictionary.put(i + "," + j, String.valueOf(Character.toChars(65+j)) + Integer.toString(8-i));
            }
        }
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
    private void generatePositionToIntsDictionary() {
        for(int row = 0; row<8; row++){
            for(char c : "ABCDEFGH".toCharArray()){
                positionToIntsDictionary.put(c + Integer.toString(8-row),row + "," + ((int) c - 65));
            }
        }
    }

    /**
     * Generates a new Chess board with Tiles
     */
    private void generateBoard() {
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

    /** Finds all legal moves for the piece in this position
     * @param pos textual position on the board
     * @return A list of legal moves for this piece
     */
    public ArrayList<String> getLegalMoves(String pos) {
        ArrayList<String> legalMoves = new ArrayList<>();

        int[] index = convertPosToInts(pos);
        int row = index[0];
        int column = index[1];

        ChessPiece chessPiece = getTile(pos).getPiece();
        ArrayList<Move> moves = chessPiece.getLegalMoves();

        for(Move move : moves){
            int rowOffset = move.getRowOffset();
            int columnOffset = move.getColumnOffset();
            boolean continuous = move.isContinuous();

            if(continuous){
                //TODO: fix
            } else {
                Tile tile = getTile(row+rowOffset, column+columnOffset);
                // Checking if this tile is non existing or this contains an allied piece
                if(tile == null || tile.hasPiece() && tile.getPiece().getColor().equals(chessPiece.getColor())){
                    break;
                }
                else{
                    legalMoves.add(intsToPositionDictionary.get(Integer.toString(row+rowOffset )+ "," + Integer.toString(column+columnOffset)));
                }

            }
        }
        return legalMoves;
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

    /**
     * @param pos textual position in the board
     * @return the tile in this pos, null if none
     */
    public Tile getTile(String pos) {
        int[] index = convertPosToInts(pos);
        return board[index[0]][index[1]];
    }

    /**
     * @param row vertical index in board
     * @param column horizontal index in board
     * @return the tile in this pos, null if none
     */
    private Tile getTile(int row, int column){
        if(row < 0 || row > 7 || column < 0 || column > 7){
            return null;
        }
        return board[row][column];
    }

    public Tile[][] getBoard(){
        return  board;
    }

    /** Converts a textual position to indexes in board
     * @param pos A textual position in the chess board, i.e A8, A7, etc.
     * @return An integer array with indexes to the board
     */
    private int[] convertPosToInts(String pos){
        String value = positionToIntsDictionary.get(pos);

        // Returns null if this pos is not in the board
        if(value == null){
            return null;
        }

        int[] positions = new int[2];

        positions[0] = Integer.valueOf(value.split(",")[0]);
        positions[1] = Integer.valueOf(value.split(",")[1]);

        return positions;
    }

    @Override
    public String toString() {
        String string = "\n";
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                string += board[i][j].toString() + "\t";
            }
            string += "\n";
        }
        return string;
    }
}
