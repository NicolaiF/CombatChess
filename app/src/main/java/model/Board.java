package model;

import interfaces.*;
import model.pieces.ChessPiece;
import model.pieces.King;
import model.pieces.Pawn;
import sheep.game.Sprite;
import sheep.graphics.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private Tile[][] board = new Tile[8][8];
    private String[] colors = new String[2];
    private AbstractPieceFactory pieceFactory = new ClassicPieceFactory();
    private Map<String, String> positionToIntsDictionary = new HashMap<>();
    private Map<String, String> intsToPositionDictionary = new HashMap<>();
    private String posWhiteKing;
    private String posBlackKing;
    private Sprite sprite;

    public Board(){
        // Initializing the colors for the board to be black and white
        colors[0] = "#000000";
        colors[1] = "#FFFFFF";

        // Starting position for kings
        posBlackKing = "E8";
        posWhiteKing = "E1";

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

    /**
     * Places pieces in the standard beginning position
     */
    private void placeStartingPieces() {
        // Adding pawns
        for(int i=0; i<8; i++){
            board[1][i].setPiece((ChessPiece) pieceFactory.createPawn(false));
            board[6][i].setPiece((ChessPiece) pieceFactory.createPawn(true));
        }

        // Adding rooks
        board[0][0].setPiece((ChessPiece) pieceFactory.createRook(false));
        board[0][7].setPiece((ChessPiece) pieceFactory.createRook(false));
        board[7][0].setPiece((ChessPiece) pieceFactory.createRook(true));
        board[7][7].setPiece((ChessPiece) pieceFactory.createRook(true));

        // Adding knights
        board[0][1].setPiece((ChessPiece) pieceFactory.createKnight(false));
        board[0][6].setPiece((ChessPiece) pieceFactory.createKnight(false));
        board[7][1].setPiece((ChessPiece) pieceFactory.createKnight(true));
        board[7][6].setPiece((ChessPiece) pieceFactory.createKnight(true));

        // Adding bishops
        board[0][2].setPiece((ChessPiece) pieceFactory.createBishop(false));
        board[0][5].setPiece((ChessPiece) pieceFactory.createBishop(false));
        board[7][2].setPiece((ChessPiece) pieceFactory.createBishop(true));
        board[7][5].setPiece((ChessPiece) pieceFactory.createBishop(true));

        // Adding queens
        board[0][3].setPiece((ChessPiece) pieceFactory.createQueen(false));
        board[7][3].setPiece((ChessPiece) pieceFactory.createQueen(true));

        // Adding kings
        board[0][4].setPiece((ChessPiece) pieceFactory.createKing(false));
        board[7][4].setPiece((ChessPiece) pieceFactory.createKing(true));
        posBlackKing = "E8";
        posWhiteKing = "E1";
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

    /**
     * @param pos Position of the tile
     * @return true if it has a piece, else false
     */
    public boolean hasPiece(String pos) {
        return getTile(pos).getPiece() != null;
    }

    /**
     * @param pos Position of the tile
     * @return true if it has a power up, else false
     */
    public boolean hasPowerUp(String pos) {
        return getTile(pos).getPowerUp() != null;
    }

    /** Finds all legal moves for the piece in this position
     * @param pos textual position on the board of the piece to be checked for legal moves
     * @return A list of legal moves for this piece
     */
    public ArrayList<String> getLegalMoves(String pos) {
        ArrayList<String> legalMoves = new ArrayList<>();
        int[] index = convertPosToInts(pos);

        int newRow;
        int newColumn;

        ChessPiece chessPiece = getTile(pos).getPiece();
        ArrayList<Move> moves = chessPiece.getLegalMoves();

        for(Move move : moves){
            newRow = index[0] + move.getRowOffset();
            newColumn = index[1] + move.getColumnOffset();

            if(move.isContinuous()){
                Tile tile = getTile(newRow, newColumn);

                while(tile != null){
                    // Checking if this tile contains a piece
                    if(tile.hasPiece()){
                        // Checking if this piece is an allied piece
                        if(tile.getPiece().getColor().equals(chessPiece.getColor())){
                            break;
                        } else {
                            // Simulate move and check if it is a legal state
                            if(isLegalMove(pos, newRow, newColumn)){
                                legalMoves.add(intsToPositionDictionary.get(Integer.toString(newRow)+ "," + Integer.toString(newColumn)));
                            }
                            break;
                        }
                    } else {
                        // Simulate move and check if it is a legal state
                        if(isLegalMove(pos, newRow, newColumn)){
                            legalMoves.add(intsToPositionDictionary.get(Integer.toString(newRow)+ "," + Integer.toString(newColumn)));
                        }
                        // Updating variables for next iteration
                        newRow += move.getRowOffset();
                        newColumn += move.getColumnOffset();
                        tile = getTile(newRow, newColumn);
                    }
                }
            } else {
                Tile tile = getTile(newRow, newColumn);
                // Checking if this tile exists
                if(tile != null){
                    // Checking if this tile contains a piece that is not an ally
                    if(tile.hasPiece()){
                        // Checking if this piece is an enemy piece
                        if(!tile.getPiece().getColor().equals(chessPiece.getColor())){
                            if(!(chessPiece instanceof Pawn)){
                                // Simulate move and check if it is a legal state
                                if(isLegalMove(pos, newRow, newColumn)){
                                    legalMoves.add(intsToPositionDictionary.get(Integer.toString(newRow)+ "," + Integer.toString(newColumn)));
                                }
                            }
                        }
                    } else {
                        // Simulate move and check if it is a legal state
                        if(isLegalMove(pos, newRow, newColumn)){
                            legalMoves.add(intsToPositionDictionary.get(Integer.toString(newRow)+ "," + Integer.toString(newColumn)));
                        }
                    }
                }
            }
        }

        // Special case for capturing moves
        moves = chessPiece.getCaptureMoves();
        for (Move move : moves) {
            newRow = index[0] + move.getRowOffset();
            newColumn = index[1] + move.getColumnOffset();

            Tile tile = getTile(newRow, newColumn);
            // Checking if this tile exists and contains enemy piece
            if (tile != null && tile.hasPiece() && !tile.getPiece().getColor().equals(chessPiece.getColor())) {
                legalMoves.add(intsToPositionDictionary.get(Integer.toString(newRow) + "," + Integer.toString(newColumn)));
            }
        }
        return legalMoves;
    }

    /** Simulating move to check if it's a legal move
     * @param oldPos old position for piece to be checked for legal moves
     * @param newRow new row for piece to be moved
     * @param newColumn new column for piece to be moved
     * @return
     */
    private boolean isLegalMove(String oldPos, int newRow, int newColumn) {
        String pieceColor = getTile(oldPos).getPiece().getColor();

        // Simulating move
        ChessPiece removedPiece = getTile(convertIntsToPos(newRow, newColumn)).removePiece();
        setPiece(convertIntsToPos(newRow, newColumn), getTile(oldPos).getPiece());
        setPiece(oldPos, null);

        // Updating position of the king
        if(getTile(newRow, newColumn).getPiece() instanceof King){
            if(getTile(newRow, newColumn).getPiece().getColor().equals("black")){
                posBlackKing = convertIntsToPos(newRow, newColumn);
            } else {
                posWhiteKing = convertIntsToPos(newRow, newColumn);
            }
        }

        boolean isLegal;

        // Checking if allied king is attacked after the move
        if(pieceColor.equals("black")){
            isLegal = !isKingAttacked(posBlackKing);
        } else {
            isLegal = !isKingAttacked(posWhiteKing);
        }
        // Resetting the board
        setPiece(oldPos, getTile(convertIntsToPos(newRow, newColumn)).removePiece());
        setPiece(convertIntsToPos(newRow, newColumn), removedPiece);

        // Resetting the position of the king if it was moved
        if(getTile(oldPos).getPiece() instanceof King){

            if(getTile(oldPos).getPiece().getColor().equals("black")){
                posBlackKing = oldPos;
            } else {
                posWhiteKing = oldPos;
            }
        }

        return isLegal;
    }

    /** Checking if king is under attack
     * @param kingPos position of the king
     * @return true if attacked, else false
     */
    private boolean isKingAttacked(String kingPos){
        int[] index = convertPosToInts(kingPos);
        int kingRow = index[0];
        int kingColumn = index[1];

        String kingColor = getTile(kingPos).getPiece().getColor();

        // Checking all tiles in the chess board
        for (int pieceRow = 0; pieceRow < 8; pieceRow++) {

            for (int pieceColumn = 0; pieceColumn < 8; pieceColumn++) {

                if (getTile(pieceRow, pieceColumn).hasPiece()) {
                    // If piece in this tile has different color than kingColor, check if it attacks the king
                    if (!kingColor.equals(getTile(pieceRow, pieceColumn).getPiece().getColor())) {

                        if(pieceAttacksKing(getTile(pieceRow, pieceColumn).getPiece(), pieceRow, pieceColumn, kingRow, kingColumn)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /** Checking if this piece attacks the opponents king
     * @param pieceRow The row of the piece to be checked if it attacks the king
     * @param pieceColumn The column of the piece to be checked if it attacks the king
     * @param piece The kind of piece in this position
     * @param kingRow The row for which the king is in
     * @param kingColumn The column for which the king is in
     * @return true if the piece attacks the king, else false
     */
    private boolean pieceAttacksKing(ChessPiece piece, int pieceRow, int pieceColumn, int kingRow, int kingColumn) {
        ArrayList<Move> legalMoves = piece.getLegalMoves();
        ArrayList<Move> captureMoves = piece.getCaptureMoves();

        if(!captureMoves.isEmpty()){
            for (Move move : captureMoves) {
                Tile tile = getTile(pieceRow+move.getRowOffset(), pieceColumn+move.getColumnOffset());

                if(tile != null && tile.hasPiece()) {

                        if (!tile.getPiece().getColor().equals(piece.getColor())) {

                            if (pieceRow + move.getRowOffset() == kingRow && pieceColumn + move.getColumnOffset() == kingColumn) {
                                // Piece attacks the king
                                return true;
                            }
                        }
                    }
                }
        } else {
            for (Move move : legalMoves) {
                int row = pieceRow + move.getRowOffset();
                int column = pieceColumn +  move.getColumnOffset();

                Tile tile = getTile(row, column);

                if(move.isContinuous()){

                    while (tile != null){

                        if(tile.hasPiece()){
                            // Piece attacks the king
                            if(row == kingRow && column == kingColumn){
                                return true;
                            } else {
                                break;
                            }
                        }
                        else {
                            if(row == kingRow && column == kingColumn){
                                // Piece attacks the king
                                return true;
                            }

                            // Updating for next iteration
                            row += move.getRowOffset();
                            column += move.getColumnOffset();
                            tile = getTile(row, column);
                        }
                    }

                } else if(tile != null && tile.hasPiece()){
                    if(!tile.getPiece().getColor().equals(piece.getColor())){

                        if(row == kingRow && column == kingColumn){
                            // Piece attacks the king
                            return true;
                        }
                    }
                }
            }
        }
        return  false;
    }

    /** Inserts color to the board
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
    public Tile getTile(int row, int column){
        if(row < 0 || row > 7 || column < 0 || column > 7){
            return null;
        }
        return board[row][column];
    }

    /**
     * @return the board
     */
    public Tile[][] getTiles(){
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

    /** Converts indexes to position
     * @param newRow row index
     * @param newColumn column index
     * @return textual position
     */
    public String convertIntsToPos(int newRow, int newColumn) {
        return intsToPositionDictionary.get(newRow + "," + newColumn);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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
