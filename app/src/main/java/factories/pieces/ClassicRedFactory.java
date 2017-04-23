package factories.pieces;

import main.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class ClassicRedFactory extends PieceFactory {

	@Override
	public Sprite createPawnSprite(boolean isWhite) {
		if(isWhite ){
			return new Sprite(new Image(R.drawable.classic_red_white_pawn));
		} else {
			return new Sprite(new Image(R.drawable.classic_red_black_pawn));
		}
	}

	@Override
	public Sprite createRookSprite(boolean isWhite) {
		if(isWhite ){
			return new Sprite(new Image(R.drawable.classic_red_white_rook));
		} else {
			return new Sprite(new Image(R.drawable.classic_red_black_rook));
		}
	}

	@Override
	public Sprite createKnightSprite(boolean isWhite) {
		if(isWhite ){
			return new Sprite(new Image(R.drawable.classic_red_white_knight));
		} else {
			return new Sprite(new Image(R.drawable.classic_red_black_knight));
		}
	}

	@Override
	public Sprite createBishopSprite(boolean isWhite) {
		if(isWhite ){
			return new Sprite(new Image(R.drawable.classic_red_white_bishop));
		} else {
			return new Sprite(new Image(R.drawable.classic_red_black_bishop));
		}
	}

	@Override
	public Sprite createQueenSprite(boolean isWhite) {
		if(isWhite ){
			return new Sprite(new Image(R.drawable.classic_red_white_queen));
		} else {
			return new Sprite(new Image(R.drawable.classic_red_black_queen));
		}
	}

	@Override
	public Sprite createKingSprite(boolean isWhite) {
		if(isWhite ){
			return new Sprite(new Image(R.drawable.classic_red_white_king));
		} else {
			return new Sprite(new Image(R.drawable.classic_red_black_king));
		}
	}
}
