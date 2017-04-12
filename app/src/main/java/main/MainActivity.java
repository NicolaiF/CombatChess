package main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import controller.ChessBoardController;
import model.Board;
import sheep.game.Game;
import view.GameState;
import view.MenuState;

public class MainActivity extends Activity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        game = new Game(this, null);
        game.pushState(new MenuState(game));

        setContentView(game);
    }

    @Override
    public void onBackPressed() {
        game.popState();
        game.pushState(new MenuState(game));
    }
}
