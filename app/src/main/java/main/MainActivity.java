package main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import sheep.game.Game;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Game game = new Game(this, null);
        game.pushState(new GameState());

        setContentView(game);
    }

}
