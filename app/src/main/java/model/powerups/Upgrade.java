package model.powerups;

import android.animation.ValueAnimator;

import controller.ChessBoardController;
import interfaces.AbstractPieceFactory;
import interfaces.PowerUp;
import main.R;
import model.factories.pieces.PieceFactory;
import model.pieces.ChessPiece;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Upgrade implements PowerUp {

    private ValueAnimator va = new ValueAnimator();
    private Sprite sprite;

    public Upgrade(){
        initSprite();
        initValueAnimator();
    }

    private void initSprite() {
        sprite = new Sprite(new Image(R.drawable.power_rod_i));
    }

    private void initValueAnimator() {
        //Setting up the chopper animation
        va.setDuration(100);
        va.setIntValues(R.drawable.power_rod_i, R.drawable.power_rod_ii, R.drawable.power_rod_iii, R.drawable.power_rod_iv, R.drawable.power_rod_v, R.drawable.power_rod_vi,
                R.drawable.power_rod_vii, R.drawable.power_rod_viii, R.drawable.power_rod_ix,  R.drawable.power_rod_x, R.drawable.power_rod_xi, R.drawable.power_rod_xii);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setSpriteImage((int) va.getAnimatedValue());
            }
        });
        va.start();
    }

    @Override
    public String getName() {
        return "Upgrade";
    }

    @Override
    public String getPowerUpInfo() {
        return "Upgrades a piece to a queen";
    }

    /** Activates the power up. This power up upgrades the piece to a queen
     * @param controller Reference to the controller to the board
     * @param chessPiece The piece that took the power up
     * @param row vertical index for this power up
     * @param column horizontal index for this power up
     */
    @Override
    public void activatePowerUp(ChessBoardController controller, ChessPiece chessPiece, int row, int column) {
        AbstractPieceFactory factory = controller.getPieceFactory();
        controller.setPiece(row ,column, (ChessPiece) factory.createQueen(chessPiece.isWhite()));
    }

    public Sprite getSprite(){
        return sprite;
    }

    /** This method is used by the value animator to change to sprite image
     * @param drawableRef reference got by the value animator
     */
    public void setSpriteImage(int drawableRef) {
        sprite = new Sprite(new Image(drawableRef));
    }
}