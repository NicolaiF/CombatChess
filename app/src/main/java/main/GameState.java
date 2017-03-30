package main;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.game.World;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.graphics.Image;
import sheep.input.TouchListener;
import sheep.math.Vector2;

public class GameState extends State {

    private Image whitePawn = new Image(R.drawable.white_pawn);
    private Sprite pawnSprite;
    private int screenWidth = 1080;
    private int screenHeight = 1920;


    public GameState(){
        pawnSprite = new Sprite(whitePawn);
        //pawnSprite.setScale(0.5f, 0.5f);
        pawnSprite.setPosition(0, 0);


    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        pawnSprite.draw(canvas);
    }

    public void update(float dt){
        pawnSprite.setPosition(pawnSprite.getX()+1, pawnSprite.getY()+1);
        pawnSprite.update(dt);
    }
    /*
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    */
}
