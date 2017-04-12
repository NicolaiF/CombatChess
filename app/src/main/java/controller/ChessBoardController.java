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

    /** Sets if a tile is highlighted
     * @param tilePos positions of the tiles to be changed
     * @param isHighlighted true is highlighted, else false
     */
    public void setHighlightedOnTiles(ArrayList<String> tilePos, boolean isHighlighted){
        for (String pos:tilePos) {
            board.getTile(pos).setHighlighted(isHighlighted);
        }
    }

    /** Returns legal moves for a piece at a specific location
     * @param whiteTurn true if it's white players turn
     * @param index position in the chess board
     * @return A list of legal moves
     */
    public ArrayList<String> getLegalMoves(boolean whiteTurn, int[] index){
        String pos = board.convertIntsToPos(index[0], index[1]);
        String pieceColor;
        if(whiteTurn)
            pieceColor = "white";
        else pieceColor = "black";
        // Checks if the players owns this piece, and returns legal moves if it does
        if(pieceColor.equals(board.getTile(pos).getPiece().getColor())){
            return board.getLegalMoves(pos);
        }
        return new ArrayList<>();
    }
    /**
     * @param oldIndex The old position for the chess piece
     * @param newIndex The new position for this chess piece
     * @return true if the piece was moved, else false
     */
    public boolean movePiece(ArrayList<String> legalMoves ,int[] oldIndex, int[] newIndex) {
        String oldPos = board.convertIntsToPos(oldIndex[0], oldIndex[1]);
        String newPos = board.convertIntsToPos(newIndex[0], newIndex[1]);

        if (legalMoves.contains(newPos)) {
            ChessPiece chessPiece = board.getTile(oldPos).removePiece();
            board.getTile(newPos).setPiece(chessPiece);

            if (chessPiece instanceof Pawn) {
                Pawn pawn = (Pawn) chessPiece;
                pawn.moved();
            }
            return true;
        }
        return false;
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

    /** Returns the tile in this position
     * @param row vertical index
     * @param column horizontal index
     * @return the tile in this position
     */
    public Tile getTile(int row, int column) {
        return board.getTile(row, column);
    }

    /**
     * @param row vertical index
     * @param column horizontal index
     * @return true if the tile is highlighted, else false
     */
    public boolean isTileHighlighted(int row, int column) {
        return getTile(row, column).isHighlighted();
    }

    public int[] convertPosToInts(String pos){
        return board.convertPosToInts(pos);
    }
}
