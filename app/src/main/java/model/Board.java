package model;

import android.util.Log;

import interfaces.*;
import model.factories.boards.FillFactory;
import model.factories.pieces.ClassicFillFactory;
import model.pieces.Bishop;
import model.pieces.ChessPiece;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;
import sheep.game.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private Tile[][] board = new Tile[8][8];
    private String[] colors = new String[2];
    private AbstractPieceFactory pieceFactory = new ClassicFillFactory();
    private AbstractBoardFactory boardFactory = new FillFactory();
    private Map<String, String> intsToPositionDictionary = new HashMap<>();
    private int[] posWhiteKing;
    private int[] posBlackKing;
    private Sprite boardSprite;

    public Board(){

        posBlackKing = new int[2];
        posWhiteKing = new int[2];

        generateBoard();
        generateIntsToPositionDictionary();
        placeStartingPieces();

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
        posBlackKing[0] = 0;
        posBlackKing[1] = 4;
        posWhiteKing[0] = 7;
        posWhiteKing[1] = 4;
    }

    /**
     * Generates a new Chess board with Tiles
     */
    private void generateBoard() {
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                board[i][j] = new Tile();
            }
        }
    }

    /** Adds a power up in this position on the chess board. The method must find the correct tile and add the power
     * up to the tile
     * @param row vertical index in the board
     * @param column horizontal index in the board
     * @param powerUp The power up to be added
     */
    public void setPowerUp(int row, int column, PowerUp powerUp) {
        getTile(row, column).setPowerUp(powerUp);
    }

    /**
     * @param row vertical index in the board
     * @param column horizontal index in the board
     * @return true if it has a piece, else false
     */
    public boolean hasPiece(int row, int column) {
        Tile tile = getTile(row, column);
        if(tile != null){
            return tile.getPiece() != null;
        } else {
            return false;
        }
    }

    /**
     * @param row vertical index in the board
     * @param column horizontal index in the board
     * @return true if it has a power up, else false
     */
    public boolean hasPowerUp(int row, int column) {
        return getTile(row, column).getPowerUp() != null;
    }

    /** Finds all legal moves for the piece in this position
     * @param row vertical index in the board
     * @param column horizontal index in the board
     * @return A list of legal moves for this piece
     */
    public ArrayList<String> getLegalMoves(int row, int column) {
        Log.d("Debug", "White King pos: " + posWhiteKing[0] + "," + posWhiteKing[1]);
        Log.d("Debug", "Black King pos: " + posBlackKing[0] + "," + posBlackKing[1]);
        ArrayList<String> legalMoves = new ArrayList<>();
        ChessPiece chessPiece = getTile(row, column).getPiece();
        legalMoves.addAll(findAllLegalMoves(row, column, chessPiece));
        legalMoves.addAll(findAllCaptureMoves(row, column, chessPiece));

        if (chessPiece instanceof King) {
            // Checking if allied king is attacked after the move
            boolean isLegal = isAlliedKingAttacked(chessPiece.isWhite());
            if (!isLegal)
                return legalMoves;
            if (chessPiece.isWhite()) {
                legalMoves.addAll(findRocadeMoveForRook(7, 4, 2, 0, 3, true));
                legalMoves.addAll(findRocadeMoveForRook(7, 4, 6, 7, 5, true));
            }
            else
            {
                legalMoves.addAll(findRocadeMoveForRook(0, 4, 2, 0, 3, false));
                legalMoves.addAll(findRocadeMoveForRook(0, 4, 6, 7, 5, false));
            }
        }
        return legalMoves;
    }

    private ArrayList<String> findRocadeMoveForRook(int row, int kingColumn, int newKingColumn, int rookColumn, int newRookColumn, boolean isWhite) {
        ArrayList<String> legalMoves = new ArrayList<>();
        if (isRookUnmoved(row, rookColumn, isWhite)) {
            if (tilesQualifyForRocade(row, rookColumn, isWhite)) {
                if (isLegalMove(row, kingColumn, row, newKingColumn) && isLegalMove(row, rookColumn, row, newRookColumn))
                    legalMoves.add(row + "," + newKingColumn);
            }
        }
        return legalMoves;
    }

    private boolean tilesQualifyForRocade(int row, int rookColumn, boolean isWhite){
        if(rookColumn > 5) {
            for (int i = 5; i < 7; i++) {
                Tile tile = getTile(row, i);
                if (tileDoesNotQualfyForRocade(row, rookColumn, isWhite, tile)) return false;
            }
        }else {
            for (int i = 3; i > 1; i--) {
                Tile tile = getTile(row, i);
                if (tileDoesNotQualfyForRocade(row, rookColumn, isWhite, tile)) return false;
            }
        }
        return true;
    }

    private boolean tileDoesNotQualfyForRocade(int row, int column, boolean isWhite, Tile tile) {
        if(tile.hasPiece())
            return true;
        if(isTileAttacked(isWhite, row, column))
            return true;
        return false;
    }

    private boolean isRookUnmoved(int row, int column, boolean isWhite){
        Tile tile = getTile(row,column);
        ChessPiece piece = tile.getPiece();
        if(piece == null || !(piece instanceof Rook) || piece.isWhite() != isWhite)
            return false;
        Rook rook = (Rook) piece;
        if(rook.getIsMoved())
            return false;
        return true;
    }
    private ArrayList<String> findAllLegalMoves(int row, int column, ChessPiece chessPiece) {
        ArrayList<String> legalMoves = new ArrayList<>();
        ArrayList<Move> moves = chessPiece.getLegalMoves();
        for(Move move : moves) {
            int newRow = row + move.getRowOffset();
            int newColumn = column + move.getColumnOffset();
            Tile tile = getTile(newRow, newColumn);
            if (move.isContinuous())
                legalMoves.addAll(findAllContinuousLegalMoves(row, column, chessPiece, move, tile));
            else {
                // Continue if the tile does not exist
                if (tile == null) {
                    continue;
                }
                if (!tile.hasPiece()) {
                    // Simulate move and check if it is a legal state
                    if (isLegalMove(row, column, newRow, newColumn)) {
                        legalMoves.add(newRow + "," + newColumn);
                    }
                } else {
                    boolean isFriendlyPiece = tile.getPiece().isWhite() == chessPiece.isWhite();
                    //Continue if there is a friendly piece
                    if (isFriendlyPiece) continue;
                    //Return if the piece is pawn
                    if ((chessPiece instanceof Pawn)) return legalMoves;
                    // Simulate move and check if it is a legal state
                    if (isLegalMove(row, column, newRow, newColumn)) {
                        legalMoves.add(newRow + "," + newColumn);
                    }

                }
            }
        }
        return legalMoves;
    }

    private ArrayList<String> findAllContinuousLegalMoves(int row, int column, ChessPiece chessPiece, Move move, Tile tile) {
        ArrayList<String> legalMoves = new ArrayList<>();
        int newRow = row + move.getRowOffset();
        int newColumn = column + move.getColumnOffset();
        while(tile != null){
            // Checking if this tile contains a piece
            // Checking if this piece is an allied piece
            if(tile.hasPiece()) {
                if (tile.getPiece().isWhite() == chessPiece.isWhite())
                    break;
                // Simulate move and check if it is a legal state
                if (isLegalMove(row, column, newRow, newColumn)) {
                    legalMoves.add(newRow + "," + newColumn);
                }
                break;
            }
            else {
                // Simulate move and check if it is a legal state
                if(isLegalMove(row, column, newRow, newColumn)){
                    legalMoves.add(newRow + "," + newColumn);
                }
                // Updating variables for next iteration
                newRow += move.getRowOffset();
                newColumn += move.getColumnOffset();
                tile = getTile(newRow, newColumn);
            }
        }
        return legalMoves;
    }

    private ArrayList<String> findAllCaptureMoves(int row, int column, ChessPiece chessPiece) {
        ArrayList<String> legalMoves = new ArrayList<>();
        ArrayList<Move> moves = chessPiece.getCaptureMoves();
        for (Move move : moves) {
            int newRow = row + move.getRowOffset();
            int newColumn = column + move.getColumnOffset();

            Tile tile = getTile(newRow, newColumn);
            // Checking if this tile exists and contains enemy piece
            if (tile != null && tile.hasPiece() && tile.getPiece().isWhite() != chessPiece.isWhite()) {

                // Simulate move and check if it is a legal state
                if(isLegalMove(row, column, newRow, newColumn)){
                    legalMoves.add(newRow + "," + newColumn);
                }
            }
        }
        return legalMoves;
    }

    /** Simulating move to check if it's a legal move
     * @param oldRow vertical index in the board
     * @param oldColumn horizontal index in the board
     * @param newRow new row for piece to be moved
     * @param newColumn new column for piece to be moved
     * @return true if legal, else false
     */
    private boolean isLegalMove(int oldRow, int oldColumn, int newRow, int newColumn) {
        boolean isWhite = getTile(oldRow, oldColumn).getPiece().isWhite();

        // Simulating move
        ChessPiece removedPiece = getTile(newRow, newColumn).removePiece();
        setPiece(newRow, newColumn, getTile(oldRow, oldColumn).getPiece(), true);
        setPiece(oldRow, oldColumn, null, true);

        boolean isLegal;

        // Checking if allied king is attacked after the move
        isLegal = isAlliedKingAttacked(isWhite);
        // Resetting the board
        setPiece(oldRow, oldColumn, getTile(newRow, newColumn).removePiece(), true);
        setPiece(newRow, newColumn, removedPiece, true);

        return isLegal;
    }

    private boolean isAlliedKingAttacked(boolean isWhite) {
        boolean isLegal;
        if(isWhite){
            isLegal = !isTileAttacked(true, posWhiteKing[0], posWhiteKing[1]);
        } else {
            isLegal = !isTileAttacked(false, posBlackKing[0], posBlackKing[1]);
        }
        return isLegal;
    }

    /** Adds a chess piece in this position of the chess board. The method must find the correct tile and add the
     * chess piece to the tile
     * @param row vertical index in the board
     * @param column horizontal index in the board
     * @param chessPiece The chess piece to be added
     * @param simulation Differs behavior whether it is a move or a simulation
     */
    public void setPiece(int row, int column, ChessPiece chessPiece, boolean simulation) {
        if(!simulation){
            if(chessPiece instanceof Pawn){
                if(chessPiece.isWhite() && row == 0)
                    chessPiece = (ChessPiece) pieceFactory.createQueen(true);
                if(!chessPiece.isWhite() && row == 7)
                    chessPiece = (ChessPiece) pieceFactory.createQueen(false);
            }
            //Do rocade check
            if(chessPiece instanceof King){
                King king = (King) chessPiece;
                if(!king.getIsMoved()){
                    if(column == 2){
                        Tile tile = getTile(row, 0);
                        ChessPiece rook = tile.getPiece();
                        tile.removePiece();
                        getTile(row,3).setPiece(rook);
                    }
                    if(column == 6){
                        Tile tile = getTile(row, 7);
                        ChessPiece rook = tile.getPiece();
                        tile.removePiece();
                        getTile(row,5).setPiece(rook);
                    }
                }
            }
            chessPiece.moved();
        }
        getTile(row, column).setPiece(chessPiece);

        // Updating position of the king
        if(chessPiece instanceof King){
            if(chessPiece.isWhite()){
                Log.d("Debug", "White king moved");
                posWhiteKing[0] = row;
                posWhiteKing[1] = column;
                Log.d("Debug", "White king pos: " + posWhiteKing[0] + "," + posWhiteKing[1]);
            } else {
                posBlackKing[0] = row;
                posBlackKing[1] = column;
            }
        }
    }

    /** Checking if king is under attack
     * @param tileRow
     * @param tileColumn
     * @return true if attacked, else false
     */
    private boolean isTileAttacked(boolean isWhite, int tileRow, int tileColumn){

        // Checking all tiles in the chess board
        for (int pieceRow = 0; pieceRow < 8; pieceRow++) {

            for (int pieceColumn = 0; pieceColumn < 8; pieceColumn++) {

                if (getTile(pieceRow, pieceColumn).hasPiece()) {
                    // If piece in this tile has different color than tileColor, check if it attacks the tile
                    if (isWhite != getTile(pieceRow, pieceColumn).getPiece().isWhite()) {

                        if(pieceAttacksTile(pieceRow, pieceColumn, tileRow, tileColumn)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /** Checking if this state is check mate
     * @param whiteMoved the color of the piece that was last moved
     * @return true if check mate, else false
     */
    public boolean isCheckMate(boolean whiteMoved){

        boolean isCheckMate = true;

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {
                Tile tile = getTile(row, column);
                // Checking if this tile contains a piece
                if(tile.hasPiece()){
                    boolean isPieceWhite = tile.getPiece().isWhite();
                    // Checking if this piece has different color than the piece that was last moved
                    if(whiteMoved != isPieceWhite){

                        if(!getLegalMoves(row, column).isEmpty()){
                            // The opponent still has legal moves. I.e. no check mate
                            isCheckMate = false;
                            break;
                        }
                    }
                }
            }
        }
        return isCheckMate;
    }

    /** Checking if this piece attacks the opponents king
     * @param pieceRow The row of the piece to be checked if it attacks the king
     * @param pieceColumn The column of the piece to be checked if it attacks the king
     * @param tileRow The row for which the king is in
     * @param tileColumn The column for which the king is in
     * @return true if the piece attacks the king, else false
     */
    private boolean pieceAttacksTile(int pieceRow, int pieceColumn, int tileRow, int tileColumn) {
        ChessPiece piece = getTile(pieceRow, pieceColumn).getPiece();
        ArrayList<Move> legalMoves = piece.getLegalMoves();
        ArrayList<Move> captureMoves = piece.getCaptureMoves();

        if(!captureMoves.isEmpty()){
            for (Move move : captureMoves) {
                Tile tile = getTile(pieceRow+move.getRowOffset(), pieceColumn+move.getColumnOffset());

                if(tile != null && tile.hasPiece()) {

                    // Checking if this tile contains a piece of the opposite color
                    if (tile.getPiece().isWhite() != piece.isWhite()) {

                        if (pieceRow + move.getRowOffset() == tileRow && pieceColumn + move.getColumnOffset() == tileColumn) {
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
                            if(row == tileRow && column == tileColumn){
                                return true;
                            } else {
                                break;
                            }
                        }
                        else {
                            if(row == tileRow && column == tileColumn){
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
                    // Checking if pieces have different colors
                    if(tile.getPiece().isWhite() != piece.isWhite()){

                        if(row == tileRow && column == tileColumn){
                            // Piece attacks the king
                            return true;
                        }
                    }
                }
            }
        }
        return  false;
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

    /** Changes the theme on the chess pieces
     * @param pieceFactory the new factory for piece styles
     */
    public void setPieceFactory(AbstractPieceFactory pieceFactory){
        this.pieceFactory = pieceFactory;

        for (Tile[] row : board) {

            for (Tile tile : row) {
                ChessPiece chessPiece = tile.getPiece();
                // Checks if this tile contains a piece
                if (chessPiece != null){
                    boolean isWhite = chessPiece.isWhite();
                    Sprite oldSprite = tile.getPiece().getSprite();

                    if(chessPiece instanceof Pawn){
                        chessPiece.setSprite(pieceFactory.createPawnSprite(isWhite));
                    } else if(chessPiece instanceof Knight){
                        chessPiece.setSprite(pieceFactory.createKnightSprite(isWhite));
                    } else if(chessPiece instanceof Bishop){
                        chessPiece.setSprite(pieceFactory.createBishopSprite(isWhite));
                    } else if(chessPiece instanceof Queen){
                        chessPiece.setSprite(pieceFactory.createQueenSprite(isWhite));
                    } else if(chessPiece instanceof King){
                        chessPiece.setSprite(pieceFactory.createKingSprite(isWhite));
                    } else if(chessPiece instanceof Rook){
                        chessPiece.setSprite(pieceFactory.createRookSprite(isWhite));
                    }

                    // Setting position and scaling new sprite
                    Sprite newSprite = chessPiece.getSprite();
                    newSprite.setOffset(0, 0);
                    newSprite.setPosition(oldSprite.getX(), oldSprite.getY());
                    newSprite.setScale(oldSprite.getScale());
                    newSprite.update(0);
                }
            }
        }
    }

    public void setBoardFactory(AbstractBoardFactory boardFactory) {
        this.boardFactory = boardFactory;
        Sprite oldSprite = boardSprite;

        // Setting position and scaling new sprite
        Sprite newSprite = boardFactory.createBoardSprite();
        newSprite.setOffset(0, 0);
        newSprite.setPosition(oldSprite.getX(), oldSprite.getY());
        newSprite.setScale(oldSprite.getScale());
        newSprite.update(0);

        boardSprite = newSprite;
    }

    public AbstractPieceFactory getPieceFactory() {
        return pieceFactory;
    }

    public AbstractBoardFactory getBoardFactory() {
        return boardFactory;
    }

    public Sprite getBoardSprite(){
        return boardSprite;
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

    public void setBoardSprite(Sprite boardSprite) {
        this.boardSprite = boardSprite;
    }
}
