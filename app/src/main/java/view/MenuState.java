package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import controller.ChessBoardController;
import main.R;
import model.Board;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;

public class MenuState extends State {

    private Sprite background;
    private Image backgroundImage;
    private int screenWidth;
    private int screenHeight;
    private float scale;

    public MenuState(Game game){
        setGame(game);
        backgroundImage = new Image(R.drawable.background_1);
        background = new Sprite(backgroundImage);

        // Setting size of the screen
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
    }

    @Override
    public boolean onTouchDown(MotionEvent motionEvent){
        getGame().popState();
        getGame().pushState(new GameState(getGame(), new ChessBoardController(new Board())));
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        background.update(0);
        background.draw(canvas);
    }
}
