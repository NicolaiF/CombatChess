package view;

import controller.ChessBoardController;
import main.R;
import model.Board;
import model.ImageButton;
import model.factories.ClassicFillFactory;
import model.factories.ClassicWoodFactory;
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

import java.util.ArrayList;

import sheep.graphics.Image;
import sheep.gui.Container;

public class GameState extends State {

    private ChessBoardController controller;
    private Container buttonContainer;
    private Sprite chessBoard;
    private Sprite table;

    private ChessPiece piece;
    private int[] posSelectedPiece;
    private ArrayList<String> legalMoves;

    private ImageButton buttonClassicFillStyle;
    private ImageButton buttonClassicWoodStyle;
    private ImageButton buttonBack;
    private ImageButton buttonSettings;

    private boolean whiteTurn;

    private int screenWidth;
    private int screenHeight;
    private int leftMargin = 100;
    private int topMargin = 100;

    private float pieceWidth;
    private float scale;


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

        // Creating sprites
        createSprites();

        // Creating buttons
        createButtons();
    }

    private void createSprites() {
        // Setting the chess board sprite
        Image boardImage = new Image(R.drawable.wood_board);
        chessBoard = new Sprite(boardImage);
        scale = screenWidth/boardImage.getWidth();
        chessBoard.setScale(scale, scale);
        chessBoard.setOffset(0, 0);
        chessBoard.setPosition(0, (screenHeight-screenWidth)/2);

        // Setting the table sprite
        Image tableImage = new Image(R.drawable.wood_texture);
        float tableScale = screenWidth/tableImage.getWidth();
        table = new Sprite(tableImage);
        table.setScale(tableScale,tableScale);
        table.setOffset(0, 0);
        // Placing the table in the middle under the chess board
        table.setPosition(0, chessBoard.getY() - (tableImage.getHeight()*tableScale - boardImage.getHeight()*scale)/2);

        // Scaled width of the chess pieces
        pieceWidth = new Image(R.drawable.classic_fill_black_pawn).getWidth() * scale;
    }

    private void createButtons() {
        buttonContainer = new Container();

        Image imageClassicFillStyle = new Image(R.drawable.classic_fill_white_pawn);
        Image imageClassicWoodStyle = new Image(R.drawable.classic_wood_white_pawn);
        Image imageBack = new Image(R.drawable.white_back);
        Image imageSettings = new Image(R.drawable.white_gear);

        float scaleButtons = screenWidth/(imageClassicFillStyle.getWidth() * 8);

        buttonClassicFillStyle = new ImageButton(imageClassicFillStyle, leftMargin, topMargin, screenWidth / (imageClassicFillStyle.getWidth() * 8)) {
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                Log.d("Debug", "Classic fill style button clicked: " + getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()));
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    controller.setPieceFactory(new ClassicFillFactory());
                    return true;
                } else {
                    return false;
                }
            }
        };
        buttonClassicWoodStyle = new ImageButton(imageClassicWoodStyle, buttonClassicFillStyle.getX() + buttonClassicFillStyle.getWidth(), topMargin, screenWidth / (imageClassicFillStyle.getWidth() * 8)) {
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                Log.d("Debug", "Classic wood style button clicked: " + getBoundingBox().contains(motionEvent.getX(), motionEvent.getY()));
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    controller.setPieceFactory(new ClassicWoodFactory());
                    return true;
                } else {
                    return false;
                }
            }
        };
        buttonBack = new ImageButton(imageBack, 0, (int) (table.getY() - imageBack.getHeight()*scaleButtons), scaleButtons) {
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
        buttonSettings = new ImageButton(imageSettings, (int) (screenWidth -  imageSettings.getWidth()*scaleButtons), (int) (table.getY() - imageSettings.getHeight()*scaleButtons), scaleButtons) {
            @Override
            public boolean onTouchDown(MotionEvent motionEvent) {
                if (getBoundingBox().contains(motionEvent.getX(), motionEvent.getY())) {
                    getGame().pushState(new SettingState(getGame()));
                    return true;
                } else {
                    return false;
                }
            }
        };

        buttonContainer.addWidget(buttonClassicFillStyle);
        buttonContainer.addWidget(buttonClassicWoodStyle);
        buttonContainer.addWidget(buttonBack);
        buttonContainer.addWidget(buttonSettings);
    }

    @Override
    public boolean onTouchDown(MotionEvent motionEvent) {

        // Checking if a button is clicked
        buttonContainer.onTouchDown(motionEvent);

        int column = (int) (motionEvent.getX()/pieceWidth);
        int row = (int) ((motionEvent.getY()-(screenHeight-screenWidth)/2)/pieceWidth);

        Log.d("Clicked : ", "column: " + column);
        Log.d("Clicked : ", "row: " + row);
        Log.d("Clicked : ", "SelectedPiecePos: " + posSelectedPiece);

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
                // Move was successful other players turn
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

    private boolean onChessPieceSelected(int column, int row) {
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

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.DKGRAY);

        // Drawing buttons
        buttonContainer.draw(canvas);

        // Drawing sprites
        table.update(0);
        chessBoard.update(0);
        table.draw(canvas);
        chessBoard.draw(canvas);

        for (int row = 0; row < 8; row++) {

            for (int column = 0; column < 8; column++) {

                if(controller.isTileHighlighted(row, column)){
                    Sprite sprite = controller.getTile(row, column).getHighlightSprite();
                    sprite.setPosition(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth );
                    sprite.setScale(scale, scale);
                    sprite.setOffset(0, 0);
                    sprite.update(0);
                    sprite.draw(canvas);
                }

                if(controller.hasPiece(row, column)){
                    Sprite sprite = controller.getPiece(row, column).getSprite();
                    sprite.setPosition(column * pieceWidth, (screenHeight-screenWidth)/2 + row * pieceWidth );
                    sprite.setScale(scale, scale);
                    sprite.setOffset(0,0);
                    sprite.update(0);
                    sprite.draw(canvas);
                }
            }
        }
    }

    public void setController(ChessBoardController controller){
        this.controller = controller;
    }
}
