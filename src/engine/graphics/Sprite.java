package engine.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Kaden Cringle
 * 10/21/2017
 */

public class Sprite {

    private int width, height;
    private int[] pixels;
    private boolean hasAlpha = false;

    public Sprite(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = image.getWidth();
        height = image.getHeight();
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
        image.flush();
    }

    public Sprite(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }

    public void setAlpha(boolean hasAlpha) {
        this.hasAlpha = hasAlpha;
    }
}
