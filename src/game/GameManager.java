package game;

import engine.AbstractGame;
import engine.Engine;
import engine.audio.AudioClip;
import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Kaden Cringle
 * 10/20/2017
 */

public class GameManager extends AbstractGame {

    private Sprite alphaTest;
    private Sprite alphaTest2;
    private SpriteSheet spriteTest;
    private AudioClip audioTest;

    public GameManager() {
        alphaTest = new Sprite("/alphaTest.png");
        alphaTest.setAlpha(true);
        alphaTest2 = new Sprite("/alphaTest2.png");
        spriteTest = new SpriteSheet("/rock.png", 16, 16);
        audioTest = new AudioClip("/test.wav");
    }

    @Override
    public void update(Engine engine, float delta) {
        if(engine.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            audioTest.start();
            audioTest.setVolume(-15);
        }
        x += delta * 10;
        if(x > 3)
            x = 0;
    }

    float x = 0;

    @Override
    public void render(Engine engine, Renderer renderer) {
        renderer.setSort(1);
        renderer.renderSpriteFromSheet(spriteTest,(int)x, 0, 144, 104);
        renderer.setSort(0);
        renderer.renderSprite(alphaTest, engine.getInput().getMousePosX() - 16, engine.getInput().getMousePosY() - 16);
        //renderer.renderFilledRectangle(engine.getInput().getMousePosX() - 16, engine.getInput().getMousePosY() - 16, 32, 32, 0xFF00FFFF);
        //renderer.renderSpriteFromSheet(spriteTest, (int) x, 0, engine.getInput().getMousePosX() - 8, engine.getInput().getMousePosY() - 16);

    }

    public static void main(String... args) {
        new Engine(new GameManager()).start();
    }
}