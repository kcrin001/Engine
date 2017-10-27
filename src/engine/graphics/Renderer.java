package engine.graphics;

import engine.Engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Kaden Cringle
 * 10/20/2017
 */

public class Renderer {

    private ArrayList<SpriteRequest> spriteRequests = new ArrayList<>();
    private Font font;
    private int width, height;
    private int[] pixels;
    private int[] zBuffer;
    private int[] lm;
    private int[] lb;
    private int ambientColor = 0xFF6B6B6B;
    private int sort = 0;
    private boolean processing = false;

    public Renderer(Engine engine) {
        font = Font.getInstance();
        width = engine.getWidth();
        height = engine.getHeight();
        pixels = ((DataBufferInt) engine.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zBuffer = new int[pixels.length];
        lm = new int[pixels.length];
        lb = new int[pixels.length];
    }

    public void renderText(String text, int xPos, int yPos, int color) {
        int offset = 0;
        for(int i = 0; i < text.length(); i++) {
            int character = text.codePointAt(i);
            for(int y = 0; y < font.getImage().getHeight(); y++)
                for(int x = 0; x < font.getWidths()[character]; x++)
                    if(font.getImage().getPixels()[(x + font.getOffsets()[character]) + y * font.getImage().getWidth()] == 0xFFFFFFFF)
                        setPixel(x + xPos + offset, y + yPos, color);
            offset += font.getWidths()[character];
        }
    }

    public void renderSpriteFromSheet(SpriteSheet spriteSheet, int xSprite, int ySprite, int xPos, int yPos) {
        if(spriteSheet.hasAlpha() && !processing) {
            spriteRequests.add(new SpriteRequest(spriteSheet.getSprite(xSprite, ySprite), sort, xPos, yPos));
            return;
        }
        if(xPos < -spriteSheet.getSpriteWidth())
            return;
        if(yPos < -spriteSheet.getSpriteHeight())
            return;
        if(xPos > width)
            return;
        if(yPos > height)
            return;
        int xMin = 0, yMin = 0;
        int spriteWidth = spriteSheet.getSpriteWidth();
        int spriteHeight = spriteSheet.getSpriteHeight();
        if(xPos < 0)
            xMin -= xPos;
        if(yPos < 0)
            yMin -= yPos;
        if(spriteWidth + xPos >= width)
            spriteWidth -= spriteWidth + xPos - width;
        if(spriteHeight + yPos >= height)
            spriteHeight -= spriteHeight + yPos - height;
        for(int y = yMin; y < spriteHeight; y++) {
            for(int x = xMin; x < spriteWidth; x++) {
                setPixel(x + xPos, y + yPos, spriteSheet.getPixels()[(x + xSprite * spriteSheet.getSpriteWidth()) + (y + ySprite * spriteSheet.getSpriteHeight()) * spriteSheet.getWidth()]);
            }
        }
    }

    public void processSprites() {
        processing = true;
        spriteRequests.sort(Comparator.comparingInt(SpriteRequest::getSort));
        for(int i = 0; i < spriteRequests.size(); i++) {
            SpriteRequest sr = spriteRequests.get(i);
            setSort(sr.getSort());
            renderSprite(sr.getSprite(), sr.getXPos(), sr.getYPos());
        }
        for(int i = 0; i < pixels.length; i++) {
            float r = ((lm[i] >>  16) & 0xFF) / 255.0f;
            float g = ((lm[i] >>  8) & 0xFF) / 255.0f;
            float b = (lm[i] & 0xFF) / 255.0f;
            pixels[i] = ((int)(((pixels[i] >> 16) & 0xFF) * r) << 16 | (int)(((pixels[i] >> 8) & 0xFF) * g) << 8 | (int)((pixels[i] & 0xFF) * b));
        }
        spriteRequests.clear();
        processing = false;
    }

    public void renderSprite(Sprite sprite, int xPos, int yPos) {
        if(sprite.hasAlpha() && !processing) {
            spriteRequests.add(new SpriteRequest(sprite, sort, xPos, yPos));
            return;
        }
        if(xPos < -sprite.getWidth())
            return;
        if(yPos < -sprite.getHeight())
            return;
        if(xPos > width)
            return;
        if(yPos > height)
            return;
        int xMin = 0, yMin = 0;
        int spriteWidth = sprite.getWidth();
        int spriteHeight = sprite.getHeight();
        if(xPos < 0)
            xMin -= xPos;
        if(yPos < 0)
            yMin -= yPos;
        if(spriteWidth + xPos >= width)
            spriteWidth -= spriteWidth + xPos - width;
        if(spriteHeight + yPos >= height)
            spriteHeight -= spriteHeight + yPos - height;
        for(int y = yMin; y < spriteHeight; y++) {
            for(int x = xMin; x < spriteWidth; x++) {
                setPixel(x + xPos, y + yPos, sprite.getPixels()[x + y * sprite.getWidth()]);
            }
        }
    }

    public void renderFilledRectangle(int xPos, int yPos, int width, int height, int color) {
        if(xPos < -width)
            return;
        if(yPos < -height)
            return;
        if(xPos > this.width)
            return;
        if(yPos > this.height)
            return;
        int xMin = 0, yMin = 0;
        if(xPos < 0)
            xMin -= xPos;
        if(yPos < 0)
            yMin -= yPos;
        int newWidth = width;
        int newHeight = height;
        if(newWidth + xPos >= this.width)
            newWidth -= newWidth + xPos - this.width;
        if(newHeight + yPos >= this.height)
            newHeight -= newHeight + yPos - this.height;
        for(int y = yMin; y < newHeight; y++) {
            for(int x = xMin; x < newWidth; x++) {
                setPixel(x + xPos, y + yPos, color);
            }
        }
    }

    public void renderRectangle(int xPos, int yPos, int width, int height, int color) {
        for(int y = 0; y < height; y++) {
            setPixel(xPos, y + yPos, color);
            setPixel(xPos + width, y + yPos, color);
        }
        for(int x = 0; x < width; x++) {
            setPixel(x + xPos, yPos, color);
            setPixel(x + xPos, yPos + height, color);
        }
    }

    public void clear() {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0x00;
            zBuffer[i] = 0x00;
            lm[i] = ambientColor;
            lb[i] = 0x00;
        }
    }

    private void setPixel(int x, int y, int color) {
        int alpha = (color >> 24) & 0xFF;
        if((x < 0 || x >= width || y < 0 || y >= height) || alpha == 0)
            return;
        int index = x + y * width;
        if(zBuffer[index] > sort)
            return;
        zBuffer[index] = sort;
        if(alpha == 255)
            pixels[index] = color;
        else {
            int previousColor = pixels[index];
            int blendedR = ((previousColor >> 16) & 0xFF) - (int) ((((previousColor >> 16) & 0xFF) - ((color >> 16) & 0xFF)) * (alpha / 255.0f));
            int blendedG = ((previousColor >> 8) & 0xFF) - (int) ((((previousColor >> 8) & 0xFF) - ((color >> 8) & 0xFF)) * (alpha / 255.0f));
            int blendedB = (previousColor & 0xFF) - (int) (((previousColor & 0xFF) - (color & 0xFF)) * (alpha / 255.0f));
            pixels[index] = (blendedR << 16 | blendedG << 8 | blendedB);
        }
    }

    public void setLightMap(int x, int y, int color) {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return;
        int baseColor = lm[x + y * width];
        int maxR = Math.max((baseColor >> 16) & 0xFF, (color >> 16) & 0xFF);
        int maxG = Math.max((baseColor >> 8) & 0xFF, (color >> 8) & 0xFF);
        int maxB = Math.max(baseColor & 0xFF, color & 0xFF);
        lm[x + y * width] = (maxR << 16 | maxG << 8 | maxB);
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
