package model.factories.boards;

import interfaces.AbstractBoardFactory;
import main.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class BlueBoard implements AbstractBoardFactory {
	@Override
	public Sprite createBoardSprite() {
		return new Sprite(new Image(R.drawable.blue_board));
	}

	@Override
	public Image getSampleImage() {
		return new Image(R.drawable.blue_board);
	}
}
