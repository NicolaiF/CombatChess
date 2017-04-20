package controller;

import interfaces.AbstractBoardFactory;
import interfaces.AbstractPieceFactory;
import model.Board;
import model.Tile;
import model.pieces.ChessPiece;
import sheep.game.Sprite;
import sheep.math.Vector2;

import java.util.ArrayList;

public class ChessBoardController {

    private Board board;
    private ChessPiece lastCapturedPiece;

    public ChessBoardController(Board board){
        this.board = board;
    }

    /** Sets if a tile is highlighted
     * @param tilePos positions of the tiles to be changed
     * @param isHighlighted true is highlighted, else false
     */
    public void setHighlightedOnTiles(ArrayList<String> tilePos, boolean isHighlighted){
        for (String pos:tilePos) {
            String[] index = pos.split(",");
            board.getTile(Integer.valueOf(index[0]), Integer.valueOf(index[1])).setHighlighted(isHighlighted);
        }
    }

    /** Returns legal moves for a piece at a specific location
     * @param whiteTurn true if it's white players turn
     * @param row vertical index
     * @param column horizontal index
     * @return A list of legal moves
     */
    public ArrayList<String> getLegalMoves(boolean whiteTurn, int row, int column){

        // Checks if the players owns this piece, and returns legal moves if it does
        if(whiteTurn == board.getTile(row, column).getPiece().isWhite()){
            return board.getLegalMoves(row, column);
        }
        return new ArrayList<>();
    }

    /** Moves a piece from one tile to another
     * @param oldRow vertical index
     * @param oldColumn horizontal index
     * @param newRow vertical index
     * @param newColumn horizontal index
     * @return true if the piece was moved, else false
     */
    public boolean movePiece(ArrayList<String> legalMoves, int oldRow, int oldColumn, int newRow, int newColumn) {

        String newPos = newRow + "," + newColumn;

        if (legalMoves.contains(newPos)) {
            ChessPiece chessPiece = board.getTile(oldRow, oldColumn).removePiece();

            // Updating last piece captured
            ChessPiece removedPiece = board.getTile(newRow, newColumn).removePiece();
            if(removedPiece != null){
                lastCapturedPiece = removedPiece;
            }

            board.setPiece(newRow, newColumn, chessPiece, false);
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
        return board.getTile(row, column).getPiece();
    }

    /** Returns true if this position contains piece
     * @param row vertical index
     * @param column horizontal index
     * @return true if this tile contains a piece
     */
    public boolean hasPiece(int row, int column) {
        return board.hasPiece(row, column);
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

    /** Sets a new piece factory
     * @param pieceFactory the new factory
     */
    public void setPieceFactory(AbstractPieceFactory pieceFactory){
        board.setPieceFactory(pieceFactory);
    }

    public void setBoardFactory(AbstractBoardFactory boardFactory){
        board.setBoardFactory(boardFactory);
    }

    public AbstractBoardFactory getBoardFactory(){
        return board.getBoardFactory();
    }

    public AbstractPieceFactory getPieceFactory(){
        return board.getPieceFactory();
    }

    public Sprite getBoardSprite(){
        return board.getBoardSprite();
    }

    public ChessPiece getLastCapturedPiece() {
        return lastCapturedPiece;
    }

    public void setBoardSprite(Sprite boardSprite) {
        board.setBoardSprite(boardSprite);
    }

    /** Adjust the sprite
     * @param sprite the sprite to be adjusted
     * @param scale the scale for this sprite
     * @param offset the offset for this sprite
     * @param position the position for this sprite
     */
    public void adjustSprite(Sprite sprite, Vector2 scale, Vector2 offset, Vector2 position){
        sprite.setScale(scale);
        sprite.setOffset(offset);
        sprite.setPosition(position);
    }
}
