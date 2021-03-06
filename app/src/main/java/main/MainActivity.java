package main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import sheep.game.Game;
import view.GameState;
import view.MenuState;

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
