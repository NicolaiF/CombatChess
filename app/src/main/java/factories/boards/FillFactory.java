package factories.boards;

import interfaces.AbstractBoardFactory;
import main.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class FillFactory implements AbstractBoardFactory{
    @Override
    public Sprite createBoardSprite() {
        return new Sprite(new Image(R.drawable.fill_board));
    }

    @Override
    public Image getSampleImage() {
        return new Image(R.drawable.fill_board);
    }
}
