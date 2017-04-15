package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import controller.ChessBoardController;
import main.R;
import model.Board;
import sheep.game.*;
import sheep.graphics.*;
import sheep.gui.*;

public class MenuState extends State {

    private Sprite background;
    private Image backgroundImage;
    private int screenWidth;
    private int screenHeight;
    private float scale;
    private GameState savedGameState;

    private Container buttonContainer;
    private TextButton buttonNewGame;
    private TextButton buttonContinueGame;

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

        // Setting the scale
        scale = screenWidth/backgroundImage.getWidth();

        // Adjusting the background
        background.setScale(scale, scale);
        background.setOffset(0, 0);
        background.setPosition(0, 0);

        createButtons();
    }

    private void createButtons() {

        buttonContainer = new Container();

        // Setting up font for the labels on the buttons
        Paint[] paint = new Paint[]{new Font(255, 255, 255, 100, Typeface.DEFAULT, Typeface.BOLD), new Font(255, 255, 255, 100, Typeface.DEFAULT, Typeface.BOLD)};

        buttonNewGame = new TextButton(100, 200, getGame().getResources().getString(R.string.newGame), paint) {
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    savedGameState = new GameState(getGame(), new ChessBoardController(new Board()));
                    getGame().pushState(savedGameState);
                    return true;
                } else {
                    return false;
                }
            }
        };
        buttonContinueGame = new TextButton(100, 400, getGame().getResources().getString(R.string.continueGame), paint) {
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()) && savedGameState != null) {
                    getGame().popState();
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
