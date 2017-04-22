package main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

import java.util.EmptyStackException;

import controller.ChessBoardController;
import model.Board;
import sheep.game.Game;
import sheep.game.State;
import view.GameState;
import view.MenuState;
import view.SettingState;

public class MainActivity extends Activity {
    private Game game;
    private MenuState menuState;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        game = new Game(this, null);
        menuState = new MenuState(game);
        setContentView(game);
    }

    @Override
    public void onResume(){
        super.onResume();
        game.pushState(menuState);
    }

    @Override
    public void onPause(){
        super.onPause();
        while (game.getPreviousState() != null){
            game.popState();
        }
    }

    /**
     * Directs the user to the main menu if playing a game
     * If pressed back in the menu, pause the app
     */
    @Override
    public void onBackPressed() {
        if(game.getPreviousState() instanceof GameState){
            ((GameState) game.getPreviousState()).updateTimer();
        }
        game.popState();
        if(game.getPreviousState() == null){
            super.onBackPressed();
        }
    }
}
