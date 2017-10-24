package engine;

import engine.graphics.Renderer;

/**
 * Kaden Cringle
 * 10/21/2017
 */

public abstract class AbstractGame {

    public abstract void update(Engine engine, float delta);
    public abstract void render(Engine engine, Renderer renderer);
}
