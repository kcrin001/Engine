package engine;

import engine.graphics.Renderer;

/**
 * Kaden Cringle
 * 10/21/2017
 */

public interface IGame {

    void update(Engine engine, float delta);
    void render(Engine engine, Renderer renderer);
}
