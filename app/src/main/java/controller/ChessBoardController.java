package controller;

import model.Board;
import model.pieces.ChessPiece;
import model.Player;
import model.pieces.Pawn;

import java.util.ArrayList;

public class ChessBoardController {

    private Board board;

    /**
     * @param board Reference to the chess board
     */
    public ChessBoardController(Board board){
        this.board = board;
    }

    /**
     * @param oldPos The old position for the chess piece
     * @param newPos The new position for this chess piece
     * @return true if the piece was moved, else false
     */
    public boolean movePiece(Player player, String oldPos, String newPos){
        // Checks if the players owns this piece, and that it's a legal move
        if(player.ownsPiece(board.getTile(oldPos).getPiece())){
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
}
