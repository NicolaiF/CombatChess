package interfaces;

import sheep.game.Sprite;
import sheep.graphics.Image;

public interface AbstractBoardFactory {
    Sprite createBoardSprite();
    Image getSampleImage();
}
