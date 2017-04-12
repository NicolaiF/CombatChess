package view;

import controller.ChessBoardController;
import main.R;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import java.util.ArrayList;

import sheep.graphics.Image;
import sheep.input.TouchListener;

public class GameState extends State {

    private ChessBoardController controller;
    private Sprite chessBoard;
    private int[] posSelectedPiece;
    private int screenWidth;
    private int screenHeight;
    private float pieceWidth;
    private float scale;
    private boolean whiteTurn;
    private ArrayList<String> legalMoves;


    public GameState(Game game, final ChessBoardController controller){

        setGame(game);
        setController(controller);
        whiteTurn = true;

        // Setting size of the screen
        WindowManager wm = (WindowManager) getGame().getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // Setting the chess board sprite
        final Image boardImage = new Image(R.drawable.wood_board);
        chessBoard = new Sprite(boardImage);
        scale = screenWidth/boardImage.getWidth();
        chessBoard.setScale(scale, scale);
        chessBoard.setOffset(0, 0);
        chessBoard.setPosition(0, (screenHeight-screenWidth)/2);

        // Scaled width of the chess pieces
        pieceWidth = new Image(R.drawable.classic_fill_black_pawn).getWidth() * scale;

        // Adding touch listener
        addTouchListener(new TouchListener() {
            /**
             * @param motionEvent
             * @return true if something was changed, else false
             */
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                int column = (int) (motionEvent.getX()/pieceWidth);
                int row = (int) ((motionEvent.getY()-(screenHeight-screenWidth)/2)/pieceWidth);

                Log.d("Clicked : ", "column: " + column);
                Log.d("Clicked : ", "row: " + row);
                Log.d("Clicked : ", "SelectedPiecePos: " + posSelectedPiece);

                // A piece is not selected and a new piece is clicked. Set is as selected piece
                if(posSelectedPiece == null && controller.hasPiece(row, column)){
                    int[] index = new int[2];
                    index[0] = row;
                    index[1] = column;
                    posSelectedPiece = index;
                    legalMoves = controller.getLegalMoves(whiteTurn, posSelectedPiece);
                    controller.setHighlightedOnTiles(legalMoves, true);
                    return true;
                }
                // A piece is selected and a new tile is clicked. Try to move the piece
                if(posSelectedPiece != null && legalMoves != null && !legalMoves.isEmpty() && controller.hasTile(row, column)){
                    int[] newPos = new int[2];
                    newPos[0] = row;
                    newPos[1] = column;
                    if(controller.movePiece(legalMoves, posSelectedPiece, newPos)){
                        // Move was successful other players turn
                        whiteTurn = !whiteTurn;
                    }
                    // Remove highlighting on tiles
                    controller.setHighlightedOnTiles(legalMoves, false);
                    posSelectedPiece = null;
                    legalMoves = null;
                    return true;
                }
                // Illegal action was attempted. Remove highlighted on tiles and selected piece if any
                if(legalMoves != null){
                    controller.setHighlightedOnTiles(legalMoves, false);
                    legalMoves = null;
                }
                posSelectedPiece = null;
                return false;
            }

            @Override
            public boolean onTouchUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onTouchMove(MotionEvent motionEvent) {
                return false;
            }
        });
    }

    public void draw(Canvas canvas){
        chessBoard.draw(canvas);

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                // TODO: Fix bug with blinking highlight tiles in left corner. The error is somewhere in this class
                if(controller.isTileHighlighted(row, column)){
                    Sprite sprite = controller.getTile(row, column).getHighlightSprite();
                    sprite.setPosition(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth );
                    sprite.setScale(scale, scale);
                    sprite.setOffset(0,0);
                    sprite.draw(canvas);
                }

                if(controller.hasPiece(row, column)){
                    Sprite sprite = controller.getPiece(row, column).getSprite();
                    sprite.setPosition(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth );
                    sprite.setScale(scale, scale);
                    sprite.setOffset(0,0);
                    sprite.draw(canvas);
                }
            }
        }
    }

    public void update(float dt){
        chessBoard.update(dt);

        Log.d("Debug : ", "Legal Moves: " + legalMoves);

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                if(controller.isTileHighlighted(row, column)){
                    Sprite sprite = controller.getTile(row, column).getHighlightSprite();
                    sprite.update(dt);
                }

                if(controller.getTile(row, column).hasPiece()){
                    Sprite sprite = controller.getPiece(row, column).getSprite();
                    sprite.update(dt);
                }
            }
        }
    }

    public void setController(ChessBoardController controller){
        this.controller = controller;
    }
}
