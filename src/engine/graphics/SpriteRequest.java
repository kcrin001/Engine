package engine.graphics;

/**
 * Kaden Cringle
 * 10/22/2017
 */

public class SpriteRequest {

    private Sprite sprite;
    private int sort, xPos, yPos;

    public SpriteRequest(Sprite sprite, int sort, int xPos, int yPos) {
        this.sprite = sprite;
        this.sort = sort;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getSort() {
        return sort;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
