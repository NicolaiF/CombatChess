package controller;

import model.Board;
import model.pieces.ChessPiece;
import model.Player;
import java.util.ArrayList;

public class ChessBoardController {

    Board board;

    /**
     * @param board Reference to the chess board
     */
    public ChessBoardController(Board board){
        this.board = board;
    }

    /**
     * @param player The player trying to move a piece
     * @param chessPiece The chess piece to be moved
     * @param newPos The new position for this chessPiece
     * @return true if the piece was moved, else false
     */
    public boolean movePiece(Player player, ChessPiece chessPiece, String newPos){
        return false;
    }

    /**
     * @param pos The position of the piece checked for legal moves
     * @param player The player interacting
     */
    public void showLegalMoves(String pos, Player player){
        // Showing legal moves if player owns this chess piece
        if(player.ownsPiece((ChessPiece) board.getTile(pos).getPiece())){
            ArrayList<String> legalMoves = board.getLegalMoves(pos);
            System.out.println(legalMoves);
        }

    }
}
