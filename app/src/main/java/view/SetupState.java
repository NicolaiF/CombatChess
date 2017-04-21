package view;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import controller.ChessBoardController;
import main.R;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.Container;
import sheep.math.Vector2;

public class SetupState extends State {

    private ChessBoardController controller;
    private GameState gameState;

    private Sprite bg;
    private Container buttonContainer;

    private int screenHeight;
    private int screenWidth;
    private float arrowHeight;

    private boolean combatChessOn;
    private boolean timeOn;

    private Sprite spriteCombatChessOn;
    private Sprite spriteCombatChessOff;
    private Sprite spriteNoTimer;
    private Sprite spriteTimer;

    public SetupState(ChessBoardController controller, GameState gameState){
        this.controller = controller;
        this.gameState = gameState;

        combatChessOn = false;

        screenHeight = Constants.SCREEN_HEIGHT;
        screenWidth = Constants.SCREEN_WIDTH;

        // Creating buttons
        createButtons();

        // Create images
        createImages();
    }

    private void createImages() {
        // Getting Images
        Image imgCombatChessOn = new Image(R.drawable.button_power_on);
        Image imgCombatChessOff = new Image(R.drawable.button_power_off);
        Image imgNoTimer = new Image(R.drawable.button_notimer);
        Image imgTimer = new Image(R.drawable.button_fifteen_ten);
        Image imgBg = new Image(R.drawable.background_1);

        // Calculating scales
        float scale = screenWidth/(imgTimer.getWidth()*2);
        float scaleBg = screenWidth/imgBg.getWidth();

        // Creating images
        spriteCombatChessOff = new Sprite(imgCombatChessOff);
        spriteCombatChessOn = new Sprite(imgCombatChessOn);
        spriteNoTimer = new Sprite(imgNoTimer);
        spriteTimer = new Sprite(imgTimer);
        bg = new Sprite(imgBg);

        // Adjusting images
        controller.adjustSprite(bg, new Vector2(scaleBg, scaleBg), new Vector2(0,0), new Vector2(0,0));
        controller.adjustSprite(spriteCombatChessOff, new Vector2(scale, scale), new Vector2(0,0), new Vector2(screenWidth/2 - imgCombatChessOff.getWidth()*scale/2, (float) (screenHeight * 0.3)  - imgCombatChessOff.getHeight()*scale/2 + arrowHeight/2));
        controller.adjustSprite(spriteCombatChessOn, new Vector2(scale, scale), new Vector2(0,0), new Vector2(screenWidth/2 - imgCombatChessOn.getWidth()*scale/2, (float) (screenHeight * 0.3)  - imgCombatChessOn.getHeight()*scale/2  + arrowHeight/2));
        controller.adjustSprite(spriteNoTimer, new Vector2(scale, scale), new Vector2(0,0), new Vector2(screenWidth/2 - imgNoTimer.getWidth()*scale/2, (float) (screenHeight*0.5) - imgNoTimer.getHeight()*scale/2  + arrowHeight/2));
        controller.adjustSprite(spriteTimer, new Vector2(scale, scale), new Vector2(0,0), new Vector2(screenWidth/2 - imgTimer.getWidth()*scale/2, (float) (screenHeight*0.5)  - imgTimer.getHeight()*scale/2  + arrowHeight/2));
    }

    private void createButtons() {
        buttonContainer = new Container();

        // Getting images
        Image imgStart = new Image(R.drawable.button_startgame);
        Image imgPrev = new Image(R.drawable.left_arrow);
        Image imgNext = new Image(R.drawable.right_arrow);

        // Calculating scales
        float buttonScale = screenWidth/(imgPrev.getWidth()*8);

        arrowHeight = imgNext.getHeight()*buttonScale;

        // Creating buttons
        ImageButton btnStart = new ImageButton(imgStart, 0, (int) (screenHeight*0.8), screenWidth/(imgStart.getWidth()*15/10)){

            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    getGame().popState();
                    getGame().pushState(gameState);
                    return true;
                } else {
                    return false;
                }
            }
        };
        ImageButton btnNextPowerUp = new ImageButton(imgNext, (int) (0.9*(screenWidth- imgPrev.getWidth()*buttonScale)), (int) (screenHeight*0.3), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    controller.setCombatChess(!combatChessOn);
                    combatChessOn = !combatChessOn;
                    return true;
                }
                return false;
            }
        };
        ImageButton btnPrevPowerUp = new ImageButton(imgPrev, (int) (0.1*(screenWidth- imgPrev.getWidth()*buttonScale)), (int) (screenHeight*0.3), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    controller.setCombatChess(!combatChessOn);
                    combatChessOn = !combatChessOn;
                    return true;
                }
                return false;
            }
        };
        ImageButton btnPrevTime = new ImageButton(imgPrev, (int) (0.1*(screenWidth- imgPrev.getWidth()*buttonScale)), (int) (screenHeight*0.5), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    controller.setTimerActivated(!timeOn);
                    timeOn = !timeOn;
                    return true;
                }
                return false;
            }
        };
        ImageButton btnNextTime = new ImageButton(imgNext, (int) (0.9*(screenWidth- imgNext.getWidth()*buttonScale)), (int) (screenHeight*0.5), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    controller.setTimerActivated(!timeOn);
                    timeOn = !timeOn;
                    return true;
                }
                return false;
            }
        };

        // Adding them to the container
        buttonContainer.addWidget(btnStart);
        buttonContainer.addWidget(btnPrevTime);
        buttonContainer.addWidget(btnNextTime);
        buttonContainer.addWidget(btnPrevPowerUp);
        buttonContainer.addWidget(btnNextPowerUp);
    }

    @Override
    public boolean onTouchDown(MotionEvent motionEvent){
        return buttonContainer.onTouchDown(motionEvent);
    }

    @Override
    public void draw(Canvas canvas){

        // Drawing images
        bg.draw(canvas);
        if(combatChessOn){
            spriteCombatChessOn.draw(canvas);
        } else {
            spriteCombatChessOff.draw(canvas);
        }

        if(timeOn){
            spriteTimer.draw(canvas);
        } else{
            spriteNoTimer.draw(canvas);
        }

        // Drawing buttons
        buttonContainer.draw(canvas);
    }

}
