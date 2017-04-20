package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import java.util.ArrayList;

import controller.ChessBoardController;
import interfaces.AbstractBoardFactory;
import interfaces.AbstractPieceFactory;
import main.R;
import model.factories.boards.FillFactory;
import model.factories.boards.WoodFactory;
import model.factories.pieces.ClassicFillFactory;
import model.factories.pieces.ClassicWoodFactory;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.Container;
import sheep.math.Vector2;

public class SettingState extends State {

    private Sprite spriteBg;
    private Sprite whiteKing;
    private Sprite blackKing;
    private Sprite board;

    private ChessBoardController controller;
    private Container buttonContainer;

    private int screenWidth;
    private int screenHeight;

    private ArrayList<AbstractPieceFactory> pieceFactories;
    private ArrayList<AbstractBoardFactory> boardFactories;

    private int pieceIndex;
    private int boardIndex;

    private float buttonScale;

    public SettingState(Game game, ChessBoardController controller){

        setGame(game);
        buttonContainer = new Container();
        this.controller = controller;

        // Getting size of screen
        collectScreenData();

        // Creating factories
        createFactories();

        // Creating buttons
        createButtons();

        // Creating sprites
        createSprites();

    }

    private void createFactories() {
        pieceFactories = new ArrayList<>();
        boardFactories = new ArrayList<>();

        // Adding piece factories
        pieceFactories.add(new ClassicFillFactory());
        pieceFactories.add(new ClassicWoodFactory());

        // Adding board factories
        boardFactories.add(new FillFactory());
        boardFactories.add(new WoodFactory());

        // Getting the right index
        for (int i = 0; i < pieceFactories.size(); i++) {
            // Checking if this index matches current factory
            if(pieceFactories.get(i).getClass() == controller.getPieceFactory().getClass()){
                pieceIndex = i;
            }
        }
        for (int i = 0; i < boardFactories.size(); i++) {
            // Checking if this index matches current factory
            if(boardFactories.get(i).getClass() == controller.getBoardFactory().getClass()){
                boardIndex = i;
            }
        }
    }

    private void collectScreenData() {
        // Getting the size of the screen
        WindowManager wm = (WindowManager) getGame().getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    private void createButtons() {

        Image imageContinue = new Image(R.drawable.button_continue);
        Image imagePrev = new Image(R.drawable.left_arrow);
        Image imageNext = new Image(R.drawable.right_arrow);

        buttonScale = screenWidth/(imagePrev.getWidth()*8);

        ImageButton buttonContinue = new ImageButton(imageContinue, 0, (int) (screenHeight*0.8), screenWidth/(imageContinue.getWidth()*15/10)){

            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                Log.d("Debug", "Continue button clicked: " + getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()));
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    ((GameState) getGame().getPreviousState()).updateTimer();
                    getGame().popState();
                    return true;
                } else {
                    return false;
                }
            }
        };
        ImageButton buttonPrevPieceStyle = new ImageButton(imagePrev, (int) (0.1*(screenWidth- imagePrev.getWidth()*buttonScale)), (int) (screenHeight*0.3), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    //Updating index
                    pieceIndex = (pieceIndex-1) % pieceFactories.size();
                    if(pieceIndex < 0){
                        pieceIndex += pieceFactories.size();
                    }

                    // Setting new  factory for the pieces
                    AbstractPieceFactory pieceFactory = pieceFactories.get(pieceIndex);
                    controller.setPieceFactory(pieceFactory);

                    // Updating piece sprites
                    Sprite oldWKing = whiteKing;
                    whiteKing = pieceFactories.get(pieceIndex).createKingSprite(true);
                    adjustSprite(whiteKing, oldWKing.getScale(), oldWKing.getOffset(), oldWKing.getPosition());
                    whiteKing.update(0);

                    Sprite oldBBlack = blackKing;
                    blackKing = pieceFactories.get(pieceIndex).createKingSprite(false);
                    adjustSprite(blackKing, oldBBlack.getScale(), oldBBlack.getOffset(), oldBBlack.getPosition());
                    blackKing.update(0);

                    return true;
                }
                return false;
            }
        };
        ImageButton buttonPrevBoardStyle = new ImageButton(imagePrev, (int) (0.1*(screenWidth- imagePrev.getWidth()*buttonScale)), (int) (screenHeight*0.50), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    // Updating index
                    boardIndex = (boardIndex-1) % boardFactories.size();
                    if(boardIndex < 0){
                        boardIndex += boardFactories.size();
                    }

                    // Settings new factory for board
                    AbstractBoardFactory boardFactory = boardFactories.get(boardIndex);
                    controller.setBoardFactory(boardFactory);

                    // Updating board sprite
                    Sprite oldSprite = board;
                    board = boardFactories.get(boardIndex).createBoardSprite();
                    adjustSprite(board, oldSprite.getScale(), oldSprite.getOffset(), oldSprite.getPosition());
                    board.update(0);

                    return true;
                }
                return false;
            }
        };
        ImageButton buttonNextPieceStyle = new ImageButton(imageNext, (int) (0.9*(screenWidth- imageNext.getWidth()*buttonScale)), (int) (screenHeight*0.3), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    //Updating index
                    pieceIndex = (pieceIndex+1) % pieceFactories.size();

                    // Settings new factory for the pieces
                    AbstractPieceFactory pieceFactory = pieceFactories.get(pieceIndex);
                    controller.setPieceFactory(pieceFactory);

                    // Updating piece sprites
                    Sprite oldWKing = whiteKing;
                    whiteKing = pieceFactories.get(pieceIndex).createKingSprite(true);
                    adjustSprite(whiteKing, oldWKing.getScale(), oldWKing.getOffset(), oldWKing.getPosition());
                    whiteKing.update(0);

                    Sprite oldBBlack = blackKing;
                    blackKing = pieceFactories.get(pieceIndex).createKingSprite(false);
                    adjustSprite(blackKing, oldBBlack.getScale(), oldBBlack.getOffset(), oldBBlack.getPosition());
                    blackKing.update(0);

                    return true;
                }
                return false;
            }
        };
        ImageButton buttonNextBoardStyle = new ImageButton(imageNext, (int) (0.9*(screenWidth- imageNext.getWidth()*buttonScale)), (int) (screenHeight*0.50), buttonScale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                        // Updating index
                        boardIndex = (boardIndex+1) % boardFactories.size();

                        // Settings new factory for board
                        AbstractBoardFactory boardFactory = boardFactories.get(boardIndex);
                        controller.setBoardFactory(boardFactory);

                        // Updating board sprite
                        Sprite oldSprite = board;
                        board = boardFactories.get(boardIndex).createBoardSprite();
                        adjustSprite(board, oldSprite.getScale(), oldSprite.getOffset(), oldSprite.getPosition());
                        board.update(0);

                        return true;
                    }
                }
                return false;
            }
        };

        buttonContainer.addWidget(buttonContinue);
        buttonContainer.addWidget(buttonNextBoardStyle);
        buttonContainer.addWidget(buttonPrevBoardStyle);
        buttonContainer.addWidget(buttonNextPieceStyle);
        buttonContainer.addWidget(buttonPrevPieceStyle);
    }

    private void createSprites() {
        // Getting the images for the sprites
        Image imgBackground = new Image(R.drawable.background_2);
        Image imgBoard = boardFactories.get(boardIndex).getSampleImage();
        Image imgPiece = pieceFactories.get(pieceIndex).getSampleImage();

        // Getting the sprites
        spriteBg = new Sprite(imgBackground);
        board = boardFactories.get(boardIndex).createBoardSprite();
        whiteKing = pieceFactories.get(pieceIndex).createKingSprite(true);
        blackKing = pieceFactories.get(pieceIndex).createKingSprite(false);

        // Calculating the scales
        float bgScale = screenWidth/imgBackground.getWidth();
        float pieceScale = screenWidth/(imgPiece.getWidth()*8f);
        float boardScale = screenWidth/(imgBoard.getWidth()*2f);

        // Adjusting pieces
        adjustSprite(spriteBg, new Vector2(bgScale, bgScale), new Vector2(0,0), new Vector2(0,0));

        float xPosBoard = screenWidth*0.5f - imgBoard.getWidth()*boardScale/2f;
        float yPosBoard = screenHeight*0.5f - (imgBoard.getHeight()*boardScale - new Image(R.drawable.right_arrow).getHeight()*buttonScale)/2f;
        adjustSprite(board, new Vector2(boardScale, boardScale), new Vector2(0,0), new Vector2(xPosBoard, yPosBoard));

        float xPosWPawn = screenWidth*0.4f - imgPiece.getWidth()*pieceScale*0.5f;
        float yPosWPawn = screenHeight*0.3f;
        adjustSprite(whiteKing, new Vector2(pieceScale, pieceScale), new Vector2(0,0), new Vector2(xPosWPawn, yPosWPawn));

        float xPosBPawn = screenWidth*0.6f - imgPiece.getWidth()*pieceScale*0.5f;
        float yPosBPawn = screenHeight*0.3f;
        adjustSprite(blackKing, new Vector2(pieceScale, pieceScale), new Vector2(0,0), new Vector2(xPosBPawn, yPosBPawn));
    }

    /** Adjust the sprite
     * @param sprite the sprite to be adjusted
     * @param scale the scale for this sprite
     * @param offset the offset for this sprite
     * @param position the position for this sprite
     */
    private void adjustSprite(Sprite sprite, Vector2 scale, Vector2 offset, Vector2 position){
        sprite.setScale(scale);
        sprite.setOffset(offset);
        sprite.setPosition(position);
    }

    @Override
    public void draw(Canvas canvas){
        spriteBg.update(0);
        whiteKing.update(0);
        blackKing.update(0);
        board.update(0);

        spriteBg.draw(canvas);
        whiteKing.draw(canvas);
        blackKing.draw(canvas);
        board.draw(canvas);

        buttonContainer.draw(canvas);
    }

    @Override
    public boolean onTouchDown(MotionEvent motionEvent){
        return buttonContainer.onTouchDown(motionEvent);
    }
}
