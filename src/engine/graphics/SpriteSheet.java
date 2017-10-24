package engine.graphics;

/**
 * Kaden Cringle
 * 10/22/2017
 */

public class SpriteSheet extends Sprite {

    private int spriteWidth, spriteHeight;

    public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
        super(path);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public Sprite getSprite(int xSprite, int ySprite) {
        int[] pixels = new int[spriteWidth * spriteHeight];
        for(int y = 0; y < spriteWidth; y++) {
            for(int x = 0; x < spriteWidth; x++) {
                pixels[x + y * spriteWidth] = getPixels()[(x + xSprite * spriteWidth) + (y + ySprite * spriteHeight)];
            }
        }
        return new Sprite(pixels, spriteWidth, spriteHeight);
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }
}
