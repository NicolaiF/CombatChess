package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import controller.ChessBoardController;
import main.R;
import model.Board;
import model.ImageButton;
import sheep.game.*;
import sheep.graphics.*;
import sheep.gui.*;

public class MenuState extends State {
    private Sprite background;
    private Image backgroundImage;
    private int screenWidth;
    private int screenHeight;
    private int leftMargin = 100;
    private int topMargin = 100;
    private GameState savedGameState;

    private Container buttonContainer;
    private ImageButton buttonNewGame;
    private ImageButton buttonContinueGame;

    public MenuState(Game game){
        setGame(game);
        backgroundImage = new Image(R.drawable.background_1);
        background = new Sprite(backgroundImage);

        // Getting the size of the screen
        WindowManager wm = (WindowManager) getGame().getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // Adjusting the background
        background.setScale(screenWidth/backgroundImage.getWidth(), screenWidth/backgroundImage.getWidth());
        background.setOffset(0, 0);
        background.setPosition(0, 0);

        createButtons();
    }

    private void createButtons() {

        buttonContainer = new Container();

        Image imageNewGame = new Image(R.drawable.button_newgame);
        Image imageContinueGame = new Image(R.drawable.button_continue);

        buttonNewGame = new ImageButton(imageNewGame, 0, topMargin*15/10, screenWidth/(imageNewGame.getWidth()*15/10)){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                Log.d("Debug", "New button clicked: " + getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()));
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    savedGameState = new GameState(getGame(), new ChessBoardController(new Board()));
                    getGame().pushState(savedGameState);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onTouchUp(MotionEvent motionEvent){
                return true;
            }
        };
        buttonContinueGame = new ImageButton(imageContinueGame, 0, buttonNewGame.getY() + buttonNewGame.getHeight() + 20, screenWidth/(imageContinueGame.getWidth()*15/10)){

            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                Log.d("Debug", "Continue button clicked: " + getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()));
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()) && savedGameState != null) {
                    getGame().pushState(savedGameState);
                    return true;
                } else {
                    return false;
                }
            }
        };

        buttonContainer.addWidget(buttonNewGame);
        buttonContainer.addWidget(buttonContinueGame);
    }

    @Override
    public boolean onTouchDown(MotionEvent motionEvent){
        buttonContainer.onTouchDown(motionEvent);
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        background.update(0);
        background.draw(canvas);
        buttonNewGame.draw(canvas);

        // Drawing continue game button if there exist a game to be continued
        if(savedGameState != null){
            buttonContinueGame.draw(canvas);
        }

    }
}
