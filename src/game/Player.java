package game;

import engine.Engine;
import engine.GameObject;
import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.math.Vector2f;

public class Player extends GameObject {

    public Player(Sprite sprite, Vector2f position) {
        super(sprite, position);
    }

    @Override
    public void update(Engine engine, float delta) {
        int x = engine.getInput().getMousePosX() - 32;
        int y = engine.getInput().getMousePosY() - 32;
        super.getPosition().set(x, y);
    }

    @Override
    public void render(Engine engine, Renderer renderer) {
        renderer.renderSprite(super.getSprite(), super.getPosition().getX(), super.getPosition().getY());
    }
}
