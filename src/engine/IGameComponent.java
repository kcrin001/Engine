package engine;

import engine.graphics.Renderer;

public interface IGameComponent {

    void update(Engine engine, float delta);
    void render(Engine engine, Renderer renderer);
}
