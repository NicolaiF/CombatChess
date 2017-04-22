package view;

import android.graphics.Canvas;

import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.gui.Widget;
import sheep.math.BoundingBox;


public class ImageButton extends Widget {

    private Sprite imageSprite;
    private float scale;
    private int x;
    private int y;
    private BoundingBox box;
    private Image image;
    private int width;

    /**
     * @param image The image for this button
     * @param x     Horizontal position in the canvas
     * @param y     Vertical position in the canvas
     */
    public ImageButton(Image image, int x, int y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.image = image;
        this.imageSprite = new Sprite(image);
        this.box = new BoundingBox(x, x + image.getWidth() * scale, y, y + image.getHeight() * scale);

        imageSprite.setOffset(0, 0);
        imageSprite.setPosition(x, y);
        imageSprite.setScale(scale, scale);
    }

    @Override
    public void draw(Canvas canvas) {
        imageSprite.update(0);
        imageSprite.draw(canvas);
    }

    public BoundingBox getBoundingBox() {
        return box;
    }

    public int getHeight() {
        return (int) (image.getHeight() * scale);
    }

    public int getWidth() {
        return (int) (image.getWidth() * scale);
    }

    public int getX() {
        return (int) imageSprite.getX();
    }

    public int getY() {
        return (int) imageSprite.getY();
    }

    /**
     * Sets a new image for this button
     *
     * @param image the new image
     */
    public void setImage(Image image) {
        imageSprite = new Sprite(image);
        imageSprite.setPosition(x, y);
        imageSprite.setOffset(0, 0);
        imageSprite.setScale(scale, scale);
        this.box = new BoundingBox(x, x + image.getWidth() * scale, y, y + image.getHeight() * scale);
    }

    /**
     * Sets a new position for this button
     *
     * @param x horizontal position
     * @param y vertical position
     */
    public void setPosition(int x, int y) {
        imageSprite.setPosition(x, y);
    }
}
