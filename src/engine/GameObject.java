package engine;

import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.math.Vector2f;

import java.util.ArrayList;

public abstract class GameObject {

    private ArrayList<IGameComponent> components = new ArrayList<>();
    private Sprite sprite;
    private Vector2f position;

    public GameObject(Sprite sprite, Vector2f position) {
        this.sprite = sprite;
        this.position = position;
    }

    public abstract void update(Engine engine, float delta);
    public abstract void render(Engine engine, Renderer renderer);

    public void addComponent(IGameComponent component) {
        components.add(component);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2f getPosition() {
        return position;
    }
}
