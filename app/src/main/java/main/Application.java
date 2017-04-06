package main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import controller.ChessBoardController;
import model.Board;
import sheep.game.Game;
import view.GameState;

public class Application extends Activity {
    private Game game;
    private Board board;
    private ChessBoardController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        game = new Game(this, null);
        board = new Board();
        controller = new ChessBoardController(board);

        game.pushState(new GameState(game, controller));

        setContentView(game);
    }

    @Override
    public void onBackPressed() {
        game.popState();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
