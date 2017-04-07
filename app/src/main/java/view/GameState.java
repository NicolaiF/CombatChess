package view;

import controller.ChessBoardController;
import main.R;
import model.pieces.ChessPiece;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

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
        final Image boardImage = new Image(R.drawable.fill_board);
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

                // A piece is not selected and a new piece is clicked
                if(posSelectedPiece == null && controller.hasPiece(row, column)){
                    int[] index = new int[2];
                    index[0] = row;
                    index[1] = column;
                    posSelectedPiece = index;
                    return true;
                }

                // A piece is selected and a new tile is clicked. Try to move the piece
                if(posSelectedPiece != null && controller.hasTile(row, column)){
                    int[] newPos = new int[2];
                    newPos[0] = row;
                    newPos[1] = column;
                    if(controller.movePiece(whiteTurn, posSelectedPiece, newPos)){
                        // Move was successful other players turn
                        whiteTurn = !whiteTurn;
                    }
                    posSelectedPiece = null;
                    return true;
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

                if(controller.getTiles()[row][column].hasPiece()){
                    Sprite sprite = controller.getTiles()[row][column].getPiece().getSprite();
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

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                if(controller.getTiles()[row][column].hasPiece()){
                    Sprite sprite = controller.getTiles()[row][column].getPiece().getSprite();
                    sprite.setPosition(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth );
                    sprite.update(dt);
                }
            }
        }
    }

    public void setController(ChessBoardController controller){
        this.controller = controller;
    }
}
