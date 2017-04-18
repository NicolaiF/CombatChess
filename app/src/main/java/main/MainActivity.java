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

public class MainActivity extends Activity {
    private Game game;
    private MenuState menuState;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        game = new Game(this, null);
        menuState = new MenuState(game);
        game.pushState(menuState);
        setContentView(game);
    }

    /**
     * Directs the user to the main menu if playing a game
     * If pressed back in the menu, pause the app
     */
    @Override
    public void onBackPressed() {
        try{
            game.popState();

        } catch (EmptyStackException e){
            Log.d("Debug", e.toString());

            // Close the app
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
