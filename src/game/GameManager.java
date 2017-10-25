package game;

import engine.GameObject;
import engine.IGame;
import engine.Engine;
import engine.audio.AudioClip;
import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;
import engine.math.Vector2f;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Kaden Cringle
 * 10/20/2017
 */

public class GameManager implements IGame {

    private ArrayList<GameObject> objects = new ArrayList<>();
    private Player player;
    private SpriteSheet spriteTest;
    private AudioClip audioTest;

    public GameManager() {
        player = new Player(new Sprite("/alphaTest.png"), new Vector2f(0, 0));
        player.getSprite().setAlpha(true);
        objects.add(player);
        spriteTest = new SpriteSheet("/rock.png", 16, 16);
        audioTest = new AudioClip("/test.wav");
    }

    @Override
    public void update(Engine engine, float delta) {
        if (engine.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            audioTest.start();
            audioTest.setVolume(-15);
        }
        x += delta * 10;
        if (x > 3)
            x = 0;
        for (GameObject object : objects)
            object.update(engine, delta);
    }

    float x = 0;

    @Override
    public void render(Engine engine, Renderer renderer) {
        renderer.setSort(1);
        renderer.renderSpriteFromSheet(spriteTest, (int) x, 0, 144, 104);
        renderer.setSort(0);
        for (GameObject object : objects)
            object.render(engine, renderer);
    }

    public static void main(String... args) {
        new Engine(new GameManager()).start();
    }
}