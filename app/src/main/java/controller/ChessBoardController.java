package controller;

import model.Board;
import model.Tile;
import model.pieces.ChessPiece;
import model.Player;
import model.pieces.Pawn;

import java.util.ArrayList;

public class ChessBoardController {

    private Board board;

    public ChessBoardController(Board board){
        this.board = board;
    }

    /**
     * @param oldIndex The old position for the chess piece
     * @param newIndex The new position for this chess piece
     * @return true if the piece was moved, else false
     */
    public boolean movePiece(boolean whiteTurn ,int[] oldIndex, int[] newIndex){
        String oldPos = board.convertIntsToPos(oldIndex[0],oldIndex[1]);
        String newPos = board.convertIntsToPos(newIndex[0],newIndex[1]);

        String pieceColor;
        if(whiteTurn){
            pieceColor = "white";
        } else {
            pieceColor = "black";
        }

        // Checks if the players owns this piece, and that it's a legal move
        if(pieceColor.equals(board.getTile(oldPos).getPiece().getColor())){
            if(board.getLegalMoves(oldPos).contains(newPos)){
                ChessPiece chessPiece = board.getTile(oldPos).removePiece();
                board.getTile(newPos).setPiece(chessPiece);

                if(chessPiece instanceof Pawn){
                    Pawn pawn = (Pawn) chessPiece;
                    pawn.moved();
                }

                return true;
            }
        }
        return false;
    }

    /**
     * @param pos The position of the piece checked for legal moves
     * @param player The player interacting
     */
    public void showLegalMoves(String pos, Player player){
        // Showing legal moves if player owns this chess piece
        if(player.ownsPiece(board.getTile(pos).getPiece())){
            ArrayList<String> legalMoves = board.getLegalMoves(pos);
            System.out.println("Legal moves for " + board.getTile(pos).getPiece() + " at " + pos + " :" + legalMoves);
        }
    }

    public Tile[][] getTiles(){
        return board.getTiles();
    }

    /** Returns the piece in this position. Null if none
     * @param row vertical index
     * @param column horizontal index
     * @return chess piece in this location
     */
    public ChessPiece getPiece(int row, int column) {
        String pos = board.convertIntsToPos(row, column);
        return board.getTile(pos).getPiece();
    }

    /** Returns true if this position contains piece
     * @param row vertical index
     * @param column horizontal index
     * @return true if this tile contains a piece
     */
    public boolean hasPiece(int row, int column) {
        return board.hasPiece(board.convertIntsToPos(row, column));
    }

    /** Returns true if this position contains a tile
     * @param row vertical index
     * @param column horizontal index
     * @return true if position has tile, else false
     */
    public boolean hasTile(int row, int column) {
        return board.getTile(row, column) != null;
    }
}
