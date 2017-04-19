package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import main.R;
import model.ImageButton;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.Container;

public class SettingState extends State {

    private Sprite spriteBackground;

    private Container buttonContainer;

    private int screenWidth;
    private int screenHeight;

    public SettingState(Game game){

        setGame(game);
        buttonContainer = new Container();

        // Getting the size of the screen
        WindowManager wm = (WindowManager) getGame().getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // Creating sprites
        createSprites();

        // Creating buttons
        createButtons();
    }

    private void createButtons() {

        Image imageContinue = new Image(R.drawable.button_continue);

        ImageButton buttonContinue = new ImageButton(imageContinue, 0, (int) (screenHeight*0.8), screenWidth/(imageContinue.getWidth()*15/10)){

            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                Log.d("Debug", "Continue button clicked: " + getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()));
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    getGame().popState();
                    return true;
                } else {
                    return false;
                }
            }
        };

        buttonContainer.addWidget(buttonContinue);
    }

    private void createSprites() {

        // Creating background sprite
        Image imageBackground = new Image(R.drawable.background_2);
        spriteBackground = new Sprite(imageBackground);
        spriteBackground.setOffset(0, 0);
        float backgroundScale = screenWidth/imageBackground.getWidth();
        spriteBackground.setScale(backgroundScale, backgroundScale);
    }

    @Override
    public void draw(Canvas canvas){
        spriteBackground.update(0);
        spriteBackground.draw(canvas);

        buttonContainer.draw(canvas);
    }

    @Override
    public boolean onTouchDown(MotionEvent motionEvent){
        return buttonContainer.onTouchDown(motionEvent);
    }
}
