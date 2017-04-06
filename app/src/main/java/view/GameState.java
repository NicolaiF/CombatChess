package view;

import controller.ChessBoardController;
import main.R;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import sheep.graphics.Image;

public class GameState extends State {

    private ChessBoardController controller;
    private Sprite chessBoard;
    private int screenWidth;
    private int screenHeight;
    private float pieceWidth;
    private float scale;


    public GameState(Game game, ChessBoardController controller){

        setGame(game);
        setController(controller);

        // Setting size of the screen
        WindowManager wm = (WindowManager) getGame().getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // Setting the chess board sprite
        Image boardImage = new Image(R.drawable.fill_board);
        chessBoard = new Sprite(boardImage);
        scale = screenWidth/boardImage.getWidth();
        chessBoard.setScale(scale, scale);
        chessBoard.setOffset(0, 0);
        chessBoard.setPosition(0, (screenHeight-screenWidth)/2);

        // Scaled width of the chess pieces
        pieceWidth = new Image(R.drawable.classic_fill_black_pawn).getWidth() * scale;
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
                    sprite.update(dt);
                }
            }
        }
    }

    public void setController(ChessBoardController controller){
        this.controller = controller;
    }
}
