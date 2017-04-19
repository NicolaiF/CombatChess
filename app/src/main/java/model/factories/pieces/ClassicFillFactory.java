package model.factories.pieces;

import main.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class ClassicFillFactory extends PieceFactory {


    @Override
    public Sprite createPawnSprite(boolean isWhite) {
        if(isWhite ){
            return new Sprite(new Image(R.drawable.classic_fill_white_pawn));
        } else {
            return new Sprite(new Image(R.drawable.classic_fill_black_pawn));
        }
    }

    @Override
    public Sprite createRookSprite(boolean isWhite) {
        if(isWhite ){
            return new Sprite(new Image(R.drawable.classic_fill_white_rook));
        } else {
            return new Sprite(new Image(R.drawable.classic_fill_black_rook));
        }
    }

    @Override
    public Sprite createKnightSprite(boolean isWhite) {
        if(isWhite ){
            return new Sprite(new Image(R.drawable.classic_fill_white_knight));
        } else {
            return new Sprite(new Image(R.drawable.classic_fill_black_knight));
        }
    }

    @Override
    public Sprite createBishopSprite(boolean isWhite) {
        if(isWhite ){
            return new Sprite(new Image(R.drawable.classic_fill_white_bishop));
        } else {
            return new Sprite(new Image(R.drawable.classic_fill_black_bishop));
        }
    }

    @Override
    public Sprite createQueenSprite(boolean isWhite) {
        if(isWhite ){
            return new Sprite(new Image(R.drawable.classic_fill_white_queen));
        } else {
            return new Sprite(new Image(R.drawable.classic_fill_black_queen));
        }
    }

    @Override
    public Sprite createKingSprite(boolean isWhite) {
        if(isWhite ){
            return new Sprite(new Image(R.drawable.classic_fill_white_king));
        } else {
            return new Sprite(new Image(R.drawable.classic_fill_black_king));
        }
    }
}
