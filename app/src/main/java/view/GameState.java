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
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import java.util.ArrayList;

import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.Container;
import sheep.gui.TextButton;
import sheep.math.Vector2;
import sheep.util.Timer;

public class GameState extends State {

    private ChessBoardController controller;
    private Container buttonContainer;
    private Sprite table;

    private ChessPiece piece;
    private int[] posSelectedPiece;
    private ArrayList<String> legalMoves;

    private boolean whiteTurn;
    private boolean gameWon;

    private int screenWidth;
    private int screenHeight;
    private int leftMargin = 100;
    private int topMargin = 100;

    private float pieceWidth;
    private float boardHeight;
    private float tableHeight;
    private float pieceScale;

    private Timer timer;
    private float timeStep;
    private float whiteTime;
    private float blackTime;
    private TextButton txtWhiteTime;
    private TextButton txtBlackTime;

    private ArrayList<ChessPiece> whiteCaptures = new ArrayList<>();
    private ArrayList<ChessPiece> blackCaptures = new ArrayList<>();


    public GameState(Game game, ChessBoardController controller){

        screenHeight = Constants.SCREEN_HEIGHT;
        screenWidth = Constants.SCREEN_WIDTH;

        setGame(game);
        setController(controller);
        whiteTurn = true;

        // Creating sprites
        createSprites();

        // Creating timers
        createTimers();

        // Creating buttons
        createButtons();
    }

    private void createTimers() {
        timer = new Timer();

        timeStep = Constants.TIME_STEP;
        whiteTime = Constants.START_TIME;
        blackTime = Constants.START_TIME;

        Paint paint = new Paint();
        paint.setTextSize(pieceWidth/2);
        Font font = new Font(255, 255, 255, pieceWidth/2, Typeface.DEFAULT, Typeface.BOLD);
        txtBlackTime = new TextButton(screenWidth/2 - paint.measureText("0:00")/2, table.getY() - paint.measureText("X"), "", new Paint[]{font, font});

        float yPos = table.getY() + tableHeight + paint.measureText("X")*2;
        if(yPos + paint.measureText("X") > screenHeight){
            yPos = table.getY() + tableHeight;
        }
        txtWhiteTime = new TextButton(screenWidth/2 - paint.measureText("0:00")/2, yPos, "", new Paint[]{font, font});
    }

    private void createSprites() {
        // Getting the images for the sprites
        Image boardImage = controller.getBoardFactory().getSampleImage();
        Image tableImage = new Image(R.drawable.wood_texture);

        // Getting the sprites
        Sprite chessBoard = controller.getBoardFactory().createBoardSprite();
        table = new Sprite(tableImage);

        // Calculating the scales
        pieceScale = screenWidth/boardImage.getWidth();
        float tableScale = screenWidth/tableImage.getWidth();

        // Calculating widths and heights
        pieceWidth = new Image(R.drawable.classic_fill_black_pawn).getWidth() * pieceScale;
        boardHeight = boardImage.getHeight()*pieceScale;
        tableHeight = tableImage.getHeight()*tableScale;

        // Updating constants
        Constants.SCALE_PIECES = pieceScale;
        Constants.PIECE_HEIGHT = pieceWidth;
        Constants.PIECE_WIDTH = pieceWidth;


        // Adjusting the sprites
        controller.adjustSprite(chessBoard, new Vector2(pieceScale, pieceScale), new Vector2(0, 0), new Vector2(0, (float) (screenHeight-screenWidth)/2));
        controller.adjustSprite(table, new Vector2(tableScale, tableScale), new Vector2(0, 0), new Vector2(0 , chessBoard.getY() - (tableImage.getHeight()*tableScale - boardImage.getHeight()* pieceScale)/2));

        controller.setBoardSprite(chessBoard);
    }

    private void createButtons() {
        buttonContainer = new Container();

        Image imageBack = new Image(R.drawable.white_back);
        Image imageSettings = new Image(R.drawable.white_gear);

        float scaleButtons = screenWidth/(imageBack.getWidth() * 10);

        ImageButton buttonBack = new ImageButton(imageBack, 0, (int) (table.getY() - imageBack.getHeight()*scaleButtons), scaleButtons) {
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    getGame().popState();
                    return true;
                } else {
                    return false;
                }
            }
        };
        ImageButton buttonSettings = new ImageButton(imageSettings, (int) (screenWidth -  imageSettings.getWidth()*scaleButtons), (int) (table.getY() - imageSettings.getHeight()*scaleButtons), scaleButtons) {
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    getGame().pushState(new SettingState(getGame(), controller));
                    return true;
                } else {
                    return false;
                }
            }
        };

        buttonContainer.addWidget(buttonBack);
        buttonContainer.addWidget(buttonSettings);
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

    private boolean onChessPieceSelected(int column, int row) {
        // Updating position of selected piece
        int[] index = new int[2];
        index[0] = row;
        index[1] = column;
        posSelectedPiece = index;
        piece = controller.getPiece(row, column);

        //Remove legal moves on old legal moves
        if(legalMoves != null)
            controller.setHighlightedOnTiles(legalMoves, false);
        legalMoves = controller.getLegalMoves(whiteTurn, row, column);
        controller.setHighlightedOnTiles(legalMoves, true);
        return true;
    }

    private synchronized void drawCapturedPieces(Canvas canvas) {
        for (ChessPiece piece : blackCaptures) {
            piece.getSprite().draw(canvas);
        }

        for (ChessPiece piece : whiteCaptures) {
            piece.getSprite().draw(canvas);
        }
    }

    private void drawSprites(Canvas canvas) {
        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                // Drawing highlighted tiles
                if(controller.isTileHighlighted(row, column)){
                    Sprite sprite = controller.getTile(row, column).getHighlightSprite();
                    controller.adjustSprite(sprite, new Vector2(pieceScale, pieceScale), new Vector2(0,0), new Vector2(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth));
                    sprite.update(0);
                    sprite.draw(canvas);
                }

                // Drawing pieces
                if(controller.hasPiece(row, column)){
                    Sprite sprite = controller.getPiece(row, column).getSprite();
                    controller.adjustSprite(sprite, new Vector2(pieceScale, pieceScale), new Vector2(0,0), new Vector2(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth ));
                    sprite.update(0);
                    sprite.draw(canvas);
                }

                // Drawing power ups
                if(controller.hasPowerUp(row, column)){
                    Sprite sprite = controller.getPowerUp(row, column).getSprite();
                    controller.adjustSprite(sprite, new Vector2(pieceScale, pieceScale), new Vector2(0,0), new Vector2(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth ));
                    sprite.update(0);
                    sprite.draw(canvas);
                }

                // Drawing captured pieces
                ArrayList<ChessPiece> whiteCaptures = controller.getWhiteCaptures();
                ArrayList<ChessPiece> blackCaptures = controller.getBlackCaptures();
                for (int i = 0; i < whiteCaptures.size(); i++) {
                    whiteCaptures.get(i).getSprite().setPosition(i*pieceWidth/2 + screenWidth*0.05f, controller.getBoardSprite().getY() + boardHeight);
                    whiteCaptures.get(i).getSprite().update(0);
                    whiteCaptures.get(i).getSprite().draw(canvas);

                }
                for (int i = 0; i < controller.getBlackCaptures().size(); i++) {
                    blackCaptures.get(i).getSprite().setPosition(blackCaptures.size()*pieceWidth/2 - pieceWidth/2 + screenWidth*0.05f, controller.getBoardSprite().getY() - pieceWidth);
                    blackCaptures.get(i).getSprite().update(0);
                    blackCaptures.get(i).getSprite().draw(canvas);
                }
            }
        }
    }

    public void setController(ChessBoardController controller){
        this.controller = controller;
    }

    @Override
    public boolean onTouchDown(MotionEvent motionEvent) {

        // Checking if a button is clicked
        buttonContainer.onTouchDown(motionEvent);

        int column = (int) (motionEvent.getX()/pieceWidth);
        int row = (int) ((motionEvent.getY()-(screenHeight-screenWidth)/2)/pieceWidth);

        // A piece is not selected and a new piece is clicked. Set is as selected piece
        if(posSelectedPiece == null && controller.hasPiece(row, column)){
            return onChessPieceSelected(column, row);
        }
        //A piece is selected and a new piece of the same color is selected
        if(posSelectedPiece != null && controller.hasPiece(row,column) && controller.getPiece(row,column).isWhite() == piece.isWhite()){
            return onChessPieceSelected(column, row);
        }
        // A piece is selected and a new tile is clicked. Try to move the piece
        if(posSelectedPiece != null && legalMoves != null && !legalMoves.isEmpty() && controller.hasTile(row, column)){
            int[] newPos = new int[2];
            newPos[0] = row;
            newPos[1] = column;
            if(controller.movePiece(legalMoves, posSelectedPiece[0], posSelectedPiece[1], newPos[0], newPos[1])){

                // Checking for check mate
                if(controller.isCheckMate(whiteTurn)){
                    handleWin(whiteTurn);
                }

                // Move was successful other players turn
                if(controller.isTimerActivated()){
                    // Adding time to the timer
                    addTime(Constants.TIME_STEP);
                }
                whiteTurn = !whiteTurn;
            }
            // Remove highlighting on tiles
            controller.setHighlightedOnTiles(legalMoves, false);
            piece = null;
            posSelectedPiece = null;
            legalMoves = null;
            return true;
        }
        // Illegal action was attempted. Remove highlighted on tiles and selected piece if any
        if(legalMoves != null){
            controller.setHighlightedOnTiles(legalMoves, false);
            legalMoves = null;
        }
        piece = null;
        posSelectedPiece = null;
        return false;
    }

    /** Handles what happens when a player wins by check mate
     * @param whiteTurn true if white player won
     */
    private void handleWin(boolean whiteTurn) {
        // Creating a image that informs the winner
        Image imgWon;
        if(whiteTurn) { imgWon = new Image(R.drawable.victory_white);
        } else { imgWon = new Image(R.drawable.victory_black);}

        float scale = screenWidth/imgWon.getWidth()*1.5f;

        ImageButton btnWin = new ImageButton(imgWon, (int) (screenWidth/2 - imgWon.getWidth()*scale/2), (int) (screenHeight/2 - imgWon.getHeight()*scale/2), scale){
            @Override
            public boolean onTouchDown(MotionEvent motionEvent){
                if(getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())){
                    getGame().popState();
                    return true;
                }
                return false;
            }
        };
        buttonContainer.addWidget(btnWin);

        gameWon = true;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.DKGRAY);

        // Drawing sprites
        controller.getBoardSprite().update(0);
        table.update(0);
        table.draw(canvas);
        controller.getBoardSprite().draw(canvas);
        drawSprites(canvas);
        drawCapturedPieces(canvas);

        // Drawing buttons
        buttonContainer.draw(canvas);

        // Drawing timers
        if(controller.isTimerActivated()){
            updateTimeLabels();
            txtWhiteTime.draw(canvas);
            txtBlackTime.draw(canvas);
        }
    }

    public void addTime(float dt){
        if(whiteTurn){
            whiteTime += dt;
        } else {
            blackTime += dt;
        }
    }

    private void updateTimeLabels() {
        float dt = timer.getDelta();

        if(!gameWon){
            if (whiteTurn){
                whiteTime -= dt;
                if(whiteTime < 0){
                    handleWin(!whiteTurn);
                }
            }
            else {
                blackTime -= dt;
                if(blackTime < 0){
                    handleWin(whiteTurn);
                }
            }
        }

        String minWhite = Float.toString(whiteTime/60).split("\\.")[0];
        String secWhite = String.valueOf(Math.round(whiteTime) - 60*Integer.parseInt(minWhite));
        if(secWhite.equals("60")){ secWhite = "59";}
        if(secWhite.length() < 2)
            secWhite = "0" + secWhite;
        txtWhiteTime.setLabel(minWhite + ":" + secWhite);

        String minBlack = Float.toString(blackTime/60).split("\\.")[0];
        String secBlack = String.valueOf(Math.round(blackTime) - 60*Integer.parseInt(minBlack));
        if(secBlack.equals("60")){ secBlack = "59";}
        if(secBlack.length() < 2)
            secBlack = "0" + secBlack;
        txtBlackTime.setLabel(minBlack + ":" + secBlack);
    }

    /**
     * Use this method to update the timer. Such that the time doesn't go on as while in other states.
     * Should be used when leaving a state and pushing game state
     */
    public void updateTimer(){
        timer.getDelta();
    }
}
